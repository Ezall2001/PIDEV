<?php

namespace App\Components;

use Symfony\UX\TwigComponent\Attribute\AsTwigComponent;


#[AsTwigComponent('sessionsFilter')]
class SessionsFilterComponent
{
  public array $filterObject;
  public array $creators;
  public array $courses;
  public bool $isAdmin;

  public function mount(array $filterObject, array $creators, array $courses, bool $isAdmin)
  {
    $this->filterObject = $filterObject;
    $this->creators = $creators;
    $this->courses = $courses;
    $this->isAdmin = $isAdmin;
  }
}
