<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use App\Repository\CoursesRepository;
use Symfony\Component\Serializer\Annotation\Groups;


#[ORM\Entity(repositoryClass: CoursesRepository::class)]
class Courses
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("sessionFrom")]
    private ?int $id = null;

    #[ORM\Column(type: 'string', length: 500)]
    #[Assert\NotBlank]
    private ?string $name =  null;

    #[ORM\Column(type: 'string', length: 500)]
    #[Assert\NotBlank(message: 'ajouter description')]
    private ?string $description =  null;

    #[ORM\Column(type: 'string', length: 255)]
    #[Assert\NotBlank(message: 'choisir a difficulter')]
    #[Assert\Choice(choices: ['EASY', 'MEDIUM', 'HARD'], message: 'Choose a valid difficulty')]
    private ?string $difficulty = null;

    #[Assert\NotBlank(message: 'choisir a quel matiere')]
    #[ORM\ManyToOne(targetEntity: Subjects::class, inversedBy: 'courses')]
   #[ORM\JoinColumn(name: 'id_subject', referencedColumnName: 'id', nullable: true)]
   private ?Subjects $subject;

    // #[ORM\ManyToOne(targetEntity: Tests::class, inversedBy: 'courses')]
    // #[ORM\JoinColumn(name: '$id_test', referencedColumnName: 'id', nullable: true)]
    // private Collection $tests;

    #[ORM\OneToMany(targetEntity: Sessions::class, mappedBy: 'course')]
    private Collection $sessions;

    public function __construct()
    {
        $this->sessions = new ArrayCollection();
    }




    // public function getTests(): Collection
    // {
    //     return $this->tests;
    // }



    public function getId(): ?int
    {
        return $this->id;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): self
    {
        $this->name = $name;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getDifficulty(): ?string
    {
        return $this->difficulty;
    }

    public function setDifficulty(string $difficulty): self
    {
        $this->difficulty = $difficulty;

        return $this;
    }

    public function getSubject(): ?Subjects
    {
        return $this->subject;
    }

    public function setSubject(?Subjects $subject): self
    {
        $this->subject = $subject;

        return $this;
    }

    // public function setTests(?Collection $tests): self
    // {
    //     $this->tests = $tests;

    //     return $this;
    // }

    /**
     * @return Collection<int, Sessions>
     */
    public function getSessions(): Collection
    {
        return $this->sessions;
    }

    public function addSession(Sessions $session): self
    {
        if (!$this->sessions->contains($session)) {
            $this->sessions->add($session);
            $session->setCourse($this);
        }

        return $this;
    }

    public function removeSession(Sessions $session): self
    {
        if ($this->sessions->removeElement($session)) {
            // set the owning side to null (unless already changed)
            if ($session->getCourse() === $this) {
                $session->setCourse(null);
            }
        }

        return $this;
    }
}
