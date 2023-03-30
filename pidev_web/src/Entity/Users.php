<?php

namespace App\Entity;

use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: UsersRapository::class)]
class Users
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]

    private ?int $id;

    #[ORM\Column(length: 100)]

    private ?string $firstName = null;

    #[ORM\Column(length: 100)]
    private ?string $lastName = null;

    #[ORM\Column(length: 500)]
    private ?string $bio = null;

    #[ORM\Column(length: 300)]
    private ?string $avatarPath = null;

    #[ORM\Column]
    private ?int $age;

    #[ORM\Column]
    private ?int $score = 0;

    #[ORM\Column(length: 200)]
    private ?string $email = null;

    #[ORM\Column(length: 1000)]
    private ?string $password = null;

    #[ORM\Column(length: 50)]
    private ?string $type = 'STUDENT';

    #[ORM\Column]
    private ?int $warnings = 0;

    #[ORM\OneToOne(targetEntity: TestResults::class, inversedBy: 'users')]
    #[ORM\JoinColumn(name: "id_user", referencedColumnName: "id", nullable: false)]
    public ?TestResults $result;


    #[ORM\OneToMany(targetEntity: Sessions::class,  mappedBy: "users")]
    public ?Collection $session = null;

    #[ORM\OneToMany(targetEntity: Questions::class, mappedBy: 'users')]
    public ?Collection $question = null;

    #[ORM\OneToMany(targetEntity: Answers::class, mappedBy: 'users')]
    public ?Collection $answer = null;

    #[ORM\OneToMany(targetEntity: Votes::class, mappedBy: 'users')]
    public ?Collection $vote = null;


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getFirstName(): ?string
    {
        return $this->firstName;
    }

    public function setFirstName(string $firstName): self
    {
        $this->firstName = $firstName;

        return $this;
    }

    public function getLastName(): ?string
    {
        return $this->lastName;
    }

    public function setLastName(string $lastName): self
    {
        $this->lastName = $lastName;

        return $this;
    }

    public function getBio(): ?string
    {
        return $this->bio;
    }

    public function setBio(?string $bio): self
    {
        $this->bio = $bio;

        return $this;
    }

    public function getAvatarPath(): ?string
    {
        return $this->avatarPath;
    }

    public function setAvatarPath(?string $avatarPath): self
    {
        $this->avatarPath = $avatarPath;

        return $this;
    }

    public function getAge(): ?int
    {
        return $this->age;
    }

    public function setAge(int $age): self
    {
        $this->age = $age;

        return $this;
    }

    public function getScore(): ?int
    {
        return $this->score;
    }

    public function setScore(int $score): self
    {
        $this->score = $score;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): self
    {
        $this->type = $type;

        return $this;
    }

    public function getWarnings(): ?int
    {
        return $this->warnings;
    }

    public function setWarnings(?int $warnings): self
    {
        $this->warnings = $warnings;

        return $this;
    }

    public function getSessions(): Collection
    {
        return $this->session;
    }
    public function getAnswer(): Collection
    {
        return $this->answer;
    }
    public function getQuestion(): Collection
    {
        return $this->question;
    }
    public function getVote(): Collection
    {
        return $this->vote;
    }
}
