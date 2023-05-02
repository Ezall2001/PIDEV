<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use App\Repository\UsersRepository;
use App\Services\MailerService;
use App\Services\JWTService;
use App\Form\ResetPasswordRequestType;
use App\Form\ResetPasswordType;
use App\Form\RegistrationFormType;
use App\Form\UpdateProfileType;
use Symfony\Component\Security\Core\Authorization\AuthorizationCheckerInterface;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\Security\Core\Security;
use App\Security\UsersAuthAuthenticator;
use Symfony\Component\Security\Http\Authentication\UserAuthenticatorInterface;
use App\Entity\Users;
use App\Repository\SessionsRepository;

class UserController extends AbstractController
{
    #[Route(path: '/conn', name: 'app_login')]
    public function login(AuthenticationUtils $authenticationUtils, AuthorizationCheckerInterface $authChecker): Response
    {
        $user = $this->getUser();
        if ($user) {

            if ($authChecker->isGranted('ROLE_ADMIN', $user)) {
                return $this->redirectToRoute('admin_usersTable');
            } else if ($authChecker->isGranted('ROLE_USER', $user)) {
                return $this->render('landing/landing.html.twig');
            }
        }

        // get the login error if there is one
        $error = $authenticationUtils->getLastAuthenticationError();
        // last username entered by the user
        $lastUsername = $authenticationUtils->getLastUsername();

        return $this->render('security/login.html.twig', ['last_username' => $lastUsername, 'error' => $error]);
    }

    #[Route(path: '/logout', name: 'app_logout')]
    public function logout(): Response
    {
        return $this->render('security/logout.html.twig');
    }

    #[Route('/register', name: 'app_register')]
    public function register(Request $request, UserPasswordHasherInterface $userPasswordHasher, UserAuthenticatorInterface $userAuthenticator, UsersAuthAuthenticator $authenticator, EntityManagerInterface $entityManager, MailerService $mail, JWTService $jwt): Response
    {
        $user = new Users();
        $form = $this->createForm(RegistrationFormType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user->setCreatedAt();
            // encode the plain password
            $user->setPassword(
                $userPasswordHasher->hashPassword(
                    $user,
                    $form->get('plainPassword')->getData()
                )
            );

            // handle avatar file upload
            $avatarPath = $form->get('avatarPath')->getData();
            if ($avatarPath) {
                $avatarFileName = md5(uniqid()) . '.' . $avatarPath->guessExtension();
                $avatarPath->move(
                    $this->getParameter('avatar_directory'),
                    $avatarFileName
                );

                // $user->setAvatarPath($avatarFileName);
                $user->setAvatarPath("server/profile_avatars/{$avatarFileName}");
            }

            $entityManager->persist($user);
            $entityManager->flush();
            // generate JWT token
            $header = [
                'typ' => 'JWT',
                'alg' => 'HS256'
            ];

            $payload = [
                'user_id' => $user->getId()
            ];

            $token = $jwt->generate($header, $payload, $this->getParameter('app.jwtsecret'));

            // send email with activation link and token
            $mail->sendEya(
                $user->getEmail(),
                'Activation de votre compte sur le site Myalò',
                'register',
                compact('user', 'token')
            );


            $this->addFlash('success', 'Email d activation envoyé avec succès');
            // authenticate user and redirect to homepage
            return $userAuthenticator->authenticateUser(
                $user,
                $authenticator,
                $request
            );
        }

        return $this->render('security/register.html.twig', [
            'registrationForm' => $form->createView(),
        ]);
    }

    #[Route('/verif/{token}', name: 'verifyUser')]
    public function verifyUser($token, JWTService $jwt, UsersRepository $usersRepository, EntityManagerInterface $em): Response
    {
        //On vérifie si le token est valide, n'a pas expiré et n'a pas été modifié
        if ($jwt->isValid($token) && !$jwt->isExpired($token) && $jwt->check($token, $this->getParameter('app.jwtsecret'))) {
            // On récupère le payload
            $payload = $jwt->getPayload($token);

            // On récupère le user du token
            $user = $usersRepository->find($payload['user_id']);

            //On vérifie que l'utilisateur existe et n'a pas encore activé son compte
            if ($user && !$user->getIsVerified()) {
                $user->setIsVerified(true);
                $em->flush($user);
                $this->addFlash('success', 'Utilisateur activé');
                return $this->redirectToRoute('profil');
            }
        }
        // Ici un problème se pose dans le token
        $this->addFlash('danger', 'Le token est invalide ou a expiré');
        return $this->redirectToRoute('app_login');
    }

    #[Route('/resendverif', name: 'resendVerif')]
    public function resendVerif(JWTService $jwt, MailerService $mail, UsersRepository $usersRepository): Response
    {
        $user = $this->getUser();


        if (!$user) {
            $this->addFlash('danger', 'Vous devez être connecté pour accéder à cette page');
            return $this->redirectToRoute('app_login');
        }

        if ($user->getIsVerified()) {
            $this->addFlash('warning', 'Cet utilisateur est déjà activé');
            return $this->redirectToRoute('profil');
        }

        // On génère le JWT de l'utilisateur
        // On crée le Header
        $header = [
            'typ' => 'JWT',
            'alg' => 'HS256'
        ];

        // On crée le Payload
        $payload = [
            'user_id' => $user->getId()
        ];

        // On génère le token
        $token = $jwt->generate($header, $payload, $this->getParameter('app.jwtsecret'));

        // On envoie un mail
        $mail->sendEya(
            $user->getEmail(),
            'Activation de votre compte sur le site Myalò',
            'register',
            compact('user', 'token')
        );
        $this->addFlash('success', 'Email de vérification envoyé');
        return $this->redirectToRoute('profil');
    }

