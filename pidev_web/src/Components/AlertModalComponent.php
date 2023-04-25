<?php

namespace App\Components;

use Symfony\UX\TwigComponent\Attribute\AsTwigComponent;

#[AsTwigComponent('alertModal')]
class AlertModalComponent
{
  public string $isSuccess;
}
