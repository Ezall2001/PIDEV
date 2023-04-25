<?php

namespace App\Components;

use App\Entity\Sessions;
use Symfony\UX\TwigComponent\Attribute\AsTwigComponent;

#[AsTwigComponent('sessionForm')]
class SessionFormComponent
{
  public array $courses;

  public function mount(array $courses)
  {
    $this->courses = $courses;
  }
}