    #[Route('/forgottenPassword', name: 'forgotten_password')]
    public function forgottenPassword(Request $request, UsersRepository $usersRepository, TokenGeneratorInterface $tokenGenerator, EntityManagerInterface $entityManager, MailerService $mail): Response
    {
        $form = $this->createForm(ResetPasswordRequestType::class);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $user = $usersRepository->findOneByEmail($form->get('email')->getData());
            // dd($user);

            if ($user) {
                //   token de réinitialisation
                $token = $tokenGenerator->generateToken();
                $user->setResetToken($token);
                $entityManager->persist($user);
                $entityManager->flush();
                // dd($token);
                //   lien de réinitialisation du mot de passe
                $url = $this->generateUrl('reset_pass', ['token' => $token], UrlGeneratorInterface::ABSOLUTE_URL);

                // données du mail
                $context = compact('url', 'user');

                // Envoi du mail
                $mail->sendEya(
                    $user->getEmail(),
                    'Réinitialisation de mot de passe',
                    'passwordReset',
                    $context
                );


                $this->addFlash('success', 'Email envoyé avec succès');
                return $this->redirectToRoute('app_login');
            }
            // $user est null
            $this->addFlash('danger', 'Un problème est survenu');
            return $this->redirectToRoute('app_login');
        }

        return $this->render('security/reset_password_request.html.twig', [
            'requestPassForm' => $form->createView()
        ]);
    }
    #[Route('/resetPassword/{token}', name: 'reset_pass')]
    public function resetPass(
        string $token,
        Request $request,
        UsersRepository $usersRepository,
        EntityManagerInterface $entityManager,
        UserPasswordHasherInterface $passwordHasher
    ): Response {
        // On vérifie si on a ce token dans la base
        $user = $usersRepository->findOneByResetToken($token);

        if ($user) {
            $form = $this->createForm(ResetPasswordType::class);

            $form->handleRequest($request);

            if ($form->isSubmitted() && $form->isValid()) {
                // On efface le token
                $user->setResetToken('');
                $user->setPassword(
                    $passwordHasher->hashPassword(
                        $user,
                        $form->get('password')->getData()
                    )
                );
                $entityManager->persist($user);
                $entityManager->flush();

                $this->addFlash('success', 'Mot de passe changé avec succès');
                return $this->redirectToRoute('app_login');
            }

            return $this->render('security/resetPassword.html.twig', [
                'passForm' => $form->createView()
            ]);
        }
        $this->addFlash('danger', 'Jeton invalide');
        return $this->redirectToRoute('app_login');
    }

    // #[Route('/profil', name: 'profil')]
    // public function profil(Request $request, ManagerRegistry $registry, Security $security): Response
    // {
    //     $user = $security->getUser();

    //     if (!$user) {
    //         return $this->redirectToRoute('login');
    //     }

    //     $form = $this->createForm(UpdateProfileType::class, $user);

    //     $form->handleRequest($request);

    //     if ($form->isSubmitted() && $form->isValid()) {

    //         $entityManager = $registry->getManager();

    //         // $avatarPath = $form->get('avatarPath')->getData();
    //         // if ($avatarPath) {
    //         //     $avatarFileName = md5(uniqid()) . '.' . $avatarPath->guessExtension();
    //         //     $avatarPath->move(
    //         //         $this->getParameter('avatar_directory'),
    //         //         $avatarFileName
    //         //     );
    //         //     $user->setAvatarPath($avatarFileName);
    //         // }

    //         $entityManager->flush();
    //         $this->addFlash('success', 'Vos informations sont mis à jour .');
    //         return $this->redirectToRoute('profil');
    //     } elseif ($form->isSubmitted()) {
    //         $this->addFlash('warning', 'Il y a des erreurs.');
    //     }

    //     return $this->render('profile/profile.html.twig', [
    //         'user' => $user,
    //         'form' => $form->createView(),
    //     ]);
    // }
    #[Route('/profil', name: 'profil')]
    public function profil(Request $request, ManagerRegistry $registry, Security $security, SessionsRepository $sessionsRepository): Response
    {
        $user = $security->getUser();

        if (!$user) {
            return $this->redirectToRoute('login');
        }

        $form = $this->createForm(UpdateProfileType::class, $user);

        $form->handleRequest($request);

        $entityManager = $registry->getManager();


        $entityManager->flush();

        if ($form->isSubmitted() && $form->isValid()) {

            $this->addFlash('success', 'Vos informations sont mises à jour.');
            return $this->redirectToRoute('profil');
        } elseif ($form->isSubmitted()) {
            $this->addFlash('danger', 'Il y a des erreurs.');
        }

        $sessions = $sessionsRepository->getBaughtSessions($user->getId());


        return $this->render('profile/profile.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
            'sessions' => $sessions,
            'userId' => $user->getId()
        ]);
    }

    #[Route('/user', name: 'app_user')]
    public function index(): Response
    {
        return $this->render('auth/login.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }
}
