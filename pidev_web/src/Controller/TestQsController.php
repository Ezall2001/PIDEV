<?php

namespace App\Controller;
use App\Entity\TestQs;
use App\Repository\TestQsRepository;
use Doctrine\Persistence\ManagerRegistry;
use App\Form\TestQsType;
use App\Form\SearchType;
use Symfony\Component\HttpFoundation\Request;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class TestQsController extends AbstractController
{


    #[Route('/admin/addQuestion', name: 'addQuestion')]
    public function add(TestQsRepository $rep, ManagerRegistry $mr,Request $req): Response
    {
        $question = new TestQs();
        $questionForm = $this->createForm(TestQsType::class, $question);
        $questionForm->handleRequest($req);
        
        if ($questionForm->isSubmitted() && $questionForm->isValid()) { 
            $em = $this->getDoctrine()->getManager();
                $em->persist($question);
                $em->flush();
                
                return $this->redirectToRoute('getTestQuestions');
        }

        return $this->render('testsQs/addQs.html.twig', [
            'form'=>$questionForm->createView(),
        ]);
    }
    
    #[Route('/admin/updateQuestionNour/{id}', name: 'updateQuestionNour')]
    public function update( ManagerRegistry $mr,Request $req,$id): Response
    {

        $rep=$mr->getRepository(TestQs::class)->find($id);
        $form = $this->createForm(TestQsType::class, $rep);
        $form->handleRequest($req);
        
        if ($form->isSubmitted() && $form->isValid()) { 
            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('getTestQuestions');
        }

            return $this->render('testsQs/editTestQs.html.twig',[
           'form' => $form->createView(),
        ]);
    }

    #[Route('/admin/getTestQuestions', name: 'getTestQuestions')]
    public function getAll_Sort(Request $req,TestQsRepository $rep): Response
    {
        $questions = new TestQs();
        $questions =$rep->findAll();
        $form = $this->createForm(SearchType::class,null,[
            'method' => 'GET',
        ]);

        $form->handleRequest($req);

        if ($form->isSubmitted() && $form->isValid()) {

            $formData = $form->getData();

            if($formData['name']==null)
            {
                return $this->render('admin/testQsTable.html.twig', [
                    'questions' => $questions,
                    'form' => $form->createView(),
                ]);
            }

        $name = $formData['name']->getName();
        $questions = $rep->findQuestionsByCourseName($name);

          return $this->render('admin/testQsTable.html.twig',[
            'form' => $form->createView(),
            "questions" => $questions,
            ]);

    }
        return $this->render('admin/testQsTable.html.twig', [
            'questions' => $questions,
            'form' => $form->createView(),
        ]);
    }

    #[Route('/admin/deleteQuestionNour/{id}', name: 'deleteQuestionNour')]
    public function delete(TestQsRepository $rep,ManagerRegistry $mr,$id): Response
    {
        $em=$mr->getManager();
        $question=$rep->find($id);
        $em->remove($question);
        $em->flush();

        return $this->redirectToRoute('getTestQuestions');
    }


    #[Route('/tri', name: 'tri',methods: "GET")]
    public function sort(TestQsRepository $rep,Request $request): Response
    {
        $name=$request->get('name');
        if($name=="Nom Cours")
        $questions = $rep->findAll();
        else
        $questions = $rep->findQuestionsByCourseName($name);
        if(!$questions){
            $result['name']['error']="had";
        }else{
            $result['name'] = $this->getRealEntities($questions);
        }

       return new Response(json_encode($result));
    }

    
    public function getRealEntities ($entities)
    {
        foreach ($entities as $entity){
            $realEntities[$entity->getId()] = [ $entity->getQuestion(), $entity->getCorrectOption(), $entity->getOptiona(),$entity->getOptionb(),$entity->getOptionc(),$entity->getOptiond(),$entity->getQuestionNumber(), $entity->getId()  ];
        }
  
        return $realEntities;
    }
    }



