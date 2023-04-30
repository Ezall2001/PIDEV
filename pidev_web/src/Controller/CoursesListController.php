<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Subjects;
use App\Entity\Courses;
use Dompdf\Dompdf;

use App\Repository\CoursesRepository;
use App\Repository\SubjectsRepository;
use Knp\Component\Pager\PaginatorInterface;

class CoursesListController extends AbstractController
{
    #[Route('/subjects/{subjectId}/courses', name: 'courses_list')]


    public function course(int $subjectId, CoursesRepository $courseRepository, SubjectsRepository $subjectRepository,Request $request,PaginatorInterface $paginator): Response
    {
        $subject = $subjectRepository->find($subjectId);
        if (!$subject) {
            throw $this->createNotFoundException('The subject does not exist');
        }

        $courses = $courseRepository->findBy(['subject' => $subject]);


        $courses = $paginator->paginate(
            $courses, /* query NOT result */
            $request->query->getInt('page', 1),
           1
        );
        return $this->render('courses/coursesList.html.twig', [
            'subject' => $subject,
            'courses' => $courses,
        ]);
    }


    #[Route('/pdf', name: 'pdf')]
   
    public function generatePdf(): Response
    {
        // Render the template with any data you need
        $html = $this->renderView('courses/PDF.html.twig', [
            'title' => 'My PDF Template',
            'body' => '',
         'image_path' => $this->getParameter('kernel.project_dir') . 'public\assets\images\esprit.png',
        ]);
        // Generate the PDF using the Dompdf library
        $dompdf = new Dompdf();
        $dompdf->loadHtml($html);
        $dompdf->setPaper('A4', 'portrait');
        $dompdf->render();
        
        // Return the PDF file as a response
        return new Response($dompdf->output(), 200, [
            'Content-Type' => 'application/pdf',
            'Content-Disposition' => 'inline; filename="document.pdf"'
        ]);
    }
}



     

    



    
      

    

