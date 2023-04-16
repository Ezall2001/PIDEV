<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\TestsRepository;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: TestsRepository::class)]
class Tests
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Veuillez choisir le type.")]
    #[Assert\Choice(choices: ['COURSE'], message: 'Veuillez saisir le type correctement')]
    
    private ?string $type = null;

    #[ORM\Column]
    #[Assert\NotBlank(message:"Veuillez saisir le seuil.")]
    #[Assert\Positive(message:"le seuil ne doit pas etre négatif ou null.")]
    
    private ?int $minPoints = null;

    #[ORM\Column]
    #[Assert\NotBlank(message:"Veuillez saisir la durée.")]
    #[Assert\Positive(message:"la durée ne doit pas etre négative ou nulle.")]
    
    private ?int $duration = null;

    #[ORM\OneToMany(targetEntity: TestQs::class, mappedBy: 'test', fetch:'EAGER')]
    
    public Collection $testQs;

    #[ORM\ManyToOne(targetEntity: Subjects::class, inversedBy: 'tests')]
    #[ORM\JoinColumn(name: 'id_subject', referencedColumnName: 'id', nullable: true)]
    
    private ?Subjects $subject;

    #[ORM\OneToOne(targetEntity: TestResults::class, mappedBy: 'tests')]
    
    private ?TestResults $result = null;

    #[ORM\ManyToOne(targetEntity: Courses::class, inversedBy: 'tests')]
    
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

    public function addQuestion(TestQs $question): self
    {
        if (!$this->testQs->contains($question)) {
            $this->testQs->add($question);
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




}
