<?php

namespace App\Components;

use Symfony\UX\TwigComponent\Attribute\AsTwigComponent;

#[AsTwigComponent('seperator')]
class SeperatorComponent
{
  public string $label;
  public int $spacing = 1;
}
