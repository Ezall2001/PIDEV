<?php

namespace App\Controller;

use App\Repository\SessionsRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;


class SessionController extends AbstractController
{
  private SessionsRepository $sessionsRepository;

  public function __construct(SessionsRepository $sessionsRepository)
  {
    $this->sessionsRepository = $sessionsRepository;
  }

  #[Route('/session/{id}', name: 'session')]
  public function session(int $id): Response
  {
    $sessions = $this->sessionsRepository->find($id);

    return $this->render('sessions/session.html.twig', [
      'session' => $sessions
    ]);
  }
}
