<?php

namespace App\Controller;

use App\Entity\Subjects;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Form\SubjectType;
use App\Form\SearchFormType;
use App\Repository\SubjectsRepository;
use App\Repository\CoursesRepository;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Doctrine\ORM\QueryBuilder;
use Symfony\Component\HttpFoundation\JsonResponse;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Serializer\Serializer;
use RuntimeException;
use Symfony\Component\Serializer\SerializerInterface as SerializerSerializerInterface;
use Symfony\Component\Messenger\Transport\Serialization\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;


class SubjectsTableController extends AbstractController
{
    #[Route('/subjects', name: 'app_subjects')]
    public function index(): Response
    {
        return $this->render('admin/subjectsTable.html.twig', [
            'controller_name' => 'subjectsTableController',
        ]);
    }

    #[Route('/admin/subjects', name: 'search')]
    public function afficherSubject(Request $request): Response
    {
        //execution de url
        $em = $this->getDoctrine()->getManager();
        $subjects = $em->getRepository(Subjects::class)->findAll();

        $form = $this->createForm(SearchFormType::class);
        $form->handleRequest($request);
        //verification with retrieves values
        if ($form->isSubmitted() && $form->isValid()) {
            $nameSearchTerm = $form->get('name')->getData();
            $classes_espritSearchTerm = $form->get('classes_esprit')->getData();
            $descriptionSearchTerm = $form->get('description')->getData();
            //create a new query builder and select from subbjects
            $qb = $em->createQueryBuilder();
            $qb->select('s')
                ->from(Subjects::class, 's');
            //criteria
            if ($nameSearchTerm) {
                $qb->andWhere('s.name LIKE :nameSearchTerm')
                    ->setParameter('nameSearchTerm', '%' . $nameSearchTerm . '%');
            }

            if ($descriptionSearchTerm) {
                $qb->andWhere('s.description LIKE :descriptionSearchTerm')
                    ->setParameter('descriptionSearchTerm', '%' . $descriptionSearchTerm . '%');
            }

            if ($classes_espritSearchTerm) {
                $qb->andWhere('s.classes_esprit LIKE :classes_espritSearchTerm')
                    ->setParameter('classes_espritSearchTerm', '%' . $classes_espritSearchTerm . '%');
            }


            $subjects = $qb->getQuery()->getResult();

            // Render a different template with search results
            return $this->render('admin/subjects.html.twig', [
                'form' => $form->createView(),
                'bla' => $subjects,
                'subjects' => $subjects,
            ]);
        }

        return $this->render('admin/subjectsTable.html.twig', [
            'form' => $form->createView(),
            'bla' => $subjects,
            'subjects' => $subjects,
        ]);
    }



    #[Route('/admin/addsubject', name: 'addsubject')]

    public function addSubject(Request $request): Response
    {
        $Subject = new Subjects();
        $form = $this->createForm(SubjectType::class, $Subject);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($Subject);
            $em->flush();
            return $this->redirectToRoute('search');
        } else
            return $this->render('admin/addsubject.html.twig', ['f' => $form->createView()]);
    }


    #[Route('/admin/modifiersubject/{id}', name: 'modifiersubject')]

    public function modifiersubject(Request $request, $id): Response
    {

        $subject = $this->getDoctrine()->getManager()->getRepository(Subjects::class)->find($id);
        $form = $this->createForm(SubjectType::class, $subject);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {


            $em = $this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('search');
        } else
            return $this->render('admin/modifiersubject.html.twig', ['f' => $form->createView()]);
    }

    #[Route('/admin/deletesubject', name: 'deletesubject')]

    public function deletesubject(Request $request)
    {

        $subject = $this->getDoctrine()->getRepository(Subjects::class)->findOneBy(array('id' => $request->query->get("id")));
        $em = $this->getDoctrine()->getManager();
        $em->remove($subject);
        $em->flush();
        $form = $this->createForm(SearchFormType::class);
        $form->handleRequest($request);
        $subjects = $this->getDoctrine()->getManager()->getRepository(Subjects::class)->findAll();
        return $this->render('admin/subjectsTable.html.twig', [
            'form' => $form->createView(),
            'bla' => $subjects,
            'subjects' => $subjects,
        ]);
    }


    #[Route('/addsubjectmobile', name: 'addsubjectmobile')]
    public function addSubjectMobile(Request $request, SerializerSerializerInterface $serializer, EntityManagerInterface $manager)
    {
        $Subjects = new Subjects();


        $name = $request->query->get("name");

        $description = $request->query->get("description");
        $classes_esprit = $request->query->get("classes_esprit");

        $sql = "INSERT INTO Subjects`( name`, description, classes_esprit) VALUES 
    ('$name',' $description','$classes_esprit')";
        $stmt = $manager->getConnection()->prepare($sql);
        $result = $stmt->execute();

        return new Response("sucess");
    }

  


  

  
     #[Route('/updatemobile', name: 'updatemobile')]

     public function updatemobile(
       Request $request,
       serializerInterface $serializer,
       EntityManagerInterface $entityManager
   ) {
       $subjectId = $request->query->get("id");
       $Subjects = $this->getDoctrine()->getRepository(Subjects::class)->findOneBy(array('id' => $subjectId));
       if (!$Subjects) {
           return new Response("Subject with ID $subjectId not found");
       }
   
       $Subjects->setName($request->query->get("name"));
       $Subjects->setDescription($request->query->get("description"));
       // $Subjects->setClassesEsprit($request->query->get("classes_esprit"));
   
       $entityManager->persist($Subjects);
       $entityManager->flush();
   
       return new Response("success");
   }

   #[Route('/deletemobile{id}', name: 'deletemobile')]
   public function deletemobile($id, EntityManagerInterface $entityManager)
   {
       $Subjects = $this->getDoctrine()->getRepository(Subjects::class)->find($id);
       $em = $this->getDoctrine()->getManager();
       $em->remove($Subjects);
       $em->flush();
       return new Response("success");
   }



















}
