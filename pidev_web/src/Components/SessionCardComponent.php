<?php

namespace App\Components;

use App\Entity\Sessions;
use Symfony\UX\TwigComponent\Attribute\AsTwigComponent;



#[AsTwigComponent('sessionCard')]
class SessionCardComponent
{
  public static string $defaultSessionImageLink = 'images/defaultSessionImage.jpg';

  public Sessions $session;
  public int $userId;
  public string $actionType = 'participate';



  public function mount(Sessions $session, int $userId)
  {
    if ($session->getImageLink() == null)
      $session->setImageLink($this::$defaultSessionImageLink);
    else
      $session->setImageLink('uploads/' . $session->getUser()->getId() . '/' . $session->getImageLink());

    if ($userId == $session->getUser()->getId())
      $this->actionType = 'creator';
    else if (
      sizeof($session->getParticipations()) > 0
      and ($session->getParticipations()[0])->getState() == 'ACCEPTED'
    )
      $this->actionType = "baught";

    $this->session = $session;
    $this->userId = $userId;
  }
}
