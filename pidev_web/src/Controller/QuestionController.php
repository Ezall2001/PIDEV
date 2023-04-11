<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\AnswerType;
use App\Form\QuestionType;
use App\Form\SessionsInputType;
use App\Repository\AnswerRepository;
use App\Entity\Answers;
use App\Entity\Questions;

class QuestionController extends AbstractController

{
    #[Route('/deleteAnswer/{id}]', name: 'delete_answer')]
    public function deleteAnswer(Answers $answer,Request $request): Response
    {   
       $question = $answer->getQuestion();
        $em= $this->getDoctrine()->getManager();
        $em->remove($answer);
        $em->flush();
        return $this->redirectToRoute('question',[ 'id' => $question->getId(),])  ;    
        
    }
    
    #[Route('/trie/{id}', name: 'trier')]
    public function trier(Request $request, int $id): Response
    {
        $question = $this->getDoctrine()->getRepository(Questions::class)->find($id);
        $sortOrder = $request->query->getInt('sortOrder', 1); // Valeur par défaut = 1
        $answerRepository = $this->getDoctrine()->getRepository(Answers::class);
        $answers = $answerRepository->findBy(['question' => $question], ['createdAt' => $sortOrder == 2 ? 'ASC' : 'DESC']);
        $invalidData=false ;
             
        // Création d'une nouvelle réponse associée à la question
        $answer = new Answers();
        $answer->setQuestion($question);
        $form = $this->createForm(AnswerType::class, $answer);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($answer);
            $em->flush(); //mise a jour
        }
    
        // Création du formulaire pour la question
        $formm = $this->createForm(QuestionType::class, $question);
        $formm->handleRequest($request);
    
        return $this->render('forum/question.html.twig', [
            'answers' => $answers,
            'question' => $question,
            'answerForm' => $form->createView(),
            'sortOrder' => $sortOrder,
            'f' => $formm->createView(),
            'invalidData'=> $invalidData ,
             
        ]);
    }
    

    #[Route('/editAnswerModal/{id}', name: 'editAnswerModal')]
    public function editAnswerModal(Request $request, $id): Response
    { 
     
        $answerRepository = $this->getDoctrine()->getRepository(Answers::class);
        $answer = $answerRepository->find($id);
        $question = $answer->getQuestion();
         $answerForms = $this->createForm(AnswerType::class,$answer);
         $answerForms->handleRequest($request);
        if( $answerForms->isSubmitted() &&  $answerForms->isValid()){
          $em  = $this->getDoctrine()->getManager();
          $em->flush(); //mise a jour 
        

          return $this->redirectToRoute('question', ['id' => $question->getId()]);

        }        
 // Création du formulaire pour la réponse
 $answerForm = $this->createForm(AnswerType::class, $answer);
 $answerForm->handleRequest($request);
         // $answer = $question->getAnswers();
        
      //pour l'envoyer l form et l 'afficher
      return $this->render('forum/question.html.twig',[ 'answer' => $answer, 
      'answers' => $answer ,
      'question' => $question, 
        'answerForms' => $answerForms->createView(),
        'answerForm' => $answerForm->createView(),
        ]);
    }
   
}
