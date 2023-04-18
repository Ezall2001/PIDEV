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

    #[ORM\OneToOne(targetEntity: Tests::class, inversedBy: 'result', fetch:'EAGER')]
    #[ORM\JoinColumn(name: 'id_test', referencedColumnName: 'id', nullable: true)]
    private ?Tests $tests;

    #[ORM\OneToOne(targetEntity: Users::class, inversedBy: 'result', fetch:'EAGER')]
    #[ORM\JoinColumn(name: "id_user", referencedColumnName: "id", nullable: true)]
    public ?Users $users;

    
    public function getTest(): ?Tests
    {
        return $this->tests;
    }


    public function getUser(): ?Users
    {
        return $this->users;
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
        $this->tests = $test;

        return $this;
    }

    public function setUser(?Users $user): self
    {
        $this->users = $user;

        return $this;
    }
}
