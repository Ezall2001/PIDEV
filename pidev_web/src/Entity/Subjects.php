<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: SubjectsRepository::class)]


class Subjects
{

    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: 'string', length: 50)]
    private ?string $name =  null;

    #[ORM\Column(type: 'string', length: 500)]
    private ?string $description =  null;

    #[ORM\Column(type: 'string', length: 255)]
    #[Assert\Choice(choices: ['A3', 'B3', 'B2', 'A2', 'A1', 'B1'], message: 'Choose a valid classe_esprit.')]
    private ?string $classes_esprit = null;

    #[ORM\OneToMany(targetEntity: Courses::class, mappedBy: "subjects")]
    private  ?Courses $courses;

    #[ORM\OneToMany(targetEntity: Questions::class, mappedBy: "subjects")]
    private ?Questions $questions;

    #[ORM\OneToMany(targetEntity: Tests::class, mappedBy: "subjects")]
    private Collection $tests;

    public function getTests(): Collection
    {
        return $this->tests;
    }



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

    public function getClassesEsprit(): ?string
    {
        return $this->classes_esprit;
    }

    public function setClassesEsprit(string $classes_esprit): self
    {
        $this->classes_esprit = $classes_esprit;

        return $this;
    }

    public function getCourses(): ?Collection
    {
        return $this->courses;
    }

    public function setCourses(?Collection $courses): self
    {
        $this->courses = $courses;
        return $this;
    }

    public function getQuestions(): ?Collection
    {
        return $this->questions;
    }

    public function setQuestions(?Collection $questions): self
    {
        $this->questions = $questions;
        return $this;
    }
}
