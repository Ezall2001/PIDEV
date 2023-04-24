<?php

namespace App\Repository;

use App\Entity\Answers;
use App\Entity\Users;
use App\Entity\Votes;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Votes>
 *
 * @method Votes|null find($id, $lockMode = null, $lockVersion = null)
 * @method Votes|null findOneBy(array $criteria, array $orderBy = null)
 * @method Votes[]    findAll()
 * @method Votes[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class VotesRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Votes::class);
    }

    public function save(Votes $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Votes $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
    // public function findByUserIdAndAnswerId(int $userId, int $answerId): ?Votes
    // {
    //     $qb = $this->createQueryBuilder('vote')
    //         ->where('vote.getUser().getId() = :userId')
    //         ->andWhere('vote.getAnswer().getId() = :answerId')
    //         ->setParameter('userId', $userId)
    //         ->setParameter('answerId', $answerId);
    
    //     return $qb->getQuery()->getOneOrNullResult();
    // }
//     public function findByUserAndAnswer(Users $user, Answers $answer): ?Votes
// {
//     $query = $this->createQueryBuilder('v')
//         ->join('v.user', 'u')
//         ->join('v.answer', 'a')
//         ->andWhere('u.id = :userId')
//         ->andWhere('a.id = :answerId')
//         ->setParameter('userId', $user->getId())
//         ->setParameter('answerId', $answer->getId())
//         ->getQuery();

//     return $query->getOneOrNullResult();
// }
// public function findByUserAndAnswer(Users $user, Answers $answer): ?Votes
// {
//     return $this->createQueryBuilder('v')
//         ->andWhere('v.user = :user')
//         ->andWhere('v.answer = :answer')
//         ->setParameter('user', $user)
//         ->setParameter('answer', $answer)
//         ->getQuery()
//         ->getOneOrNullResult();
// }
public function findOneByUserAndAnswer(Users $user, Answers $answer): ?Votes
{
    return $this->createQueryBuilder('v')
        ->andWhere('v.user = :user')
        ->andWhere('v.answer = :answer')
        ->setParameter('user', $user)
        ->setParameter('answer', $answer)
        ->getQuery()
        ->getOneOrNullResult();
}


    

//    /**
//     * @return Votes[] Returns an array of Votes objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('v')
//            ->andWhere('v.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('v.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Votes
//    {
//        return $this->createQueryBuilder('v')
//            ->andWhere('v.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
