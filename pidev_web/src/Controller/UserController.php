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
                return $this->redirectToRoute('profil');
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
                $user->setAvatarPath($avatarFileName);
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
                'Activation de votre compte sur le site Myalo',
                'register',
                compact('user', 'token')
            );


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

    #[Route('/profil', name: 'profil')]
    public function profil(Request $request, ManagerRegistry $registry, Security $security): Response
    {
        $user = $security->getUser();

        if (!$user) {
            return $this->redirectToRoute('login');
        }

        $form = $this->createForm(UpdateProfileType::class, $user);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $entityManager = $registry->getManager();

            // $avatarPath = $form->get('avatarPath')->getData();
            // if ($avatarPath) {
            //     $avatarFileName = md5(uniqid()) . '.' . $avatarPath->guessExtension();
            //     $avatarPath->move(
            //         $this->getParameter('avatar_directory'),
            //         $avatarFileName
            //     );
            //     $user->setAvatarPath($avatarFileName);
            // }

            $entityManager->flush();

            return $this->redirectToRoute('profil');
        } elseif ($form->isSubmitted()) {
            $this->addFlash('error', 'There are errors in the form.');
        }

        return $this->render('profile/profile.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
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
