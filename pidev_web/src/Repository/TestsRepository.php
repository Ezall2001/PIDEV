<?php

namespace App\Repository;

use App\Entity\Tests;
use App\Entity\TestQs;

use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Tests>
 *
 * @method Tests|null find($id, $lockMode = null, $lockVersion = null)
 * @method Tests|null findOneBy(array $criteria, array $orderBy = null)
 * @method Tests[]    findAll()
 * @method Tests[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class TestsRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Tests::class);
    }

    public function save(Tests $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Tests $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    
    public function getTestQuestions($testId): array
    { 
        $entityManager = $this->getEntityManager();

        $query = $entityManager
        ->createQuery('SELECT q FROM App\Entity\TestQs q JOIN q.test t WHERE t.id = :id ORDER BY q.questionNumber ASC')
        ->setParameter('id', $testId);

        return $query->getResult();
    }

    public function findTestByCourseName($courseName): array
    {
        $entityManager = $this->getEntityManager();

        $query = $entityManager
        ->createQuery('SELECT t, c 
        FROM App\Entity\Tests t 
        JOIN t.course c 
        WHERE c.name = :courseName ')
        ->setParameter('courseName', $courseName);

        return $query->getResult();
    }




//    /**
//     * @return Tests[] Returns an array of Tests objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('t')
//            ->andWhere('t.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('t.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Tests
//    {
//        return $this->createQueryBuilder('t')
//            ->andWhere('t.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
