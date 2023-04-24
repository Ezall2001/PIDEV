<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SessionController extends AbstractController
{
    #[Route('/sessions', name: 'app_sessions')]
    public function sessionsList(): Response
    {
        return $this->render('sessions/sessionsList.html.twig', [
            'listSize' => 3,
        ]);
    }
}
