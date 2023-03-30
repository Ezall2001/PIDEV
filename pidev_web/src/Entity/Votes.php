<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Entity\Answers;


#[ORM\Entity(repositoryClass: VoteRepository::class)]
class Votes
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id;


    #[ORM\Column]
    private ?bool $voteType = null;





    #[ORM\ManyToOne(targetEntity: Answers::class, inversedBy: 'votes')]
    #[ORM\JoinColumn(name: 'answer_id', referencedColumnName: 'id', nullable: true)]
    private ?Answers $answer = null;


    #[ORM\ManyToOne(targetEntity: Users::class, inversedBy: 'votes')]
    #[ORM\JoinColumn(name: 'id_user', referencedColumnName: 'id', nullable: true)]
    private ?Users $user = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function isVoteType(): ?bool
    {
        return $this->voteType;
    }

    public function setVoteType(?bool $voteType): self
    {
        $this->voteType = $voteType;

        return $this;
    }

    public function getAnswer(): ?Answers
    {
        return $this->answer;
    }

    public function setAnswer(?Answers $answer): self
    {
        $this->answer = $answer;

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
}