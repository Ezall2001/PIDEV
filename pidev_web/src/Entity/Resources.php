<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\ResourcesRepository;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: ResourcesRepository::class)]
class Resources
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("sessionFrom")]
    private int $id;


    #[ORM\Column(length: 250)]
    #[Groups("sessionFrom")]
    private string $name;

    #[ORM\Column(length: 1000)]
    #[Groups("sessionFrom")]
    private string $filePath;

    #[ORM\ManyToOne(targetEntity: Sessions::class, inversedBy: 'resources')]
    #[ORM\JoinColumn(name: 'id_session', referencedColumnName: 'id', nullable: true)]
    private ?Sessions $session = null;


    public function getId(): int
    {
        return $this->id;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function setName(string $name): self
    {
        $this->name = $name;

        return $this;
    }

    public function getFilePath(): string
    {
        return $this->filePath;
    }

    public function setFilePath(string $filePath): self
    {
        $this->filePath = $filePath;

        return $this;
    }

    public function getSession(): ?Sessions
    {
        return $this->session;
    }

    public function setSession(?Sessions $session): self
    {
        $this->session = $session;

        return $this;
    }
}
