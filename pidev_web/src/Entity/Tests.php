<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\TestsRepository;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: TestsRepository::class)]
class Tests
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("tests")]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Veuillez choisir le type.")]
    #[Assert\Choice(choices: ['COURSE'], message: 'Veuillez saisir le type correctement')]
    #[Groups("tests")]
    private ?string $type = null;

    #[ORM\Column]
    #[Assert\NotBlank(message:"Veuillez saisir le seuil.")]
    #[Assert\Positive(message:"le seuil ne doit pas etre négatif ou null.")]
    #[Groups("tests")]
    private ?int $minPoints = null;

    #[ORM\Column]
    #[Assert\NotBlank(message:"Veuillez saisir la durée.")]
    #[Assert\Positive(message:"la durée ne doit pas etre négative ou nulle.")]
    #[Groups("tests")]
    private ?int $duration = null;

    #[ORM\OneToMany(targetEntity: TestQs::class, mappedBy: 'test', fetch:'EAGER')]
    
    public Collection $testQs;

    #[ORM\ManyToOne(targetEntity: Subjects::class, inversedBy: 'tests', fetch:'EAGER')]
    #[ORM\JoinColumn(name: 'id_subject', referencedColumnName: 'id', nullable: true)]
    
    private ?Subjects $subject;

    #[ORM\OneToOne(targetEntity: TestResults::class, mappedBy: 'tests', fetch:'EAGER')]
    
    private ?TestResults $result = null;

    #[ORM\ManyToOne(targetEntity: Courses::class, inversedBy: 'tests', fetch:'EAGER')]
    
    #[ORM\JoinColumn(name: 'id_course', referencedColumnName: 'id', nullable: true)]
    private ?Courses $course;

    public function __construct()
    {
        $this->testQs = new ArrayCollection();
    }

    /**
     * @return Collection<int, TestQs>
     */

    public function getQuestions(): Collection
    {
        return $this->testQs;
    }

    public function addQuestion(TestQs $testQs): self
    {
        if (!$this->testQs->contains($testQs)) {
            $this->testQs->add($testQs);
            // $question->setClass($this);
        }

        return $this;
    }

    public function removeQuestion(TestQs $testQs): self
    {
        if ($this->question->removeElement($testQs)) {
            // set the owning side to null (unless already changed)
            if ($testQs->getClass() === $this) {
                $testQs->setClass(null);
            }
        }

        return $this;
    }



    public function getId(): ?int
    {
        return $this->id;
    }

    public function setId(int $id): self
    {
        $this->id = $id;
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

    public function getCourse(): ?Courses
    {
        return $this->course;
    }

    public function setCourse(Courses $course): self
    {
        $this->course = $course;

        return $this;
    }

    public function getSubject(): ?Subjects
    {
        return $this->subject;
    }

    public function setSubject(Subjects $subject): self
    {
        $this->subject = $subject;

        return $this;
    }

    public function getResult(): ?TestResults
    {
        return $this->result;
    }

    public function setResult(TestResults $result): self
    {
        $this->result = $result;

        return $this;
    }







}
