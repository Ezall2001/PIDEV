<?php

namespace App\Controller;

use Doctrine\ORM\EntityManagerInterface;
use App\Repository\SessionsRepository;
use App\Helpers\SessionHelpers;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\InputBag;
use Symfony\Component\Routing\Annotation\Route;

class SessionsListController extends AbstractController
{
  private SessionsRepository $sessionsRepository;
  private EntityManagerInterface $entityManager;
  private int $tmpUserId = 529;


  public function __construct(SessionsRepository $sessionsRepository, EntityManagerInterface $entityManager)
  {
    $this->entityManager = $entityManager;
    $this->sessionsRepository = $sessionsRepository;
  }

  private function buildFilterObject(InputBag $queryString, int $userId): array
  {
    $filterObject = [];

    $is_owner = $queryString->get('owner');
    if ($is_owner == '1')
      $filterObject['user'] = $userId;

    return $filterObject;
  }


  #[Route('/sessionsList', name: 'sessions_list')]
  public function sessionsList(Request $request): Response
  {

    $filterObject = $this->buildFilterObject($request->query, $this->tmpUserId);
    $sessions = $this->sessionsRepository->findBy($filterObject);

    $totalFoundSessions = sizeof($sessions);

    return $this->render('sessions/sessionsList.html.twig', [
      'sessions' => $sessions,
      'totalFoundSessions' => $totalFoundSessions,
      'filters' => $filterObject
    ]);
  }

  #[Route('/sessionsList/addSession', name: 'sessions_list_add_session', methods: ['POST'])]
  public function addSession(Request $request): Response
  {

    $sessions = SessionHelpers::buildSessions($_POST, $this->tmpUserId, $this->entityManager);
    foreach ($sessions as $session)
      $this->getDoctrine()->getManager()->persist($session);

    $this->getDoctrine()->getManager()->flush();

    return $this->redirectToRoute('sessions_list');
  }
}
