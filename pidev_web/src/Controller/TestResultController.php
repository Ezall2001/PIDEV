<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\TestsRepository;
use App\Repository\TestResultsRepository;
use App\Repository\UsersRepository;

use Dompdf\Dompdf;
use Dompdf\Options;

class TestResultController extends AbstractController
{
    #[Route('/test/result', name: 'app_test_result')]
    public function index(): Response
    {
        return $this->render('test_result/index.html.twig', [
            'controller_name' => 'TestResultController',
        ]);
    }

    
    // #[Route('/insertScore', name: 'insertScore')]
    // public function insertScore($score): Response
    // {
    //     $entityManager = $this->getDoctrine()->getManager();

    //     $user=$userRep->find(530);
    //     $test=$testRep->find(476);

    //     $testRep=$entityManager->getRepository(Tests::class);
    //     $userRep=$entityManager->getRepository(Users::class);
    //     $resultRep=$entityManager->getRepository(Tests::class);

    //     insertScore($score,$user,$test);
        
    //     return $this->render('dummy.html.twig');
    // }

    #[Route('/result', name: 'result')]
    public function resultData(TestResultsRepository $rep): array
    {
        $userid = 530;
        $testId = 476;
        $result = $rep->getUserResult($userid,$testId);
        return $result;
    }

    #[Route('/result/download', name: 'resultDownload')]
    public function resultDataDowload(TestResultsRepository $rep): Response
    {
        $result =$this->resultData($rep);

        // police default
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont','Arial');
        $pdfOptions->setIsRemoteEnabled(true);

        //

        $domPdf = new Dompdf($pdfOptions);
        $context = stream_context_create([
            'ssl' => [ 
                'verify_peer' => FALSE,
                'verify_peer_name' => FALSE,
                'allow_self_signed' => TRUE
            ]
            ]);

        $domPdf->setHTTPContext($context);

        //  generating html

        $html = $this->renderView('passage_test/result.html.twig',[
            'result' => $result,
        ]);

        $domPdf->loadHtml($html);
        $domPdf->setPaper('A4','landscape');
        $domPdf->render();

        // generate filename

        $fichier = 'certificat.pdf';

        // send pdf to navigator

        $domPdf->stream($fichier,[
            'Attachement' => true
        ]);
        
        return new Response();
    }


}
