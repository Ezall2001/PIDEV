<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\TestQsRepository;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: TestQsRepository::class)]
class TestQs
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
   
    private ?int $id = null;

    #[ORM\Column]
    #[Assert\Type(
        type: 'integer',
        message: "Veuillez choisir une valeur numérique.",
    )]
    #[Assert\Positive(message:"le numéro de la question ne doit pas etre négatif ou nul.")]
    #[Assert\NotBlank(message:"Veuillez saisir le numéro de la question.")]
   
    private ?int $questionNumber =null;

    #[ORM\Column(length: 255)]
    #[Assert\Length(
        min: 1,
        max: 50,
        minMessage: "l'option doit etre supérieure à {{ limit }} caractères.",
        maxMessage: "l'option ne doit pas dépasser {{ limit }} caractères.",
    )]
    #[Assert\NotBlank(message:"Veuillez saisir l'option A.")]
   
    private ?string $optiona = null;

    #[ORM\Column(length: 255)]
    #[Assert\Length(
        min: 1,
        max: 50,
        minMessage: "l'option doit etre supérieure à {{ limit }} caractères.",
        maxMessage: "l'option ne doit pas dépasser {{ limit }} caractères.",
    )]
    #[Assert\NotBlank(message:"Veuillez saisir l'option B.")]
    
    private ?string $optionb = null;

    
    #[ORM\Column(length: 255)]
    #[Assert\Length(
        min: 1,
        max: 50,
        minMessage: "l'option doit etre supérieure à {{ limit }} caractères.",
        maxMessage: "l'option ne doit pas dépasser {{ limit }} caractères.",
    )]
    #[Assert\NotBlank(message:"Veuillez saisir l'option C.")]
    private ?string $optionc = null;

    #[ORM\Column(length: 255)]
    #[Assert\Length(
        min: 1,
        max: 50,
        minMessage: "l'option doit etre supérieure à {{ limit }} caractères.",
        maxMessage: "l'option ne doit pas dépasser {{ limit }} caractères.",
    )]
    #[Assert\NotBlank(message:"Veuillez saisir l'option D.")]
   
    private ?string $optiond = null;

    #[ORM\Column(length: 255)]
    #[Assert\Length(
        min: 1,
        max: 50,
        minMessage: "l'option correcte doit etre supérieure à {{ limit }} caractères.",
        maxMessage: "l'option correcte ne doit pas dépasser {{ limit }} caractères.",
    )]

    #[Assert\NotBlank(message:"Veuillez saisir l'option correcte.")]
   
    private ?string $correctOption = null;

    #[ORM\Column(length: 255)]
    #[Assert\Length(
        min: 1,
        max: 50,
        minMessage: "la question doit etre supérieure à {{ limit }} caractères.",
        maxMessage: "la question ne doit pas dépasser {{ limit }} caractères.",
    )]
    #[Assert\NotBlank(message:"Veuillez saisir la question.")]
   
    private ?string $question = null;

    #[ORM\ManyToOne(targetEntity: Tests::class, inversedBy: 'testQs', fetch:'LAZY')]
    #[ORM\JoinColumn(name:'id_test' , referencedColumnName:'id', nullable: false)]
    public $test;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getQuestionNumber(): ?int
    {
        return $this->questionNumber;
    }

    public function setQuestionNumber(int $questionNumber): self
    {
        $this->questionNumber = $questionNumber;

        return $this;
    }

    public function getOptiona(): ?string
    {
        return $this->optiona;
    }

    public function setOptiona(string $optiona): self
    {
        $this->optiona = $optiona;

        return $this;
    }

    public function getOptionb(): ?string
    {
        return $this->optionb;
    }

    public function setOptionb(string $optionb): self
    {
        $this->optionb = $optionb;

        return $this;
    }

    public function getOptionc(): ?string
    {
        return $this->optionc;
    }

    public function setOptionc(string $optionc): self
    {
        $this->optionc = $optionc;

        return $this;
    }

    public function getOptiond(): ?string
    {
        return $this->optiond;
    }

    public function setOptiond(string $optiond): self
    {
        $this->optiond = $optiond;

        return $this;
    }

    public function getCorrectOption(): ?string
    {
        return $this->correctOption;
    }

    public function setCorrectOption(string $correctOption): self
    {
        $this->correctOption = $correctOption;

        return $this;
    }

    public function getQuestion(): ?string
    {
        return $this->question;
    }

    public function setQuestion(string $question): self
    {
        $this->question = $question;

        return $this;
    }

    public function getIdTest(): ?Tests
    {
        return $this->test;
    }

    public function setIdTest(?Tests $test): self
    {
        $this->test = $test;

        return $this;
    }

    public function getTest(): ?Tests
    {
        return $this->test;
    }

    public function setTest(?Tests $test): self
    {
        $this->test = $test;

        return $this;
    }


}
