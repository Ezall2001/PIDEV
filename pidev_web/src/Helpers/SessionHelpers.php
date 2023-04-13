<?php

namespace App\Helpers;

use App\Entity\Courses;
use App\Entity\Sessions;
use App\Entity\Users;
use App\Repository\SessionsRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Session\Session;
use Symfony\Component\Routing\Annotation\Route;


class SessionHelpers
{

  public static function buildSessions(array $inputs, int $userId, EntityManagerInterface $entityManager): array
  {
    $sessions = [];

    $courseId = intval($inputs['course']);
    $course = $entityManager->getRepository(Courses::class)->find($courseId);

    $user = $entityManager->getRepository(Users::class)->find($userId);

    $timeValues = explode(';', $inputs['time']);
    $startTime = new \DateTime($timeValues[0]);
    $endTime = new \DateTime($timeValues[1]);
    $maxPlaces = intval($inputs['maxPlaces']);
    $price = intval($inputs['price']);

    $dates = explode(', ', $inputs['dates']);

    foreach ($dates as $dateString) {
      $date = new \DateTime($dateString);

      $session = new Sessions();
      $session->setPrice($price);
      $session->setDate($date);
      $session->setStartTime($startTime);
      $session->setEndTime($endTime);
      $session->setCourse($course);
      $session->setTopics($inputs['topics']);
      $session->setUser($user);
      $session->setMaxPlaces($maxPlaces);

      //TODO: add image and meet

      $sessions[] = $session;
    }

    return $sessions;
  }


  public static function validate(SessionsRepository $sessionsRepository, Sessions $session): string
  {
    $error = null;

    if (
      empty($session->getPrice()) or
      empty($session->getDate()) or
      empty($session->getStartTime()) or
      empty($session->getEndTime()) or
      empty($session->getMaxPlaces())
    )
      $error = "champ(s) vide";


    return $error;
  }
}
