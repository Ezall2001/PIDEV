<?php

namespace App\Repository;

use App\Entity\Questions;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Questions>
 *
 * @method Questions|null find($id, $lockMode = null, $lockVersion = null)
 * @method Questions|null findOneBy(array $criteria, array $orderBy = null)
 * @method Questions[]    findAll()
 * @method Questions[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class QuestionsRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Questions::class);
    }

    public function save(Questions $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Questions $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
    public function searchByTitle($keywordsArray)
    {
        $qb = $this->createQueryBuilder('q');
    
        foreach ($keywordsArray as $key => $keyword) {
            $qb->andWhere($qb->expr()->like('q.title', ':keyword'.$key))
               ->setParameter('keyword'.$key, '%'.$keyword.'%');
        }
    
        return $qb->getQuery()->getResult();
    }
    public function findById($id): ?Questions
{
    return $this->createQueryBuilder('q')
        ->andWhere('q.id = :id')
        ->setParameter('id', $id)
        ->getQuery()
        ->getOneOrNullResult();
}
public function searchByTitleAndSubject($title, $subject_id )
{
    $qb = $this->createQueryBuilder('q')
        ->leftJoin('q.subject', 's')
        ->where('q.title LIKE :title')
        ->setParameter('title', '%'.$title.'%');
    
    if ($subject_id ) {
        $qb->andWhere('s.id = :subject_id ')
           ->setParameter('subject_id ', $subject_id );
    }
    
    return $qb->getQuery()->getResult();
}
  

//    /**
//     * @return Questions[] Returns an array of Questions objects
//     */
   public function paginationQuery()
   {
       return $this->createQueryBuilder('q')
         
           ->orderBy('q.id', 'ASC')
           ->getQuery()
         
       ;
   }

//    public function findOneBySomeField($value): ?Questions
//    {
//        return $this->createQueryBuilder('q')
//            ->andWhere('q.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
