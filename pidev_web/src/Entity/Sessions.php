<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints\Date;
use Symfony\Component\Validator\Constraints\Time;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use App\Repository\SessionsRepository;

#[ORM\Entity(repositoryClass: SessionsRepository::class)]
class Sessions
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private int $id;


    #[ORM\Column]
    private float $price;


    #[ORM\Column]
    private Date $date;


    #[ORM\Column]
    private Time $startTime;


    #[ORM\Column]
    private Time $endTime;


    #[ORM\Column(length: 3000)]
    private string $topics;


    #[ORM\Column]
    private ?int $places = null;

    #[ORM\Column(length: 500)]
    private ?string $meetLink = null;


    #[ORM\Column(length: 500)]
    private ?string $imageLink = null;


    #[ORM\ManyToOne(targetEntity: Courses::class, inversedBy: 'sessions')]
    #[ORM\JoinColumn(name: 'id_course', referencedColumnName: 'id', nullable: true)]
    private ?Courses $course = null;

    #[ORM\OneToMany(targetEntity: Resources::class, mappedBy: "sessions")]
    private ?Collection $resources;

    #[ORM\ManyToOne(targetEntity: Users::class, inversedBy: 'sessions')]
    #[ORM\JoinColumn(name: 'id_user', referencedColumnName: 'id', nullable: true)]
    private ?Users $user = null;

    public function __construct()
    {
        $this->resources = new ArrayCollection();
    }

    public function getId(): int
    {
        return $this->id;
    }

    public function getPrice(): float
    {
        return $this->price;
    }

    public function setPrice(float $price): self
    {
        $this->price = $price;

        return $this;
    }

    public function getDate(): Date
    {
        return $this->date;
    }

    public function setDate(Date $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getStartTime(): Time
    {
        return $this->startTime;
    }

    public function setStartTime(Time $startTime): self
    {
        $this->startTime = $startTime;

        return $this;
    }

    public function getEndTime(): Time
    {
        return $this->endTime;
    }

    public function setEndTime(Time $endTime): self
    {
        $this->endTime = $endTime;

        return $this;
    }

    public function getTopics(): string
    {
        return $this->topics;
    }

    public function setTopics(string $topics): self
    {
        $this->topics = $topics;

        return $this;
    }

    public function getPlaces(): ?int
    {
        return $this->places;
    }

    public function setPlaces(?int $places): self
    {
        $this->places = $places;

        return $this;
    }

    public function getMeetLink(): ?string
    {
        return $this->meetLink;
    }

    public function setMeetLink(?string $meetLink): self
    {
        $this->meetLink = $meetLink;

        return $this;
    }

    public function getImageLink(): ?string
    {
        return $this->imageLink;
    }

    public function setImageLink(?string $imageLink): self
    {
        $this->imageLink = $imageLink;

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

    public function getUser(): ?Users
    {
        return $this->user;
    }

    public function setUser(?Users $user): self
    {
        $this->user = $user;

        return $this;
    }


    public function getResources(): ?Collection
    {
        return $this->resources;
    }

    public function setResources(?Collection $resources): self
    {
        $this->resources = $resources;
        return $this;
    }

    public function addResource(Resources $resource): self
    {
        if (!$this->resources->contains($resource)) {
            $this->resources->add($resource);
            $resource->setSession($this);
        }

        return $this;
    }

    public function removeResource(Resources $resource): self
    {
        if ($this->resources->removeElement($resource)) {
            if ($resource->getSession() === $this) {
                $resource->setSession(null);
            }
        }

        return $this;
    }
}
