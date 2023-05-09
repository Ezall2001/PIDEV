<?php

 namespace App\Controller;
use App\Entity\Courses;
use App\Entity\Subjects;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\CourseType;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\ORM\QueryBuilder;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface as SerializerSerializerInterface;
use Symfony\Component\Messenger\Transport\Serialization\SerializerInterface;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;


class CoursesTableController extends AbstractController
{
    #[Route('/', name: 'app_courses')]
    public function index(): Response
    {
        return $this->render('admin/coursesTable.html.twig', [
            'controller_name' => 'CoursesTableController',
        ]);
    }

    #[Route('/admin/afficher_courses', name: 'displaycourse')]
public function affichercourses(Request $request): Response
{
    $entityManager = $this->getDoctrine()->getManager();

    $form = $this->createFormBuilder()
        ->add('sortField', ChoiceType::class, [
            'choices' => [
                'Name' => 'name',
                'Description' => 'description',
                'Difficulty' => 'difficulty'
            ]
        ])
        ->add('sortOrder', ChoiceType::class, [
            'choices' => [
                'Asc' => 'asc',
                'Desc' => 'desc'
            ]
        ])
        ->add('sort', SubmitType::class, [
            'label' => 'Filter',
            'attr' => [
                'class' => 'btn btn-primary'
            ]
        ])
        ->getForm();

    // Check if the form is submitted
    $form->handleRequest($request);
    if ($form->isSubmitted() && $form->isValid()) {
        // Get the sort field and order from the form
        $sortField = $form->get('sortField')->getData();
        $sortOrder = $form->get('sortOrder')->getData();

        // Build the query
        $qb = $entityManager->createQueryBuilder();
        $qb->select('c')
            ->from(Courses::class, 'c')
            ->orderBy('c.'.$sortField, $sortOrder);

        // Update the courses variable with the sorted data
        $courses = $qb->getQuery()->getResult();
    } else {
        $courses = $entityManager->getRepository(Courses::class)->findAll();
    }

    // // Prepare the course data for rendering
    // $courseData = array();
    // foreach ($courses as $course) {
    //     $courseData[] = array(
    //         'name' => $course->getName(),
    //         'description' => $course->getDescription(),
    //         'difficulty' => $course->getDifficulty()
    //     );
    // }

    // // Return the course data as JSON if it's an Ajax request
    // if ($request->isXmlHttpRequest()) {
    //     return new JsonResponse($courseData);
    // }

    // Render the course table with the form
    return $this->render('admin/coursesTable.html.twig', [
        'bla' => $courses,
        'form' => $form->createView()
    ]);
}

    #[Route('/admin/addcourse', name: 'addcourse')]

public function addcourse(Request $request): Response
{
    $course = new Courses();
    $form = $this->createForm(CourseType::class, $course);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $em = $this->getDoctrine()->getManager();
        $subjectId = $form->get('subject')->getData();
        $subject = $em->getRepository(Subjects::class)->find($subjectId);

        $course->setSubject($subject);

        $em->persist($course);
        $em->flush();

        return $this->redirectToRoute('displaycourse');
    }

    return $this->render('admin/addcourse.html.twig', [
        'f' => $form->createView(),
    ]);
}

#[Route('/admin/modifiercourse/{id}', name: 'modifiercourse')]

    public function modifiercourse(Request $request, $id): Response
    {

        $course = $this->getDoctrine()->getManager()->getRepository(Courses::class)->find($id);
        $form = $this->createForm(CourseType::class, $course);
        $form->handleRequest($request);//liee form bil data
        if ($form->isSubmitted() && $form->isValid()) {


            $em = $this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('displaycourse');
        } else
            return $this->render('admin/modifiercourse.html.twig', ['f' => $form->createView()]);
    }

    #[Route('/admin/deletecourse', name: 'deletecourse')]

    public function deletecourse(Request $request ) {

        $course = $this->getDoctrine()->getRepository(Courses::class)->findOneBy(array('id' => $request->query->get("id")));
        $em = $this->getDoctrine()->getManager();
        $em->remove($course);
        $em->flush();
        $courses = $this->getDoctrine()->getManager()->getRepository(Courses::class)->findAll();


        return $this->redirectToRoute('displaycourse');
        // return $this->render('admin/coursesTable.html.twig', [
        //     'bla'=> $courses,
            
        // ]);
    }

    #[Route('/addcoursemobile', name: 'addcoursemobile')]
    public function addCourseMobile(Request $request, SerializerSerializerInterface $serializer, EntityManagerInterface $manager)
    {
        $name = $request->query->get("name");
        $description = $request->query->get("description");
        $difficulty = $request->query->get("difficulty");
        $subjectId = $request->query->get("id_subject");
    
        // Check if the subject exists in the database
        $subject = $manager->getRepository(Subjects::class)->find($subjectId);
        if (!$subject) {
            return new Response(sprintf("Subject with ID %d not found", $subjectId));
        }
    
        // Create a new course and set its properties
        $course = new Courses();
        $course->setName($name);
        $course->setDescription($description);
        $course->setDifficulty($difficulty);
        $course->setSubject($subject);
    
        // Persist the course entity to the database
        $manager->persist($course);
        $manager->flush();
    
        // Return a success response
        return new Response("Success");
    }

   


   

    #[Route('/updatecoursemobile', name: 'updatecoursemobile')]
    public function updateProduit(
        Request $request,
        SerializerInterface $serializer,
        EntityManagerInterface $entityManager
    ) {
        $id = $request->query->get("id");
        $course = $entityManager->getRepository(Courses::class)->findOneBy(['id' => $id]);
        if (!$course) {
            return new Response("Course with ID $id not found");
        }
    
        $course->setName($request->query->get("name"));
        $course->setDescription($request->query->get("description"));
        $course->setDifficulty($request->query->get("difficulty"));
      
    
        $subjectId = $request->query->get("id_subject");
        $subject = $entityManager->getRepository(Subjects::class)->find($subjectId);
        if (!$subject) {
            return new Response(sprintf("Subject with ID %d not found", $subjectId));
        }
        $course->setSubject($subject);
    
        $entityManager->persist($course);
        $entityManager->flush();
    
        return new Response("Success");
    }

    #[Route('/deletecoursemobile', name: 'deletecoursemobile')]
    public function deleteProduitt(Request $request, serializerInterface $serializer, EntityManagerInterface $entityManager)
    {

        $Courses = $this->getDoctrine()->getRepository(Courses::class)->findOneBy(array('id' => $request->query->get("id")));
        $em = $this->getDoctrine()->getManager();
        $em->remove($Courses);
        $em->flush();
        return new Response("success");
    }




}
