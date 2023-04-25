<?php

namespace App\Repository;

use App\Entity\Participations;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Participations>
 *
 * @method Participations|null find($id, $lockMode = null, $lockVersion = null)
 * @method Participations|null findOneBy(array $criteria, array $orderBy = null)
 * @method Participations[]    findAll()
 * @method Participations[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ParticipationsRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Participations::class);
    }

    public function getParticipation($sessionId, $userId): ?Participations
    {
        return $this->createQueryBuilder('p')
            ->select('p', 's', 'u')
            ->leftJoin('p.session', 's')
            ->leftJoin('p.user', 'u')
            ->andWhere('p.session = ' . $sessionId)
            ->andWhere('p.user = ' . $userId)
            ->getQuery()
            ->getOneOrNullResult();
    }
}
