<?php

namespace App\Entity;

use App\Repository\VotesRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

use phpDocumentor\Reflection\Types\Boolean;
use Symfony\Component\Validator\Constraints\Date;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Form\FormTypeInterface;
use App\Repository\AnswersRepository;
use App\Entity\Questions;
use App\Entity\Votes;
use Symfony\Component\Validator\Constraints as Assert;


#[ORM\Entity(repositoryClass: AnswersRepository::class)]
class Answers
{
   

    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id;



    #[ORM\Column(length: 150, nullable: true)]
    #[Assert\NotBlank(message:"la réponse doit être non vide")]
    #[Assert\Length(
        min : 5,
        minMessage:"Entrer une réponse au minimum de 20 caractères"
    )]
    private ?string $message = null;

    #[ORM\Column]
    private ?int $voteNb = 0;



    #[ORM\ManyToOne(targetEntity: Users::class, inversedBy: 'answers')]
    #[ORM\JoinColumn(name: 'user_id', referencedColumnName: 'id', nullable: true)]
    private ?Users $user = null;

    #[ORM\OneToMany(targetEntity: Votes::class, mappedBy: "answers")]
 
    private Collection $votes;
    public function __construct()
    {
        $this->votes = new ArrayCollection();
    }



    #[ORM\ManyToOne(targetEntity: Questions::class, inversedBy: 'answers')]
    #[ORM\JoinColumn(name: 'question_id', referencedColumnName: 'id', nullable: true)]
    private ?Questions $question = null;


    #[ORM\Column(name: "created_at", type: "datetime")]
    private ?\DateTimeInterface $createdAt = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getMessage(): ?string
    {
        return $this->message;
    }

    public function setMessage(?string $message): self
    {
        $this->message = $message;

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


    public function getVoteNb() :?int
    {
      
        return $this->voteNb ;
    }

    public function setVoteNb(?int $voteNb): self
    {
        $this->voteNb = $voteNb;

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

    public function getQuestion(): ?Questions
    {
        return $this->question;
    }

    public function setQuestion(?Questions $question): self
    {
        $this->question = $question;

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
    public function getQuestions(): ?Questions
    {
        return $this->question;
    }

    public function setQuestions(?Questions $question): self
    {
        $this->question = $question;
        return $this;
    }
    public function getVotes(): Collection
    {
        return $this->votes;
    }
  
    public function addVotes(Votes $vote): self
    {
        if (!$this->votes->contains($vote)) {
            $this->votes[] = $vote;
            $vote->setAnswer($this);
        }

        return $this;
    }
    // public function getVoteNb() :?int
    // {
    //     $voteNb = 0;
    //     foreach ($this->votes as $vote) {
    //         $voteNb += $vote->getVoteType();
    //     }
    //     return $voteNb;
    // }
    public function __toString(): string
    {
        return $this->getContent(); // ou une autre propriété que vous voulez afficher
    }
    

    
public function hasUserVoted(?Users $user): ?bool
{
    foreach ($this->votes as $vote) {
        if ($vote->getUser() !== null && $vote->getUser() === $user) {
            return true;
        }
    }
    return false;
}

    
    
    
}

