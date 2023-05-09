<?php

namespace App\Controller;

use App\Entity\Questions;
use App\Entity\Answers;
use App\Entity\Users;
use App\Entity\Subjects;


use App\Repository\QuestionsRepository;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Serializer;








class Questionmobile extends AbstractController

{     #[Route('/list_question', name: 'list_question')]
    public function showQuestions( QuestionsRepository  $questionRepositoy,SerializerInterface  $normlizer)

    { 
         $question= $questionRepositoy->findAll();
  
      $json =  $normlizer->serialize($question,'json',['groups'=>'questions']);
   

    
        return new Response($json);
    }

    #[Route("/question2/{id}", name: "question2")]
    public function StudentId($id, NormalizerInterface $normalizer, QuestionsRepository $repo)
    {
        $student = $repo->find($id);
        
    
      
        $studentNormalises = $normalizer->normalize($student, 'json', ['groups' => "questions"]);
   
        return new Response(json_encode($studentNormalises));
    }
    #[Route("addQuestionJSON/new", name: "addQuestionJSON")]
    public function addQuestionJSON(Request $req,   NormalizerInterface $Normalizer)
    {  $em = $this->getDoctrine()->getManager();
        
        $idsubject = 435;
        $idUser = 529;
        $user = $em->getRepository(Users::class)->find($idUser);
        $subject = $em->getRepository(Subjects::class)->find($idsubject);
    
        $em = $this->getDoctrine()->getManager();
        $question = new Questions();
        $question->setTitle($req->get('title'));
        $question->setDescription($req->get('description'));
        $question->setUser($user);
        $question->setSubject($subject);
        $question->setCreatedAt(new \DateTime());
        $hasProfanity = $this->containsProfanity($question);
    
        if ($hasProfanity) {
            // la chaîne contient un mot interdit
            $response = [
                'containsProfanity' => true,
                'message' => 'La chaine contient un mot interdit'
            ];
            return new JsonResponse($response);
        } else {
            $em->persist($question);
            $em->flush();
            $jsonContent = $Normalizer->normalize($question, 'json', ['groups' => 'questions']);
            $response2 = [
                'containsProfanity' => false,
                'message' =>  $jsonContent
            ];
            return new JsonResponse($response2);
        }
    }

    #[Route("updateQuestionJSON/{id}", name: "updateQuestionJSON")]
    public function updateStudentJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $student = $em->getRepository(Questions::class)->find($id);
        $student->setTitle($req->get('title'));
        $student->setDescription($req->get('description'));
 
        $em->flush();

        $jsonContent = $Normalizer->normalize($student, 'json', ['groups' => 'questions']);
        return new Response("question updated successfully " . json_encode($jsonContent));
    }
    #[Route("deleteQuestionJSON/{id}", name: "deleteQuestionJSON")]
    public function deleteStudentJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $student = $em->getRepository(Questions::class)->find($id);
        $em->remove($student);
        $em->flush();
        $jsonContent = $Normalizer->normalize($student, 'json', ['groups' => 'questions']);
        return new Response("Student deleted successfully " . json_encode($jsonContent));
    }

    #[Route("deleteAnswerJSON/{id}", name: "deleteAnswerJSON")]
    public function deleteAnswerJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $student = $em->getRepository(Answers::class)->find($id);
        $em->remove($student);
        $em->flush();
        $jsonContent = $Normalizer->normalize($student, 'json', ['groups' => 'answers']);
        return new Response("Student deleted successfully " . json_encode($jsonContent));
    }



    #[Route('/answer/{id}', name: 'answer')]
    public function showAnswer(QuestionsRepository $questionRepository, NormalizerInterface $normalizer,$id,Request $request): Response
    {
        $question = $questionRepository->findById($id);
     
       
   
        $question = $this->getDoctrine()->getRepository(Questions::class)->find($id);
     
         
           

        $answers = $question->getAnswers();
             
             
        
        $answerNormalises = $normalizer->normalize($answers, 'json', ['groups' => "answers"]);
   
        return new Response(json_encode($answerNormalises));
        

  
    }


    #[Route('/addAnswer/{id}', name: 'addAnswer')]
    public function AddAnswer(QuestionsRepository $questionRepository,$id,Request $req,   NormalizerInterface $Normalizer): Response
    {

        $question = $questionRepository->findById($id);
      
        $em = $this->getDoctrine()->getManager();
        
        $idUser = 529;
        $user = $em->getRepository(Users::class)->find($idUser);
             // Création d'une nouvelle réponse associée à la question
             $answers = new Answers();
             $answers->setCreatedAt(new \DateTime()); 
             $answers->setQuestion($question);
             $answers->setUser( $user);
             $answers->setMessage($req->get('message'));
           
               $em->persist($answers);
               $em->flush();
            
             
               $jsonContent = $Normalizer->normalize($answers, 'json', ['groups' => 'answers']);
        return new Response(json_encode($jsonContent));    
  
 
           
}
             
#[Route('/search1', name: 'app_search1_question')]
public function search1(Request $request,SerializerInterface  $normlizer)
{
   
    $keywords = $request->query->get('keywords');
  
    

    $qb = $this->getDoctrine()->getRepository(Questions::class)->createQueryBuilder('q'); //creer un objet query builder 
    






    // Recherche par titre
    if ($keywords) {
        $keywordsArray = explode(' ', $keywords);
        $qb->andWhere($qb->expr()->like('q.title', ':keywords')); //filtrer les resutltat selon title
        $qb->setParameter('keywords', '%' . implode('%', $keywordsArray) . '%'); //concaténation des element de tab
    }
    $questions = $qb->getQuery()->getResult();
    $json =  $normlizer->serialize($questions,'json',['groups'=>'questions']);
   

    
    return new Response($json);

}
//trie 
#[Route('/trie2', name: 'trie2')]
public function trie2(Request $request, SerializerInterface  $normlizer ): Response
{
 
    $sortOrder = $request->query->getInt('sortOrder', 1); // Valeur par défaut = 1

    $questionRepository = $this->getDoctrine()->getRepository(Questions::class);
    $query = $questionRepository->createQueryBuilder('q')
    ->orderBy('q.createdAt', $sortOrder == 2 ? 'ASC' : 'DESC')
    ->getQuery();
  

        $questions = $query->getResult();
        $json =  $normlizer->serialize($questions,'json',['groups'=>'questions']);
   

    
        return new Response($json);
   
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