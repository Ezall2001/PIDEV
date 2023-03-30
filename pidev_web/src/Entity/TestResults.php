<?php

namespace App\Entity;

use Doctrine\Common\Collections\Collection;
use App\Repository\TestResultsRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: TestResultsRepository::class)]
class TestResults
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column]
    private ?int $mark = null;

    #[ORM\OneToOne(targetEntity: Tests::class, inversedBy: 'testResults')]
    private ?Tests $test = null;

    public function getTest(): ?Tests
    {
        return $this->test;
    }

    #[ORM\OneToOne(targetEntity: Users::class, inversedBy: 'testResults')]
    private ?Users $user = null;

    public function getUser(): ?Users
    {
        return $this->user;
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getMark(): ?int
    {
        return $this->mark;
    }

    public function setMark(int $mark): self
    {
        $this->mark = $mark;

        return $this;
    }

    public function setTest(?Tests $test): self
    {
        $this->test = $test;

        return $this;
    }

    public function setUser(?Users $user): self
    {
        $this->user = $user;

        return $this;
    }
}
