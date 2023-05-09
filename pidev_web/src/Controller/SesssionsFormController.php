<?php

namespace App\Controller;

use App\Entity\Resources;
use Doctrine\ORM\EntityManagerInterface;
use App\Repository\SessionsRepository;
use App\Repository\CoursesRepository;
use App\Entity\Sessions;
use App\Entity\Users;
use App\Entity\Courses;
use App\Entity\Participations;
use App\Services\PaymentService;
use App\Services\CalendarService;
use Illuminate\Support\Facades\Redirect;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Security\Core\Security;
use Symfony\Component\Filesystem\Filesystem;

if (!isset($_SESSION)) {
  session_start();
}

class SesssionsFormController extends AbstractController
{
  private SessionsRepository $sessionsRepository;
  private CoursesRepository $coursesRepository;
  private EntityManagerInterface $entityManager;
  private PaymentService $paymentService;
  private CalendarService $calendarService;
  private ?int $userId = null;


  public function __construct(SessionsRepository $sessionsRepository, CoursesRepository $coursesRepository, EntityManagerInterface $entityManager, Security $security)
  {
    $this->entityManager = $entityManager;
    $this->sessionsRepository = $sessionsRepository;
    $this->coursesRepository = $coursesRepository;
    $this->paymentService = new PaymentService();
    $this->calendarService = new CalendarService();

    $user = $security->getUser();
    if ($user)
      $this->userId = $security->getUser()->getId();
  }


  private function buildSession(array $inputs, int $userId, string $imageLink, array $resources, EntityManagerInterface $entityManager): Sessions
  {
    $courseId = intval($inputs['course']);
    $course = $entityManager->getRepository(Courses::class)->find($courseId);

    $user = $entityManager->getRepository(Users::class)->find($userId);

    $timeValues = explode(';', $inputs['time']);
    $date = new \DateTime($inputs['dates']);
    $startTime = new \DateTime($timeValues[0]);
    $endTime = new \DateTime($timeValues[1]);
    $maxPlaces = intval($inputs['maxPlaces']);
    $price = intval($inputs['price']);

    $session = new Sessions();

    if (!empty($inputs['id'])) {
      $sessionId = intval($inputs['id']);
      $session = $entityManager->getRepository(Sessions::class)->find($sessionId);
    }

    $session->setPrice($price);
    $session->setDate($date);
    $session->setStartTime($startTime);
    $session->setEndTime($endTime);
    $session->setCourse($course);
    $session->setTopics($inputs['topics']);
    $session->setUser($user);
    $session->setMaxPlaces($maxPlaces);
    $session->setImageLink($imageLink);

    foreach ($resources as $resource) {
      $resource = $this->buildResource($resource[1], $resource[0]);
      $session->addResource($resource);
    }



    return $session;
  }


  private function buildSessionObject(int $id): Sessions
  {
    $session = $this->sessionsRepository->getPageSession($id, $this->userId);
    $creatorId = $session->getUser()->getId();


    if ($session->getImageLink() == null)
      $session->setImageLink('images/defaultSessionImage.jpg');
    else
      $session->setImageLink('/uploads/' . $creatorId  . '/' . $session->getImageLink());


    foreach ($session->getResources() as $resource) {
      $resource->setFilePath('uploads/' . $creatorId . '/' . $resource->getFilePath());
    }

    return $session;
  }

  private function buildResource($fileName, $fileLink)
  {
    $resource = new Resources();
    $resource->setName($fileName);
    $resource->setFilePath($fileLink);

    return $resource;
  }


  private function validate(Sessions $session): string
  {
    $error = false;

    if (
      empty($session->getPrice()) or
      empty($session->getDate()) or
      empty($session->getStartTime()) or
      empty($session->getEndTime()) or
      empty($session->getMaxPlaces())
    )
      $error = true;


    return $error;
  }

