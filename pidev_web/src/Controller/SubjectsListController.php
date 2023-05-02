<?php

namespace App\Controller;
use App\Entity\Subjects;
use App\Repository\SubjectsRepository ;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\SerializerInterface;
use App\Form\SearchFormType;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Serializer\Serializer;

class SubjectsListController extends AbstractController
{


    // #[Route('/subjects', name: 'subject_list')]

    // public function list(Request $request,PaginatorInterface $paginator): Response
    // { 
    //     $subjects = $this->getDoctrine()->getRepository(Subjects::class)->findAll();
    //     $subjects = $paginator->paginate(
    //         $subjects, /* query NOT result */
    //         $request->query->getInt('page', 1),
    //         2
    //     );
    //     return $this->render('subjects/subjectsList.html.twig', [
    //         'subjects' => $subjects,
         
    //     ]);   
    // }

    #[Route('/s', name: 'subject_list')]
    public function ajax(Request $request, PaginatorInterface $paginator): Response
    { 
        if ($request->isXmlHttpRequest()) {
            $classesEsprit = $request->query->get('classes_esprit');
            
            $repository = $this->getDoctrine()->getRepository(Subjects::class);
            
            if ($classesEsprit) {
                $subjects = $repository->findBy(['classes_esprit' => $classesEsprit]);
            } else {
                $subjects = $repository->findAll();
            }
            
            $subjects = $paginator->paginate(
                $subjects,
                $request->query->getInt('page', 1),
                2
            );
            
            $response = new JsonResponse();
            $response->setData([
                'html' => $this->renderView('subjects/subjectsList.html.twig', [
                    'subjects' => $subjects
                ])
            ]);
            
            return $response;
        }
        
        // The non-AJAX fallback
        $subjects = $this->getDoctrine()->getRepository(Subjects::class)->findAll();
        $subjects = $paginator->paginate(
            $subjects,
            $request->query->getInt('page', 1),
            2
        );
        
        return $this->render('subjects/list.html.twig', [
            'subjects' => $subjects
        ]);
    }
    
    






}



















