<?php

namespace App\Components;

use App\Entity\Sessions;
use Symfony\UX\TwigComponent\Attribute\AsTwigComponent;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;



#[AsTwigComponent('sessionListItem')]
class SessionListItemComponent
{
  public static string $defaultSessionImageLink = 'images/defaultSessionImage.jpg';

  public Sessions $session;
  public string $jsonSession;
  public int $userId;



  public function mount(Sessions $session)
  {
    $encoders = [new JsonEncoder()];
    $normalizers = [new ObjectNormalizer()];
    $serializer = new Serializer($normalizers, $encoders);

    if ($session->getImageLink() == null)
      $session->setImageLink(SessionListItemComponent::$defaultSessionImageLink);


    $this->session = $session;
    // $this->jsonSession = $serializer->serialize($session->getSessionFormObj(), "json");
    $this->jsonSession = "";
  }
}
