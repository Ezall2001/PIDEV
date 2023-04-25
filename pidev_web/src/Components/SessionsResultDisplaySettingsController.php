<?php

namespace App\Components;

use Symfony\UX\TwigComponent\Attribute\AsTwigComponent;

#[AsTwigComponent('sessionsResultDisplaySettings')]
class SessionsResultDisplaySettingsController
{
  public array $paginationObj;
  public array $sortObj;
  public bool $isAdmin;

  public function mount(array $paginationObj, array $sortObj, bool $isAdmin)
  {
    $this->paginationObj = $paginationObj;
    $this->sortObj = $sortObj;
    $this->isAdmin = $isAdmin;
  }
}
