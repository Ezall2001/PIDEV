<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\UsersRepository;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\Persistence\ManagerRegistry;
use App\Entity\Users;


#[Route('/admin', name: 'admin_')]
class usersTableController extends AbstractController
{
    #[Route('/usersTable', name: 'usersTable')]
    public function usersTable(UsersRepository $usersRepository): Response
    {
        $users = $usersRepository->findBy([], ['firstName' => 'asc']);
        return $this->render('admin/usersTable.html.twig', compact('users'));
    }
    #[Route('/dashboard', name: 'dashboard')]
    public function index(): Response
    {
        return $this->render('admin/dashboard.html.twig');
    }
    #[Route('/admin/users/{id}/delete', name: 'user_delete', methods: ['DELETE'])]
    public function deleteUser(Request $request, Users $user): Response
    {
        $submittedToken = $request->request->get('token');

        // Verify CSRF token
        if (!$this->isCsrfTokenValid('delete-user', $submittedToken)) {
            $this->addFlash('error', 'Invalid CSRF token.');
            return $this->redirectToRoute('admin_usersTable');
        }

        try {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($user);
            $entityManager->flush();

            $this->addFlash('success', 'User deleted successfully!');
        } catch (\Exception $e) {
            $this->addFlash('error', 'An error occurred while deleting the user: ' . $e->getMessage());
        }

        return $this->redirectToRoute('admin_usersTable');
    }
}
