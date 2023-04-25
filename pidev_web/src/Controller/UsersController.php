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
use App\Repository\UsersRapository;
use App\Service\SendMailService;
use App\Form\ResetPasswordRequestType;
use App\Form\ResetPasswordType;
use Symfony\Component\Security\Core\Authorization\AuthorizationCheckerInterface;


class UsersController extends AbstractController
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
    public function logout(): void
    {
        throw new \LogicException('This method can be blank - it will be intercepted by the logout key on your firewall.');
    }

    #[Route('/forgottenPassword', name: 'forgotten_password')]
    public function forgottenPassword(Request $request, UsersRapository $usersRepository, TokenGeneratorInterface $tokenGenerator, EntityManagerInterface $entityManager, SendMailService $mail): Response
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
                $mail->send(
                    'esprit.myalo@gmail.com',
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
        UsersRapository $usersRepository,
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
}
