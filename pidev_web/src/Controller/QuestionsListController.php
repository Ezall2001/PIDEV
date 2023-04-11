<?php

namespace App\Controller;

use App\Entity\Answers;
use App\Entity\Questions;
use App\Form\AnswerType;
use App\Form\QuestionType;
use App\Repository\AnswersRepository;
use App\Repository\QuestionsRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\FormError;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;





class QuestionsListController extends AbstractController

{

    #[Route('/', name: 'app_question')]
    public function index(): Response
    { $question= $this->getDoctrine()->getManager()->getRepository(Questions::class)->findAll();
        $allIds = "no";
        return $this->render('forum/questionsList.html.twig', [
            'questions'=>$question,
            'answerId' =>  $allIds
        ]);
    }
    #[Route('/search', name: 'app_search_question')]
    public function search(Request $request)
    {
        $keywords = $request->query->get('keywords');
    
        // // Vérifie si le paramètre "keywords" est vide
        // if (empty($keywords)) {
        //     // Redirige l'utilisateur vers la page de recherche avec un message d'erreur
        //     $this->addFlash('error', 'Veuillez entrer des mots clés pour votre recherche.');
        //     return $this->redirectToRoute('app_question_index');
        // }
    
        $keywordsArray = explode(' ', $keywords); // Transforme la chaîne de caractères en un tableau de mots-clés
    
        $questions = $this->getDoctrine()
                          ->getRepository(Questions::class)
                          ->searchByTitle($keywordsArray);
    
        return $this->render('forum/questionsList.html.twig', [
            'questions' => $questions,
        ]);
    }

    #[Route('/trie', name: 'trie')]
    public function trie(Request $request): Response
    {
        $sortOrder = $request->query->getInt('sortOrder', 1); // Valeur par défaut = 1
    
        $questionRepository = $this->getDoctrine()->getRepository(Questions::class);
    
        if ($sortOrder == 2) {
            $questions = $questionRepository->findBy([], ['createdAt' => 'ASC']);
        } else {
            $questions = $questionRepository->findBy([], ['createdAt' => 'DESC']);
        }
    
        return $this->render('forum/questionsList.html.twig', [
            'questions' => $questions,
            'sortOrder' => $sortOrder
        ]);
    }

    #[Route('/addQuestion', name: 'addQuestion')]
    public function addQuestion(Request $request): Response
    { $question = new Questions();
        $question->setCreatedAt(new \DateTime()); // ajouter la date de création
        $form = $this->createForm(QuestionType::class,$question);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
          $em  = $this->getDoctrine()->getManager();
          $em->persist($question);
          $em->flush(); //mise a jour 
          return $this->redirectToRoute('app_question');
          
        }
        return $this->render('forum/questionIntput.html.twig',['f'=>$form->createView()]); //pour l'envoyer l form et l 'afficher
        
    }
    #[Route('/deleteQuestion/{id}', name: 'delete_question')]
    public function deleteQuestion(Questions $question): Response
    {  $em= $this->getDoctrine()->getManager();
        $em->remove($question);
        $em->flush();
        return $this->redirectToRoute('app_question')  ;    
        
    }


    #[Route('/question/{id}', name: 'question')]
    public function showQuestion(QuestionsRepository $questionRepository, $id,Request $request): Response
    {
        $question = $questionRepository->findById($id);
        $question = $this->getDoctrine()->getRepository(Questions::class)->find($id);
        $form = $this->createForm(QuestionType::class,$question);
        $form->handleRequest($request);
        $invalidData = false;
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->flush(); // mise à jour
            return $this->redirectToRoute('question', ['id' => $id]);
            
        } 
        if ($form->isSubmitted() && !$form->isValid()) {
            $invalidData = true;
                
                // Ajoute chaque message d'erreur à la session flash
                $errors = $form->getErrors(true, false); 
                foreach ($errors as $error) {
                    if ($error instanceof FormError) { // Vérifier si l'erreur est une instance de FormError
                        $this->addFlash('error', $error->getMessage()); // Ajouter chaque message d'erreur à la session flash
                    }
                }
                return $this->redirectToRoute('question', ['id' => $id]);
            }        
    
        if (!$question) {
            throw $this->createNotFoundException('Question not found');
        }
        
             // Création d'une nouvelle réponse associée à la question
             $answer = new Answers();
             $answer->setCreatedAt(new \DateTime()); 
             $answer->setQuestion($question);
     
             // Création du formulaire pour la réponse
             $answerForm = $this->createForm(AnswerType::class, $answer);
             $answerForm->handleRequest($request);
             if ( $answerForm->isSubmitted() &&  $answerForm->isValid()) {
                 $em  = $this->getDoctrine()->getManager();
               $em->persist($answer);
               $em->flush();
                
              
                 // Redirection vers la page de la question avec un message de succès
               
                 return $this->redirectToRoute('question',['id'=>$id,] , ) ;
                      
                
             }
         
             $answer = $question->getAnswers();
         
        $answerr = $this->getDoctrine()->getManager()->getRepository(Answers::class)->find($id); 
     
    //modifier
 
 
           
             return $this->render('forum/question.html.twig', [
                 'question' => $question,
                 'answers' => $answer,
                 'answerForm' => $answerForm->createView(),
                 'f' => $form->createView(),
                 'answerForms'=>$form->createView(),
                 'invalidData'=> $invalidData ,
             
               
                     
             
             ]);

}

#[Route('/editQuestionModal/{id}', name: 'editQuestionModal')]
public function editQuestionModal(Request $request, $id): Response
{ 
    $question = $this->getDoctrine()->getManager()->getRepository(Questions::class)->find($id);
    $form = $this->createForm(QuestionType::class,$question);
    $form->handleRequest($request);
    $invalidData = false;
  if ($form->isSubmitted() && $form->isValid()) {
    $em = $this->getDoctrine()->getManager();
    $em->flush(); // mise à jour
    return $this->redirectToRoute('question', ['id' => $id]);

    
    
} 
if ($form->isSubmitted() && !$form->isValid()) {
    $invalidData = true;
        
        // Ajoute chaque message d'erreur à la session flash
        return new JsonResponse(['error' => 'There are errors in the form.']);
     
        
    }
       // Création d'une nouvelle réponse associée à la question
       $answer = new Answers();
       $answer->setCreatedAt(new \DateTime()); 
       $answer->setQuestion($question);
    $answerForm = $this->createForm(AnswerType::class, $answer);
    $answerForm->handleRequest($request);

    return $this->render('forum/question.html.twig',[  
 
      'question' => $question, 
      'f' => $form->createView(),
      'answers' => $answer,
      'answerForm' => $answerForm->createView(),
    'invalidData'=> $invalidData ,
       
        ]);
}
public function submitForm(Request $request, $form)
{ 
    if ($form->isSubmitted() && !$form->isValid()) {
        // If the form is not valid, return an error message
       // return $this->redirectToRoute('question', ['id' => $id]);
    }
    

    // Otherwise, return a success message
   
}





}
