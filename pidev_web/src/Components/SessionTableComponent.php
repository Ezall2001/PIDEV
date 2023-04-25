<?php

namespace App\Components;

use Symfony\UX\TwigComponent\Attribute\AsTwigComponent;

#[AsTwigComponent('sessionTable')]
class SessionTableComponent
{
  public array $sessions;
  public int $limit;
  public int $page;

  public function mount(array $sessions, int $limit, int $page)
  {

    $this->limit = $limit;
    $this->sessions = $sessions;
    $this->page = $page;

    if (sizeof($sessions) < $limit)
      $this->limit = sizeof($sessions);
  }
}
