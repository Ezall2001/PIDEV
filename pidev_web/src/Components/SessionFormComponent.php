<?php

namespace App\Components;

use App\Entity\Sessions;
use Symfony\UX\TwigComponent\Attribute\AsTwigComponent;

#[AsTwigComponent('sessionForm')]
class SessionFormComponent
{
  public bool $isModify;
  public ?Sessions $session = null;

  public function mount(Sessions $session = null)
  {
    $this->session = $session;
  }
}
