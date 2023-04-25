<?php

namespace App\Controller;

use App\Entity\Sessions;
use App\Repository\SessionsRepository;
use App\Repository\CoursesRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\InputBag;

class SessionController extends AbstractController
{
  private SessionsRepository $sessionsRepository;
  private EntityManagerInterface $entityManager;
  private CoursesRepository $coursesRepository;
  private int $tmpUserId;

  public function __construct(SessionsRepository $sessionsRepository, CoursesRepository $coursesRepository, EntityManagerInterface $entityManager)
  {
    $this->entityManager = $entityManager;
    $this->sessionsRepository = $sessionsRepository;
    $this->coursesRepository = $coursesRepository;
    $this->tmpUserId = $_ENV['TMP_USER_ID'];
  }

  private function buildAlertObject(InputBag $queryString): array
  {
    $alertObject = [];
    $alertObject['show'] = false;


    if ($queryString->get('success') == "1") {
      $alertObject['show'] = true;
      $alertObject['state'] = true;
    } else if ($queryString->get('error') == "1") {
      $alertObject['show'] = true;
      $alertObject['state'] = false;
    }

    return $alertObject;
  }

  private function buildSessionObject(int $id): Sessions
  {
    $session = $this->sessionsRepository->getPageSession($id, $this->tmpUserId);
    $creatorId = $session->getUser()->getId();


    if ($session->getImageLink() == null)
      $session->setImageLink('images/defaultSessionImage.jpg');
    else
      $session->setImageLink('uploads/' . $creatorId  . '/' . $session->getImageLink());


    foreach ($session->getResources() as $resource) {
      $resource->setFilePath('uploads/' . $creatorId . '/' . $resource->getFilePath());
    }

    return $session;
  }

  private function buildActionType(Sessions $session): string
  {
    $actionType = 'participate';

    if ($this->tmpUserId == $session->getUser()->getId())
      $actionType = 'creator';
    else if (
      sizeof($session->getParticipations()) > 0
      and ($session->getParticipations()[0])->getState() == 'ACCEPTED'
    )
      $actionType = "baught";

    return $actionType;
  }

  #[Route('/session/{id}', name: 'session')]
  public function session(Request $request, int $id): Response
  {
    $session = $this->buildSessionObject($id);
    $actionType = $this->buildActionType($session);
    $alertObject = $this->buildAlertObject($request->query);
    $courses = $this->coursesRepository->getCourseNames();

    return $this->render('sessions/session.html.twig', [
      'session' => $session,
      'actionType' => $actionType,
      'alert' => $alertObject,
      'courses' => $courses
    ]);
  }
}