  #[Route('/persistSession', name: 'persist_session', methods: ['POST'])]
  public function persistSession(Request $request)
  {

    if ($this->userId == null)
      return $this->redirectToRoute('app_login');

    $imageFileLink = '';
    $imageFile = $_FILES['image'];
    if ($imageFile['error'] != 4)
      $imageFileLink = md5(uniqid()) . '.' . pathinfo($imageFile['name'], PATHINFO_EXTENSION);

    $resourceFiles = [];
    foreach ($_FILES as $key => $file) {
      if (str_starts_with($key, 'resource-'))
        $resourceFiles[] = [md5(uniqid()) . '.' . pathinfo($file['name'], PATHINFO_EXTENSION), $file['name'], $file['tmp_name']];
    }


    $session = $this->buildSession($_POST, $this->userId, $imageFileLink, $resourceFiles,  $this->entityManager);
    $error = $this->validate($session);
    if ($error) {
      $this->entityManager->clear();
      return $this->redirect($request->query->get('redirect') . '?error=1');
    }

    $this->entityManager->persist($session);
    $this->entityManager->flush();

    if (!is_dir("uploads/" . $this->userId))
      mkdir("uploads/" . $this->userId);

    move_uploaded_file($imageFile['tmp_name'], "uploads/" . $this->userId . "/" . $imageFileLink);

    foreach ($resourceFiles as $resourceFile)
      move_uploaded_file($resourceFile[2], "uploads/" . $this->userId . "/" . $resourceFile[0]);

    if (!empty($_POST['id']))
      return $this->redirect($request->query->get('redirect') . '?success=1');

    $_SESSION['sessionId'] = $session->getId();
    $_SESSION['calendarOp'] = 'createEvent';
    return $this->redirectToRoute('create_event');
  }

  #[Route('/removeSession/{id}', name: 'sessions_list_remove_session')]
  public function removeSession(int $id): Response
  {
    if ($this->userId == null)
      return $this->redirectToRoute('app_login');

    $session = $this->sessionsRepository->find($id);
    $this->entityManager->remove($session);
    $this->entityManager->flush();
    return $this->redirectToRoute('sessions_list', ['success' => '1']);
  }

  #[Route('/getSessionForm/{id}', name: 'get_session_form')]
  public function getSessionForm(NormalizerInterface $normalizer, int $id): JsonResponse
  {

    if ($this->userId == null)
      return $this->redirectToRoute('app_login');

    $session = $this->buildSessionObject($id);
    $normalizedSession = $normalizer->normalize($session, 'json', ["groups" => "sessionFrom"]);
    return new JsonResponse($normalizedSession);
  }

  #[Route('/admin/blockSession/{id}', name: 'block_session', methods: ['PUT'])]
  public function blockSession(int $id): JsonResponse
  {
    if ($this->userId == null)
      return $this->redirectToRoute('app_login');

    $this->sessionsRepository->setSessionBlock($id, true);
    return new JsonResponse(['error' => null]);
  }

  #[Route('/admin/unblockSession/{id}', name: 'unblock_session', methods: ['PUT'])]
  public function unblockSession(int $id): JsonResponse
  {
    if ($this->userId == null)
      return $this->redirectToRoute('app_login');

    $this->sessionsRepository->setSessionBlock($id, false);
    return new JsonResponse(['error' => null]);
  }

  #[Route('/participate/{id}', name: 'participate_session')]
  public function participateSession(int $id): RedirectResponse
  {

    if ($this->userId == null)
      return $this->redirectToRoute('app_login');

    $session = $this->sessionsRepository->getParticipationSession($id);
    $user = $this->entityManager->getRepository(Users::class)->find($this->userId);

    $participation = $this->entityManager->getRepository(Participations::class)->getParticipation($id, $this->userId);

    if ($participation == null) {
      $participation = new Participations();
      $participation->setUser($user);
      $participation->setSession($session);
      $participation->setState('PENDING');

      $this->entityManager->persist($participation);
      $this->entityManager->flush();
    }

    if ($participation->getState() == 'ACCEPTED')
      return $this->redirectToRoute('sessions_list', ['error' => '1']);



    $result = $this->paymentService->init($session->getUser()->getWalledId(), $session->getPrice(), $participation->getId());
    return new RedirectResponse($result['payUrl']);
  }

