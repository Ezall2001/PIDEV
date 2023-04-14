<?php

namespace App\Controller;
use App\Entity\TestQs;
use App\Repository\TestQsRepository;
use Doctrine\Persistence\ManagerRegistry;
use App\Form\TestQsType;
use Symfony\Component\HttpFoundation\Request;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class TestsQsController extends AbstractController
{
    #[Route('/tests/qs', name: 'app_tests_qs')]
    public function index(): Response
    {
        return $this->render('tests_qs/index.html.twig', [
            'controller_name' => 'TestsQsController',
        ]);
    }

    #[Route('/getTestQuestions', name: 'getTestQuestions')]
    public function getAll(TestQsRepository $rep): Response
    {
        
        return $this->render('tests_qs/questionsList.html.twig', [
            'questions' => $rep->findAll(),
        ]);
    }

    #[Route('/addQuestion', name: 'addQuestion')]
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

        return $this->render('tests_qs/addQs.html.twig', [
            'form'=>$questionForm->createView(),
        ]);
    }

    #[Route('/updateQuestion/{id}', name: 'updateQuestion')]
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

            return $this->render('tests_qs/editTestQs.html.twig',[
           'form' => $form->createView(),
        ]);
    }

    #[Route('/deleteQuestion/{id}', name: 'deleteQuestion')]
    public function delete(TestQs $question): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($question);
        $em->flush();

        return $this->redirectToRoute('getTestQuestions', [
            'id'=>$question->getId(),
        ]);
    }

    #[Route('/findQuestion', name: 'findQuestion')]
    public function searchQuestionByCourseName(TestQsRepository $rep,Request $request): Response
    {
        $name=$request->get('name');
        $result =  $rep->findQuestionsByCourseName($name);
        
        if($request->isMethod("post") && count($result)!=0){
            
            return $this->render('tests_qs/questionsList.html.twig',[
                'questions'=>$result,
            ]);
        }

        return $this->render('tests_qs/questionsList.html.twig', [
            'questions' => $rep->findAll(),
        ]);

    }

}
