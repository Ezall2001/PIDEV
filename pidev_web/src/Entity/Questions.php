<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Entity\Answers;
use Symfony\Component\Validator\Constraints\Date;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;
use App\Repository\QuestionsRepository;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\Validator\Constraints\NotBlank;

#[ORM\Entity(repositoryClass: QuestionsRepository::class)]
class Questions
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id;

    #[ORM\Column(length: 2550, nullable: true)]
	#[Assert\NotBlank(message:" titre doit etre non vide")]
	#[Assert\Length(
		min : 10,
		minMessage:" Entrer un titre au mini de 10 caracteres")]
	
    private ?string $title = null;


    #[ORM\Column(length: 2550, nullable: true)]
	#[Assert\NotBlank(message:" la description doit etre non vide")]
	#[Assert\Length(
		min : 20,
		minMessage:" Entrer une description au mini de 20 caracteres")]

    private ?string $description= null;



    #[ORM\ManyToOne(targetEntity: Subjects::class, inversedBy: 'questions')]
    #[ORM\JoinColumn(name: 'subject_id', referencedColumnName: 'id', nullable: true)]
    #[Assert\NotBlank(message:" vous devez choisir une matiére")]
    private ?Subjects $subject = null;


    #[ORM\ManyToOne(targetEntity: Users::class, inversedBy: 'questions')]
    #[ORM\JoinColumn(name: 'user_id', referencedColumnName: 'id', nullable: true)]
    private ?Users $user = null;

    #[ORM\Column(name: "created_at", type: "datetime")]
    private ?\DateTimeInterface $createdAt = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): self
    {
        $this->title = $title;

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

    public function getSubject(): ?Subjects
    {
        return $this->subject;
    }

    public function setSubject(?Subjects $subject): self
    {
        $this->subject = $subject;

        return $this;
    }

    public function getUser(): ?Users
    {
        return $this->user;
    }

    public function setUser(?Users $user): self
    {
        $this->user = $user;

        return $this;
    }
    public function getCreatedAt(): ?\DateTimeInterface
    {
        return $this->createdAt;
    }

    public function setCreatedAt(\DateTimeInterface $createdAt): self
    {
        $this->createdAt = $createdAt;

        return $this;
    }
    #[ORM\OneToMany(targetEntity: Answers::class, mappedBy: "question")]
    #[Assert\NotBlank(message:" titre doit etre non vide")]
    private Collection $answers;

    public function __construct()
    {
        $this->answers = new ArrayCollection();
    }

    public function getAnswers(): Collection
    {
        return $this->answers;
    }

    public function addAnswer(Answers $answer): self
    {
        if (!$this->answers->contains($answer)) {
            $this->answers[] = $answer;
            $answer->setQuestion($this);
        }

        return $this;
    }

    public function removeAnswer(Answers $answer): self
    {
        if ($this->answers->removeElement($answer)) {
            // set the owning side to null (unless already changed)
            if ($answer->getQuestion() === $this) {
                $answer->setQuestion(null);
            }
        }

        return $this;
    }
    public function __call($name, $arguments) {
        // Vérifie si la méthode appelée commence par "get" et si elle correspond à une propriété de l'entité
        if (substr($name, 0, 3) === 'get') {
            $property = lcfirst(substr($name, 3));
            if (property_exists($this, $property)) {
                return $this->{$property};
            }
        } elseif (substr($name, 0, 3) === 'set') { // Vérifie si la méthode appelée commence par "set" et si elle correspond à une propriété de l'entité
            $property = lcfirst(substr($name, 3));
            if (property_exists($this, $property)) {
                $this->{$property} = $arguments[0];
                return $this;
            }
        }

        // Si la méthode appelée n'est pas reconnue, lance une exception
        throw new \BadMethodCallException("La méthode $name n'existe pas dans cette entité.");
    }

}
