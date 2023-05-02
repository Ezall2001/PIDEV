<?php

namespace App\Controller;

use App\Entity\Tests;
use App\Repository\TestsRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\HttpFoundation\Request;
use App\Form\TestType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class TestsListController extends AbstractController
{


    #[Route('/admin/getTests', name: 'getTests')]
    public function getAll(TestsRepository $rep): Response
    {
         return $this->render('admin/testsTable.html.twig', [
            'tests' => $rep->findAll(),
        ]);
    }

    
    #[Route('/admin/addTest', name: 'addTest')]
    public function add(TestsRepository $rep, ManagerRegistry $mr,Request $req): Response
    {
        $test = new Tests();
        $testForm = $this->createForm(TestType::class, $test);
        $testForm->handleRequest($req);
        if($testForm->isSubmitted() && $testForm->isValid())
        {
            $em = $this->getDoctrine()->getManager();
      
                $em->persist($test);
                $em->flush();
                
                return $this->redirectToRoute('getTests');
        }
      
        return $this->render('tests/addTest.html.twig',[
            'form'=>$testForm->createView(),
        ]);


}

    #[Route('/admin/updateTest/{id}', name: 'updateTest')]
public function update(ManagerRegistry $mr, Request $req, $id): Response
{

    $rep=$mr->getRepository(Tests::class)->find($id);
    $f=$this->createForm(TestType::class, $rep);
    $f->handleRequest($req); // analyser req http

    if($f->isSubmitted() && $f->isValid()){

    $em = $this->getDoctrine()->getManager();
    $em->flush();

    return $this->redirectToRoute('getTests');

}

    return $this->render('tests/editTest.html.twig' ,[
        'form'=>$f->createView(),

    ]);
}

    #[Route('/admin/deleteTest/{id}', name: 'deleteTest')]
    public function delete(ManagerRegistry $mr,$id,TestsRepository $rep): Response
    {
        $em=$mr->getManager();
        $test=$rep->find($id);
        $em->remove($test);
        $em->flush();
         return $this->redirectToRoute('getTests'); 
    }
}