  #[Route('/validateParticipation', name: 'validate_participation')]
  public function validateParticipation(Request $request): Response
  {

    if ($this->userId == null)
      return $this->redirectToRoute('app_login');

    $paymentRef = $request->query->get('payment_ref');
    $payment = $this->paymentService->getPayment($paymentRef);
    $participationId = $payment['orderId'];
    $participation = $this->entityManager->getRepository(Participations::class)->find($participationId);

    $redirect = null;
    if ($payment['successfulTransactions'] == 1) {
      $participation->setState('ACCEPTED');

      $_SESSION['eventId'] = $participation->getSession()->getEventId();
      $_SESSION['userEmail'] = $participation->getUser()->getEmail();
      $_SESSION['sessionId'] = $participation->getSession()->getId();
      $_SESSION['calendarOp'] = 'addAttendee';
      $redirect = $this->redirectToRoute('create_event');
    } else {
      $participation->setState('DENIED');
      $redirect = $this->redirectToRoute('sessions_list', ['error' => '1']);
    }

    $this->entityManager->persist($participation);
    $this->entityManager->flush();
    return $redirect;
  }


  #[Route('/createEvent', name: 'create_event')]
  public function createEvent(): Response
  {

    if ($this->userId == null)
      return $this->redirectToRoute('app_login');

    if ($_SESSION['calendarOp'] == 'addAttendee') {
      $url = $this->calendarService->authUser('addAttendee');
      if ($url != null)
        return $this->redirect($url);

      $this->calendarService->addAttendee($_SESSION['eventId'], $_SESSION['userEmail']);
      return $this->redirect('/session/' . $_SESSION['sessionId'] . '?success=1');
    } else {
      $url = $this->calendarService->authUser();
      if ($url != null)
        return $this->redirect($url);

      $session = $this->sessionsRepository->getCalendarSession($_SESSION['sessionId']);
      $result = $this->calendarService->createEvent($session);
      $session->setMeetLink($result[0]);
      $session->setEventId($result[1]);
      $this->entityManager->persist($session);
      $this->entityManager->flush();

      return $this->redirectToRoute('sessions_list', ['success' => '1']);
    }
  }

  #[Route('/api/removeSession/{id}', name: 'api_remove_session')]
  public function apiRemoveSession(int $id): JsonResponse
  {

    $session = $this->sessionsRepository->find($id);
    $this->entityManager->remove($session);
    $this->entityManager->flush();

    return new JsonResponse(['ok' => true]);
  }

  #[Route('/api/persistSession', name: 'api_persist_session', methods: ['POST'])]
  public function apiPersistSession(Request $request): JsonResponse
  {
    $body = json_decode($request->getContent(), true);
    $session = new Sessions();

    if (!empty($body["id"]))
      $session = $this->sessionsRepository->find(intval($body["id"]));
    else {
      $user = $this->entityManager->getRepository(Users::class)->find(intval($body["idUser"]));
      $session->setUser($user);
    }

    $course = $this->coursesRepository->find(intval($body["course"]));

    $endTimeMinutes = intval($body["endTime"]);
    $endTime = new \DateTime();
    $endTime->setTime($endTimeMinutes / 60, $endTimeMinutes % 60);

    $startTimeMinutes = intval($body["startTime"]);
    $startTime = new \DateTime();
    $startTime->setTime($startTimeMinutes / 60, $startTimeMinutes % 60);

    $session->setMeetLink($body["meetLink"]);
    $session->setPrice(doubleval($body["price"]));
    $session->setTopics($body["topics"]);
    $session->setCourse($course);
    $session->setDate(new \DateTime($body["date"]));
    $session->setEndTime($endTime);
    $session->setStartTime($startTime);

    $this->entityManager->persist($session);
    $this->entityManager->flush();

    return new JsonResponse(["ok" => true]);
  }

  #[Route('/api/coursesNames', name: 'api_courses_names')]
  public function apiCoursesNames(): JsonResponse
  {
    $courses = $this->coursesRepository->getCourseNames();
    return new JsonResponse($courses);
  }
}
