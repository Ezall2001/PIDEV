<?php

namespace App\Controller;

use App\Entity\Votes;
use App\Repository\VotesRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\AnswerType;
use App\Form\QuestionType;
use App\Entity\Answers;
use App\Entity\Users;
use App\Entity\Questions;
use ConsoleTVs\Profanity\Facades\Profanity;
use Symfony\Component\Security\Core\Security;


class QuestionController extends AbstractController

{
    #[Route('/deleteAnswer/{id}]', name: 'delete_answer')]
    public function deleteAnswer(int $id,Request $request): Response
    {  $answer= $this->getDoctrine()->getManager()->getRepository(Answers::class)->find($id);
       $question = $answer->getQuestion();
        $em= $this->getDoctrine()->getManager();
        $em->remove($answer);
        $em->flush();
        return $this->redirectToRoute('question',[ 'id' => $question->getId(),])  ;    
        
    }
    
    #[Route('/trie/{id}', name: 'trier')]
    public function trier(Request $request, int $id,VotesRepository $voteRepository,Security $security): Response
    {$user = $security->getUser();
        $userVotes= 0;

        if (!$user) {
            return $this->redirectToRoute('app_login');
        }
        $question = $this->getDoctrine()->getRepository(Questions::class)->find($id);
        $id_user=$user->getId();
    
        $sortOrder = $request->query->getInt('sortOrder', 1); // Valeur par défaut = 1
        $answerRepository = $this->getDoctrine()->getRepository(Answers::class);
        $answers = $answerRepository->findBy(['question' => $question], ['createdAt' => $sortOrder == 2 ? 'ASC' : 'DESC']);
        $answers = $answerRepository->findBy(['question' => $question], ['voteNb' => $sortOrder == 2 ? 'ASC' : 'DESC']);
        
        $invalidData=false ;
             
        // Création d'une nouvelle réponse associée à la question
        $answer = new Answers();
          
        $answer->setUser($user);  
        $answer->setQuestion($question);
        $form = $this->createForm(AnswerType::class, $answer);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $hasProfanity = $this->containsProfanity2($answer );
    
            if ($hasProfanity) {
                // la chaîne contient un mot interdit
                //return new JsonResponse(['message' => 'La chaîne contient un mot interdit']);
              
                return $this->redirectToRoute('question', ['id'=>$id, 'containsProfanity' => $hasProfanity]);
        
        
            }
            $em = $this->getDoctrine()->getManager();
            $em->persist($answer);
            $em->flush(); //mise a jour
            $userVotes = $voteRepository->findVoteByiduser($id_user, $answer->getId());
        }
    
        // Création du formulaire pour la question
    
        $formm = $this->createForm(QuestionType::class, $question);
        $formm->handleRequest($request);
        if ($formm->isSubmitted() && !$formm->isValid()) {
            $invalidData = true;
        
          
            return $this->redirectToRoute('question', ['id'=>$id,'contains' => true]);
        }
    
        
          
        if ($formm->isSubmitted() && $formm->isValid()) {
            $hasProfanity = $this->containsProfanity($question );
    
            if ($hasProfanity) {
                // la chaîne contient un mot interdit
                //return new JsonResponse(['message' => 'La chaîne contient un mot interdit']);
              
                return $this->redirectToRoute('question', ['id'=>$id, 'containsP' => $hasProfanity]);
        
        
            }
            $em = $this->getDoctrine()->getManager();
            $em->persist($question);
            $em->flush(); //mise a jour
        }
        $answers = $this->getDoctrine()->getManager()->getRepository(answers::class)->findAll();
        if($answers== null){
            $v=0;
        }else{
        foreach ($answers as $a) {
           $v=0;
            $userVotes = $voteRepository->findByVoteByiduser($user->getId(), $a->getId());
            if($userVotes == null){
                $v=0;
            }else{ $v= $userVotes->getUser()->getId();}
          
               
                
              
        
            // faire quelque chose avec $userVotes pour cette réponse
        }}
        return $this->render('forum/question.html.twig', [
            'answers' => $answers,
            'question' => $question,
            'answerForm' => $form->createView(),
            'sortOrder' => $sortOrder,
            'f' => $formm->createView(),
            'invalidData'=> $invalidData ,
            "user"=>$user,
            "userid"=>$user->getId(),
            'userVotes'=>$v,
            
             
        ]);
    }
    
    public function containsProfanity2(Answers $answer)
    {
       
        $words = $answer->getMessage(); // or any other property that contains the text to check for profanity
        $clean_words = \ConsoleTVs\Profanity\Builder::blocker($words)->filter();
       
      

        return $clean_words !== $words   ;
    }
    public function containsProfanity(Questions $question)
    {
        $words1 = $question->getTitle();
        $words = $question->getDescription(); // or any other property that contains the text to check for profanity
        $clean_words = \ConsoleTVs\Profanity\Builder::blocker($words)->filter();
        $clean_words1 = \ConsoleTVs\Profanity\Builder::blocker($words1)->filter();
      

        return $clean_words !== $words || $clean_words1 !== $words1  ;
    }

    

   
}