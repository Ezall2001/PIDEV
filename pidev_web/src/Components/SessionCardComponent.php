<?php

namespace App\Components;

use App\Entity\Sessions;
use Symfony\UX\TwigComponent\Attribute\AsTwigComponent;

use function PHPUnit\Framework\isEmpty;

#[AsTwigComponent('sessionCard')]
class SessionCardComponent
{
  public static string $defaultSessionImageLink = 'images/defaultSessionImage.jpg';

  public Sessions $session;


  public function mount(Sessions $session)
  {
    if ($session->getImageLink() == null)
      $session->setImageLink(SessionCardComponent::$defaultSessionImageLink);


    $this->session = $session;
  }
}
