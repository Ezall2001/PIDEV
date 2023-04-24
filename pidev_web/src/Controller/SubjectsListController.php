<?php



namespace App\Controller;

use App\Entity\Subjects;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SubjectsListController extends AbstractController
{
    /**
     * @Route("/subjects", name="subject_list")
     */
    public function list(): Response
    {
        $subjects = $this->getDoctrine()->getRepository(Subjects::class)->findAll();

        return $this->render('front/show.html.twig', [
            'subjects' => $subjects,
        ]);
    }

    /**
     * @Route("/subjects/{id}", name="subject_show")
     */
    public function show(Subjects $subject): Response
    {
        return $this->redirectToRoute('subject_show', ['id' => $subject->getId()]);
    }
}





