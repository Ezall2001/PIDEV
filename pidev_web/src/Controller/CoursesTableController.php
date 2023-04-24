<?php

namespace App\Controller;
use App\Entity\Courses;
use App\Entity\Subjects;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\CourseType;
use Symfony\Component\HttpFoundation\Request;


class CoursesTableController extends AbstractController
{
    #[Route('/', name: 'app_courses')]
    public function index(): Response
    {
        return $this->render('admin/coursesTable.html.twig', [
            'controller_name' => 'CoursesTableController',
        ]);
    }

/**
     * @Route("/afficher_courses", name="displaycourse")
     */
    public function affichercourse(): Response
    {
        $courses = $this->getDoctrine()->getManager()->getRepository(Courses::class)->findAll();
        

        return $this->render('admin/coursesTable.html.twig', [
            'bla'=> $courses,
            
        ]);
    }

    /**
 * @Route("/addcourse", name="addcourse")
 */
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



/**
     * @Route("/modifiercourse/{id}", name="modifiercourse")
     */
    public function modifiercourse(Request $request, $id): Response
    {

        $course = $this->getDoctrine()->getManager()->getRepository(Courses::class)->find($id);
        $form = $this->createForm(CourseType::class, $course);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {


            $em = $this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('displaycourse');
        } else
            return $this->render('admin/modifiercourse.html.twig', ['f' => $form->createView()]);
    }

    /**
     * @Route("/deletecourse", name="deletecourse")
     */
    public function deletecourse(
        Request $request,

    ) {

        $course = $this->getDoctrine()->getRepository(Courses::class)->findOneBy(array('id' => $request->query->get("id")));
        $em = $this->getDoctrine()->getManager();
        $em->remove($course);
        $em->flush();
        $courses = $this->getDoctrine()->getManager()->getRepository(Courses::class)->findAll();
        
        return $this->render('admin/coursesTable.html.twig', [
            'bla'=> $courses,
            
        ]);
    }




}
