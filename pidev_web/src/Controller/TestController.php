<?php

namespace App\Controller;


use App\Entity\Tests;
use App\Repository\TestsRepository;
use Doctrine\Persistence\ManagerRegistry;
use App\Form\TestsType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Security\Core\Security;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
class TestController extends AbstractController
{

#[Route('/testQuestions/{id}', name: 'testQuestions')]
public function testQuestions(TestsRepository $rep,$id): array
{
     $test = new Tests();
     $questions=$rep->getTestQuestions($id);

foreach($questions as $question ){
    $test->addQuestion($question);
}
    return $questions;
}


#[Route('/getTest/{id}', name: 'getTest')]
public function getTestById(TestsRepository $rep,$id,Security $security): Response
{
    $user = $security->getUser();

    if (!$user) {
        return $this->redirectToRoute('app_login');
    }

    $test = new Tests();
    $testQuestions = $this->testQuestions($rep,$id);
    $nbQuestion = count($testQuestions);

     return $this->render('tests/passageTest/quiz.html.twig', [
        'test' => $test = $rep->find($id),
        'q' => $testQuestions,
        'nb_questions'=> $nbQuestion,
    ]);
}

#[Route('/getUserTests', name: 'getUserTests')]
public function getAllUserTests(TestsRepository $rep,Security $security): Response
{
    $user = $security->getUser();

        if (!$user) {
            return $this->redirectToRoute('app_login');
        }

     return $this->render('tests/passageTest/tests.html.twig', [
        'user_tests' => $rep->findAll(),
    ]);
}


}
