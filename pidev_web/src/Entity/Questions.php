<?php

namespace App\Entity;

use App\Repository\QuestionsRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: QuestionsRepository::class)]
class Questions
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("questions")]
    private ?int $id = null;

    #[ORM\Column(length: 2550, nullable: true)]
    #[Assert\NotBlank(message:"Le titre doit être renseigné.")]
    #[Assert\Length(
        min : 10,
        minMessage:"Le titre doit contenir au minimum 10 caractères.")]
    #[Groups("questions")]
    private ?string $title = null;

    #[ORM\Column(length: 2550, nullable: true)]
    #[Assert\NotBlank(message:"La description doit être renseignée.")]
    #[Assert\Length(
        min : 20,
        minMessage:"La description doit contenir au minimum 20 caractères.")]
    #[Groups("questions")]
    private ?string $description = null;

    #[ORM\ManyToOne(targetEntity: Subjects::class, inversedBy: 'questions')]
    #[ORM\JoinColumn(name: 'subject_id', referencedColumnName: 'id', nullable: true)]
    #[Assert\NotBlank(message:"Vous devez choisir une matière.")]
    #[Groups("questions")]
    private ?Subjects $subject = null;

    #[ORM\ManyToOne(targetEntity: Users::class, inversedBy: 'questions')]
    #[ORM\JoinColumn(name: 'user_id', referencedColumnName: 'id', nullable: true)]
    #[Groups("questions")]
    private ?Users $user = null;

    #[ORM\Column(name: "created_at", type: "datetime")]

    private ?\DateTimeInterface $createdAt = null;

    #[ORM\OneToMany(targetEntity: Answers::class, mappedBy: "question")]
    #[Assert\NotBlank(message:"Au moins une réponse est requise.")]
    #[Groups("questions")]
    private Collection $answers;

    public function __construct()
    {
        $this->answers = new ArrayCollection();
    }

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