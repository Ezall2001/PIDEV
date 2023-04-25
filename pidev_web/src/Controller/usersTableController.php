<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\UsersRapository;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\Persistence\ManagerRegistry;
use App\Entity\Users;


#[Route('/admin', name: 'admin_')]
class usersTableController extends AbstractController
{
    #[Route('/usersTable', name: 'usersTable')]
    public function usersTable(UsersRapository $usersRepository): Response
    {
        $users = $usersRepository->findBy([], ['firstName' => 'asc']);
        return $this->render('admin/usersTable.html.twig', compact('users'));
    }
    #[Route('/dashboard', name: 'dashboard')]
    public function index(): Response
    {
        return $this->render('admin/dashboard.html.twig');
    }
    #[Route('/users/{id}/delete', name: 'user_delete', methods: ['DELETE'])]
    public function deleteUser(Request $request, ManagerRegistry $managerRegistry, int $id): Response
    {
        $userRepository = $managerRegistry->getRepository(Users::class);
        $user = $userRepository->find($id);

        if (!$user) {
            throw $this->createNotFoundException(sprintf('User with ID "%s" not found.', $id));
        }

        $entityManager = $managerRegistry->getManager();
        $entityManager->remove($user);
        $entityManager->flush();

        $this->addFlash('success', 'User deleted successfully.');

        // redirect back to the users table page
        return $this->redirectToRoute('admin_usersTable');
    }
}
