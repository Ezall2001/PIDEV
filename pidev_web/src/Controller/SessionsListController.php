<?php

namespace App\Controller;


use App\Repository\SessionsRepository;
use App\Repository\CoursesRepository;
use App\services\MailerService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\InputBag;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\Pagination\PaginationInterface;


class SessionsListController extends AbstractController
{
  private SessionsRepository $sessionsRepository;
  private CoursesRepository $coursesRepository;
  private int $tmpUserId;


  public function __construct(SessionsRepository $sessionsRepository, CoursesRepository $coursesRepository)
  {
    $this->sessionsRepository = $sessionsRepository;
    $this->coursesRepository = $coursesRepository;
    $this->tmpUserId = $_ENV['TMP_USER_ID'];
  }

  private function buildFilterObject(InputBag $queryString, int $userId): array
  {
    $filterObject = [];
    $activeFilters = 0;

    $is_owner = $queryString->get('owner');
    if ($is_owner == '1')
      $filterObject['user'] = $userId;
    else
      $filterObject['user'] =  $queryString->get('creator');

    $is_baught = $queryString->get('baught');
    if ($is_baught == '1')
      $filterObject['baught'] = $userId;
    else
      $filterObject['baught'] = null;

    $priceLimit = $queryString->get('priceLimit');
    if ($priceLimit == null)
      $filterObject['priceLimit'] =  100;
    else
      $filterObject['priceLimit'] = intval($priceLimit);


    $filterObject['course'] = $queryString->get('course');
    $filterObject['date'] = $queryString->get('date');


    foreach ($filterObject as $filter)
      if ($filter != null)
        $activeFilters++;

    if ($filterObject['priceLimit'] == 100)
      $activeFilters--;


    $filterObject['activeFilters'] = $activeFilters;


    return $filterObject;
  }

  private function buildSortObject(InputBag $queryString): array
  {
    $sortObject = [];

    $sortBy = $queryString->get('sortBy');
    $sortDirection = $queryString->get('sortDirection');
    if ($sortBy != 'date')
      $sortBy = 'price';
    if ($sortDirection != 'desc')
      $sortDirection = 'asc';

    $sortObject['sortBy'] = $sortBy;
    $sortObject['sortDirection'] = strtoupper($sortDirection);

    return $sortObject;
  }

  private function buildPagingObject(InputBag $queryString): array
  {
    $pagingObject = [];

    $page = $queryString->get('page');
    $limit = $queryString->get('limit');

    if ($page == null)
      $page = '1';
    if ($limit != '20' and $limit != '50')
      $limit = '10';

    $pagingObject['page'] = intval($page);
    $pagingObject['limit'] = intval($limit);

    return $pagingObject;
  }

  private function buildPaginationUIObject(PaginationInterface $pagination, array $pagingObject): array
  {
    $paginationUI = [];
    $currentPage = $pagination->getCurrentPageNumber();
    $lastPage = intval(ceil($pagination->getTotalItemCount() / $pagination->getItemNumberPerPage()));


    $paginationUI['first'] = 1;
    $paginationUI['last'] = $lastPage;


    if ($lastPage == 1 || $lastPage == 0)
      $paginationUI['pages'] = [1];
    else if ($lastPage  == 2)
      $paginationUI['pages'] = [1, 2];
    else if ($currentPage == 1)
      $paginationUI['pages'] = [1, 2, 3];
    else if ($currentPage == $lastPage)
      $paginationUI['pages'] = [$lastPage - 2, $lastPage - 1, $lastPage];
    else
      $paginationUI['pages'] = [$currentPage - 1, $currentPage, $currentPage + 1];

    $paginationUI['currentPage'] = $pagingObject['page'];
    $paginationUI['limit'] = $pagingObject['limit'];

    return $paginationUI;
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


  #[Route('/sessionsList', name: 'sessions_list')]
  public function sessionsList(Request $request): Response
  {
    $filterObject = $this->buildFilterObject($request->query, $this->tmpUserId);
    $sortObject = $this->buildSortObject($request->query);
    $pagingObject = $this->buildPagingObject($request->query);


    $resultObj = $this->sessionsRepository->getSessionList($filterObject, $sortObject, $pagingObject, $this->tmpUserId);
    $paginationUI = $this->buildPaginationUIObject($resultObj, $pagingObject);
    $sessions = $resultObj->getItems();
    $totalFoundSessions = $resultObj->getTotalItemCount();
    $alertObject = $this->buildAlertObject($request->query);

    $creators = $this->sessionsRepository->getSessionCreators();
    $courses = $this->coursesRepository->getCourseNames();

    return $this->render('sessions/sessionsList.html.twig', [
      'sessions' => $sessions,
      'creators' => $creators,
      'courses' => $courses,
      'totalFoundSessions' => $totalFoundSessions,
      'filters' => $filterObject,
      'userId' => $this->tmpUserId,
      'paginationUI' => $paginationUI,
      'sortObj' => $sortObject,
      'alert' => $alertObject
    ]);
  }

  #[Route('/admin/sessionsList', name: 'admin_sessions_list')]
  public function adminSessionsList(Request $request): Response
  {
    $filterObject = $this->buildFilterObject($request->query, $this->tmpUserId);
    $sortObject = $this->buildSortObject($request->query);
    $pagingObject = $this->buildPagingObject($request->query);


    $resultObj = $this->sessionsRepository->getSessionList($filterObject, $sortObject, $pagingObject, $this->tmpUserId, true);
    $paginationUI = $this->buildPaginationUIObject($resultObj, $pagingObject);
    $sessions = $resultObj->getItems();
    $totalFoundSessions = $resultObj->getTotalItemCount();

    $creators = $this->sessionsRepository->getSessionCreators();
    $courses = $this->coursesRepository->getCourseNames();

    return $this->render('sessions/adminSessionsList.html.twig', [
      'sessions' => $sessions,
      'creators' => $creators,
      'courses' => $courses,
      'totalFoundSessions' => $totalFoundSessions,
      'filters' => $filterObject,
      'paginationUI' => $paginationUI,
      'sortObj' => $sortObject,
    ]);
  }
}
