<?php

namespace App\Controller;

use App\Entity\Users;
use App\Form\UsersType;
use App\Form\LoginType;
use App\Form\UpdateProfileType;
use App\Repository\UsersRapository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Session\SessionInterface;


class UserController extends AbstractController
{

    #[Route('/signup', name: 'signup')]
    public function signup(ManagerRegistry $doctrine, Request $request): Response
    {
        $user = new Users();
        $form = $this->createForm(UsersType::class, $user);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {

            $password = $user->getPassword();
            $hashedPassword = password_hash($password, PASSWORD_BCRYPT);
            $user->setPassword($hashedPassword);
            $entityManager = $doctrine->getManager();
            $entityManager->persist($user);
            $entityManager->flush();
            return $this->redirectToRoute('login');
        }

        return $this->renderForm('auth/signup.html.twig', [
            'form' => $form
        ]);
    }
    #[Route('/login', name: 'login', methods: 'POST')]
    public function login(Request $request, ManagerRegistry $registry, SessionInterface $session): Response
    {
        $error = '';
        $form = $this->createForm(LoginType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $formData = $form->getData();
            $email = $formData->getEmail();
            $password = $formData->getPassword();

            $entityManager = $registry->getManagerForClass(Users::class);
            $userRepository = $entityManager->getRepository(Users::class);
            $user = $userRepository->findOneBy(['email' => $email]);

            if ($user && password_verify($password, $user->getPassword())) {
                $session->set('user_id', $user->getId());

                if ($user->getType() == "STUDENT") {
                    return $this->redirectToRoute('profile', ['id' => $user->getId()]);
                } else {
                    return $this->redirectToRoute('auth/dashboard.html.twig');
                }
            } else {
                $error = 'Invalid email or password';
            }
        }

        return $this->render('auth/login.html.twig', [
            'form' => $form->createView(),
            'error' => $error,
        ]);
    }


    #[Route('/profile/{id}', name: 'profile')]
    public function profile(Request $request, ManagerRegistry $registry, $id): Response
    {
        $entityManager = $registry->getManagerForClass(Users::class);
        $userRepository = $entityManager->getRepository(Users::class);
        $user = $userRepository->find($id);

        if (!$user) {
            return $this->redirectToRoute('login');
        }

        $form = $this->createForm(UpdateProfileType::class, $user);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Handle form submission and update user entity
            $entityManager->flush();

            // Redirect to profile page or any other page as needed
            return $this->redirectToRoute('profile', ['id' => $id]);
        }

        return $this->render('profile/profile.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }

    // #[Route('/profile/{id}', name: 'profile')]
    // public function profile(Request $request, ManagerRegistry $registry, $id): Response
    // {
    //     $entityManager = $registry->getManagerForClass(Users::class);
    //     $userRepository = $entityManager->getRepository(Users::class);
    //     $user = $userRepository->find($id);

    //     if (!$user) {
    //         return $this->redirectToRoute('login');
    //     }

    //     return $this->render('profile/profile.html.twig', [
    //         'user' => $user,
    //     ]);
    // }


    #[Route('/user', name: 'app_user')]
    public function index(): Response
    {
        return $this->render('auth/login.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }
}
