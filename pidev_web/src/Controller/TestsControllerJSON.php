<?php

namespace App\Controller;

use App\Entity\Tests;
use App\Repository\TestsRepository;
use App\Repository\TestQsRepository;
use Doctrine\Persistence\ManagerRegistry;
use App\Form\TestsType;
use Symfony\Component\HttpFoundation\Request;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


class TestsControllerJSON extends AbstractController
{
    
    #[Route('/getTestsJSON', name: 'getTestsJSON')]
    public function getAll(TestsRepository $rep,NormalizerInterface $normalizer): Response
    {
        $tests = $rep->findAll();
        $testsNormalises = $normalizer->normalize($tests,'json',['groups' => "tests"]);
        $json = json_encode($testsNormalises);
        
         return new Response($json);
    }

    
    #[Route('/addTestJSON/new', name: 'addTestJSON')]
    public function add(TestsRepository $rep,Request $req,NormalizerInterface $normalizer): Response
    {
       $em = $this->getDoctrine()->getManager();
       $test = new Tests();
       $test->setDuration($req->get('duration'));
       $test->setMinPoints($req->get('minPoints'));
       $test->setType("COURSE");
       $em->persist($test);
       $em->flush();

       $jsonContent = $normalizer->normalize($test, 'json', ['groups' => 'tests']);
       return  new Response("ajout avec succès ! " .json_encode($jsonContent));
    }

#[Route('/updateTestJSON/{id}', name: 'updateTestJSON')]
public function update(Request $req, $id,NormalizerInterface $normalizer): Response
{

   $em = $this->getDoctrine()->getManager();
   $test = $em->getRepository(Tests::class)->find($id);
   $test->setDuration($req->get('duration'));
   $test->setMinPoints($req->get('minPoints'));

    $em->persist($test);
    $em->flush();

       $jsonContent = $normalizer->normalize($test, 'json', ['groups' => 'tests']);
       return  new Response("mise à jour avec succès !".json_encode($jsonContent));

}

 
    #[Route('/deleteTestJSON/{id}', name: 'deleteTestJSON')]
    public function delete(Request $req, $id,NormalizerInterface $normalizer): Response
    {
        $em = $this->getDoctrine()->getManager();
        $test = $em->getRepository(Tests::class)->find($id);
        $em->remove($test);
        $em->flush();

        $jsonContent = $normalizer->normalize($test, 'json', ['groups' => 'tests']);
        return  new Response("suppression avec succès !".json_encode($jsonContent));
 
    }
  
   
    #[Route('/getTestJSON/{id}', name: 'getTestJSON')]
    public function getTestById(TestsRepository $rep,$id, NormalizerInterface $normalizer): Response
    {
        $test = $rep->find($id);
        $testNormalises = $normalizer->normalize($test, 'json', ['groups' => "tests"]);
         return new Response(json_encode($testNormalises));
    }

}