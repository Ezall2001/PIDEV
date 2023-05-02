<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\Persistence\ManagerRegistry;
use App\Repository\TestsRepository;
use App\Repository\TestResultsRepository;
use App\Repository\TestQsRepository;
use App\Repository\UsersRepository;
use App\Entity\Users;
use App\Entity\TestResults;
use Endroid\QrCode\Builder\BuilderInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Endroid\QrCode\Label\Margin\Margin;
use Symfony\Component\Security\Core\Security;
use DateTime;



use Dompdf\Dompdf as Dompdf;
use Dompdf\Options;

class TestResultController extends AbstractController
{

    #[Route('/check/{id}', name: 'check', methods: "POST")]
    public function testScore(UsersRepository $userRep, TestResultsRepository $resRep, TestQsRepository $qsRepo, Request $request, $id, TestsRepository $testRep, ManagerRegistry $mr, Security $security): Response
    {
        $user = $security->getUser();

        if (!$user) {
            return $this->redirectToRoute('app_login');
        }

        $jsonContent = $request->getContent();
        $data = json_decode($jsonContent, true);
        $responses = $data['responses'];

        $nbCorrectOption = sizeof($responses);
        for ($i = 0; $i < sizeof($responses); $i++) {
            $testQuestion = $qsRepo->find($responses[$i]["id"]);

            if ($responses[$i]["value"] != $testQuestion->getCorrectOption()) {
                $nbCorrectOption--;
            }
        }

        $em = $mr->getManager();
        $result = $resRep->findOneBy(
            [
                'tests' => $id,
                'users' => $user->getId(),
            ]
        );

        if ($result == null) {
            $result = new TestResults();
            $test = $testRep->find($id);

            $result->setUser($user);
            $result->setTest($test);
            $result->setMark($nbCorrectOption);
            $user->setScore($user->getScore() + 50);
            $em->persist($user);

            $em->persist($result);
            $em->flush();
        } else {
            $result->setMark($nbCorrectOption);
            $em->flush();
        }

        return $this->json(['nbCorrectOption' => $nbCorrectOption], 200);
    }

    #[Route('/result/{id}', name: 'resultDownload')]
    public function resultDataDowload(TestResultsRepository $rep, $id, BuilderInterface $qrBuilder, Security $security): Response

    {
        $user = $security->getUser();

        if (!$user) {
            return $this->redirectToRoute('app_login');
        }

        // police default

        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $pdfOptions->setIsRemoteEnabled(true);
        //$pdfOptions->set('chroot', [__DIR__.'/public', __DIR__.'/images']);

        //

        $domPdf = new Dompdf($pdfOptions);
        $context = stream_context_create([
            'ssl' => [
                'verify_peer' => FALSE,
                'verify_peer_name' => FALSE,
                'allow_self_signed' => TRUE
            ]
        ]);

        $qrResult = $qrBuilder
            ->size(400)
            ->margin(20)
            ->data('https://www.facebook.com/profile.php?id=100090591443416&mibextid=ZbWKwL')
            ->build();

        $qrImage = $qrResult->getDataUri();

        $domPdf->setHTTPContext($context);
        $domPdf->set_option('isRemoteEnabled', TRUE);



        $date = new DateTime();
        $formattedDate = $date->format('d/m/yy');

        //  generating html
        $html = $this->renderView('test_result/result.html.twig', [
            'result' => $rep->getUserResult($user->getId(), $id), // ! current user 
            'qrImage' => $qrImage,
            'date' => $formattedDate,
        ]);

        $domPdf->loadHtml($html);
        $domPdf->setPaper('A4', 'landscape');
        $domPdf->render();

        // generate filename

        $fichier = 'certificat.pdf';

        // send pdf to navigator

        $domPdf->stream($fichier, [
            'Attachement' => true
        ]);

        return $this->render('fileName.html.twig');
    }

    #[Route('/admin/getResults', name: 'getResults')]
    public function getAll(TestResultsRepository $rep): Response
    {
        return $this->render('admin/resultsTable.html.twig', [
            'result' => $rep->findAll(),
        ]);
    }


    #[Route('/searchResult', name: 'searchResult', methods: "GET")]
    public function search(TestResultsRepository $rep, Request $req): Response
    {
        $requestString = $req->get('res');
        if (empty($requestString))
            $res = $rep->findAll();
        else
            $res = $rep->findMark($requestString);

        if (!$res) {
            $result['res']['error'] = "had";
        } else {
            $result['res'] = $this->getRealEntities($res);
        }

        return new Response(json_encode($result));
    }

    public function getRealEntities($entities)
    {

        foreach ($entities as $entity) {
            $realEntities[$entity->getId()] = [$entity->getMark(), $entity->getTest()->getCourse()->getName(), $entity->getUser()->getFirstName()];
        }

        return $realEntities;
    }
}
