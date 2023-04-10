<?php

namespace App\Controller;


use App\Entity\Users;
use App\Repository\SessionsRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\InputBag;
use Symfony\Component\Routing\Annotation\Route;

class SessionsListController extends AbstractController
{
  private SessionsRepository $sessionsRepository;

  public function __construct(SessionsRepository $sessionsRepository)
  {
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

    $tmpUser = new Users();

    $filterObject = $this->buildFilterObject($request->query, 529);
    $sessions = $this->sessionsRepository->findBy($filterObject);

    $totalFoundSessions = sizeof($sessions);

    return $this->render('sessions/sessionsList.html.twig', [
      'sessions' => $sessions,
      'totalFoundSessions' => $totalFoundSessions,
      'filters' => $filterObject
    ]);
  }
}
