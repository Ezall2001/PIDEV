<?php

namespace App\Repository;

use App\Entity\Sessions;
use Doctrine\ORM\EntityManagerInterface;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Knp\Component\Pager\PaginatorInterface;
use Knp\Component\Pager\Pagination\PaginationInterface;

class SessionsRepository extends ServiceEntityRepository
{
    private PaginatorInterface $paginator;
    private EntityManagerInterface $entityManager;

    public function __construct(ManagerRegistry $registry, PaginatorInterface $paginator, EntityManagerInterface $entityManager)
    {
        parent::__construct($registry, Sessions::class);
        $this->paginator = $paginator;
        $this->entityManager = $entityManager;
    }

    public function getSessionList($filterObj, $sortObj, $pageObj, $userId, $isAdmin = false): PaginationInterface | array
    {

        $query = $this->createQueryBuilder('s')
            ->select('s', 'c', 'u')
            ->leftJoin('s.course', 'c')
            ->leftJoin('s.user', 'u')
            ->orderBy('s.' . $sortObj['sortBy'], $sortObj['sortDirection']);


        if (!$isAdmin)
            $query
                ->select('s', 'c', 'u', 'p')
                ->andwhere('s.blocked = 0')
                ->leftJoin('s.participations', 'p', 'WITH', 'p.user = ' . $userId);

        if ($filterObj['user'] != null)
            $query->andWhere('s.user = ' . $filterObj['user']);

        if ($filterObj['course'] != null)
            $query->andWhere('c.id = ' . $filterObj['course']);

        if ($filterObj['date'] != null)
            $query->andWhere("s.date = '" . $filterObj['date'] . "'");

        if ($filterObj['baught'] != null) {
            $query->andWhere("p.state = 'ACCEPTED'");
            $query->andWhere("p.user = " . $filterObj['baught']);
        }

        if ($filterObj['priceLimit'] < 100)
            $query->andWhere('s.price <= ' . $filterObj['priceLimit']);

        if ($pageObj == null)
            return $query->getQuery()->getResult();

        $pagination = $this->paginator->paginate(
            $query->getQuery(),
            $pageObj['page'],
            $pageObj['limit']
        );

        return $pagination;
    }

    public function getBaughtSessions(int $userId)
    {
        return $this->createQueryBuilder('s')
            ->select('s', 'c', 'u', 'p')
            ->leftJoin('s.course', 'c')
            ->leftJoin('s.user', 'u')
            ->leftJoin('s.participations', 'p', 'WITH', 'p.user = ' . $userId)
            ->andwhere('s.blocked = 0')
            ->andWhere("p.state = 'ACCEPTED'")
            ->getQuery()
            ->getResult();
    }

    public function getSessionCreators(): array
    {
        $result = $this->createQueryBuilder('s')
            ->select('u.id', 'u.firstName', 'u.lastName')
            ->join('s.user', 'u')
            ->groupBy('u.id')
            ->getQuery()
            ->getResult();

        return $result;
    }

    public function setSessionBlock(int $id, bool $shouldBlock)
    {
        $session = $this->find($id);
        if ($session->getBlocked() != $shouldBlock) {
            $session->setBlocked($shouldBlock);
            $this->entityManager->persist($session);
            $this->entityManager->flush();
        }
    }

    public function getParticipationSession(int $id): ?Sessions
    {
        $result = $this->createQueryBuilder('s')
            ->select('s', 'u')
            ->leftJoin('s.user', 'u')
            ->andWhere('s.id = ' . $id)
            ->getQuery()
            ->getOneOrNullResult();

        return $result;
    }

    public function getPageSession(int $id, int $userId): ?Sessions
    {
        $querry = $this->createQueryBuilder('s');

        $querry
            ->select('s', 'c', 'u', 'r', 'p')
            ->leftJoin('s.course', 'c')
            ->leftJoin('s.user', 'u')
            ->leftJoin('s.resources', 'r', 'WITH', $querry->expr()->notLike('r.filePath', "'server%'"))
            ->leftJoin('s.participations', 'p', 'WITH', 'p.user = ' . $userId)
            ->andWhere('s.id = ' . $id);


        $result = $querry
            ->getQuery()
            ->getOneOrNullResult();

        return $result;
    }

    public function getCalendarSession(int $id): ?Sessions
    {
        return $this->createQueryBuilder('s')
            ->select('s', 'u', 'c')
            ->leftJoin('s.course', 'c')
            ->leftJoin('s.user', 'u')
            ->andWhere('s.id = ' . $id)
            ->getQuery()
            ->getOneOrNullResult();
    }
}
