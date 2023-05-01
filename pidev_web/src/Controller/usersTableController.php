<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\UsersRepository;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\Persistence\ManagerRegistry;
use App\Entity\Users;
use Google\Service\AndroidEnterprise\Resource\Users as ResourceUsers;

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


    #[Route('/admin/users/{id}/delete', name: 'user_delete')]
    public function deleteUser(int $id): Response
    {
        $userRepository = $this->getDoctrine()->getManager()->getRepository(Users::class);
        $user = $userRepository->find($id);

        if (!$user) {
            throw $this->createNotFoundException('Utilisateur non trouvée');
        }

        $avatarPath = $user->getAvatarPath();
        if ($avatarPath) {
            $avatarFullPath = $this->getParameter('avatar_directory') . '/' . $avatarPath;
            if (file_exists($avatarFullPath)) {
                unlink($avatarFullPath);
            }
        }

        $em = $this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();

        return $this->redirectToRoute('admin_usersTable');
    }
}
