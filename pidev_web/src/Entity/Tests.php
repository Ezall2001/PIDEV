<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use App\Repository\TestsRepository;

use Doctrine\ORM\Mapping as ORM;
use PHPUnit\Framework\TestResult;

#[ORM\Entity(repositoryClass: TestsRepository::class)]

class Tests
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 150)]
    private ?string $type = null;

    #[ORM\Column]
    private ?int $minPoints = null;

    #[ORM\Column]
    private ?int $duration = null;


    #[ORM\ManyToOne(targetEntity: Subjects::class, inversedBy: 'tests')]
    #[ORM\JoinColumn(name: "subject_id", referencedColumnName: "id", nullable: false)]
    public ?Subjects $subject;

    #[ORM\ManyToOne(targetEntity: Courses::class, inversedBy: 'tests')]
    #[ORM\JoinColumn(name: "course_id", referencedColumnName: "id", nullable: false)]
    public ?Courses $course;

    #[ORM\ManyToOne(targetEntity: TestResults::class, inversedBy: 'tests')]
    #[ORM\JoinColumn(name: "id_test", referencedColumnName: "id", nullable: false)]
    public ?TestResults $result;

    #[ORM\OneToMany(targetEntity: TestQs::class, mappedBy: 'tests')]
    public Collection $questions;

    public function __construct()
    {
        $this->questions = new ArrayCollection();
    }

    public function getQuestions(): Collection
    {
        return $this->questions;
    }


    public function getId(): ?int
    {
        return $this->id;
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

    public function getMinPoints(): ?int
    {
        return $this->minPoints;
    }

    public function setMinPoints(int $minPoints): self
    {
        $this->minPoints = $minPoints;

        return $this;
    }

    public function getDuration(): ?int
    {
        return $this->duration;
    }

    public function setDuration(int $duration): self
    {
        $this->duration = $duration;

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

    public function getCourse(): ?Courses
    {
        return $this->course;
    }

    public function setCourse(?Courses $course): self
    {
        $this->course = $course;

        return $this;
    }

    public function getResult(): ?TestResults
    {
        return $this->result;
    }

    public function setResult(?TestResults $result): self
    {
        $this->result = $result;

        return $this;
    }

    public function addQuestion(TestQs $question): self
    {
        if (!$this->questions->contains($question)) {
            $this->questions->add($question);
            $question->setTest($this);
        }

        return $this;
    }

    public function removeQuestion(TestQs $question): self
    {
        if ($this->questions->removeElement($question)) {
            // set the owning side to null (unless already changed)
            if ($question->getTest() === $this) {
                $question->setTest(null);
            }
        }

        return $this;
    }
}
