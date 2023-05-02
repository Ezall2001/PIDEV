<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class AppController extends AbstractController
{
  #[Route('/home', name: 'home')]
  public function landing(): Response
  {

    return $this->render('landing/landing.html.twig');
  }
}
