<?php

namespace App\Controller;
use App\Entity\TestQs;
use App\Repository\TestQsRepository;
use App\Repository\TestsRepository;
use Doctrine\Persistence\ManagerRegistry;
use App\Form\TestQsType;
use App\Form\SearchType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class TestsQsControllerJSON extends AbstractController
{

    #[Route('/getTestQuestionsJSON', name: 'getTestQuestionsJSON')]
    public function getAll(TestQsRepository $rep,NormalizerInterface $normalizer): Response
    {
        $questions = $rep->findAll();
        $questionsNormalises = $normalizer->normalize($questions,'json',['groups' => "questions"]);
        $json = json_encode($questionsNormalises);
        
         return new Response($json);
    }

    #[Route('/addQuestionJSON/new', name: 'addQuestionJSON')]
    public function add(TestQsRepository $rep,TestsRepository $repo,NormalizerInterface $normalizer,Request $req): Response
    {
       $em = $this->getDoctrine()->getManager();
       $question = new TestQs();
       $idTest=$req->query->get('test');
       $question->setQuestionNumber($req->get('questionNumber'));
       $question->setOptiona($req->get('optiona'));
       $question->setOptionb($req->get('optionb'));
       $question->setOptionc($req->get('optionc'));
       $question->setOptiond($req->get('optiond'));
       $question->setCorrectOption($req->get('correctOption'));
       $question->setQuestion($req->get("question"));
       $question->setTest($repo->find($idTest));
       $em->persist($question);
       $em->flush();

       $jsonContent = $normalizer->normalize($question, 'json', ['groups' => 'questions']);
       return  new Response("ajout avec succès ! " .json_encode($jsonContent));
    }
    
    #[Route('/updateQuestionJSON/{id}', name: 'updateQuestionJSON')]
    public function update( ManagerRegistry $mr,Request $req,$id,TestsRepository $repo,NormalizerInterface $normalizer): Response
    {

        $em = $this->getDoctrine()->getManager();
        $question = $em->getRepository(TestQs::class)->find($id);
        //$idTest=$req->query->get('test');
        $question->setQuestionNumber($req->get('questionNumber'));
        $question->setOptiona($req->get('optiona'));
        $question->setOptionb($req->get('optionb'));
        $question->setOptionc($req->get('optionc'));
        $question->setOptiond($req->get('optiond'));
        $question->setCorrectOption($req->get('correctOption'));
        $question->setQuestion($req->get("question"));
        //$question->setTest($repo->find($idTest));

        $em->persist($question);
        $em->flush();

       $jsonContent = $normalizer->normalize($question, 'json', ['groups' => 'questions']);
       return  new Response("mise à jour avec succès !".json_encode($jsonContent));

    }

    #[Route('/deleteQuestionJSON/{id}', name: 'deleteQuestionJSON')]
    public function delete(TestQs $question,$id,NormalizerInterface $normalizer): Response
    {
        $em = $this->getDoctrine()->getManager();
        $question = $em->getRepository(TestQs::class)->find($id);
        $em->remove($question);
        $em->flush();

        $jsonContent = $normalizer->normalize($question, 'json', ['groups' => 'questions']);
        return  new Response("suppression avec succès !".json_encode($jsonContent));
 
    }

    #[Route('/searchJSON/question={question}', name: 'searchQuestion', methods: 'GET')]
    public function searchJSON(NormalizerInterface $normalizer,Request $req,$question): Response
    {
        $em = $this->getDoctrine()->getManager();
        $results=$em->getRepository(TestQs::class)->searchQuestion($question);
        $jsonContent = $normalizer->normalize($results, 'json', ['groups' => 'questions']);
        return  new Response("Résultats trouvés: ".json_encode($jsonContent));
    }

    }



