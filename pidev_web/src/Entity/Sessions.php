<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use App\Repository\SessionsRepository;
use phpDocumentor\Reflection\Types\Boolean;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: SessionsRepository::class)]
class Sessions
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups(["sessionFrom", "apiSessionsList"])]
    private int $id;


    #[ORM\Column]
    #[Groups(["sessionFrom", "apiSessionsList"])]
    private float $price;


    #[ORM\Column(type: "date")]
    #[Groups(["sessionFrom", "apiSessionsList"])]
    private \DateTime $date;


    #[ORM\Column(type: "time")]
    #[Groups(["sessionFrom", "apiSessionsList"])]
    private \DateTime $startTime;


    #[ORM\Column(type: "time")]
    #[Groups(["sessionFrom", "apiSessionsList"])]
    private \DateTime $endTime;


    #[ORM\Column(length: 500)]
    #[Groups(["sessionFrom", "apiSessionsList"])]
    private string $topics = "";

    #[ORM\Column]
    private ?int $places = 0;

    #[ORM\Column]
    #[Groups(["sessionFrom"])]
    private ?int $maxPlaces = null;


    #[ORM\Column(length: 500)]
    #[Groups(["apiSessionsList"])]
    private ?string $meetLink = null;


    #[ORM\Column(length: 500)]
    #[Groups(["sessionFrom"])]
    private ?string $imageLink = null;

    #[ORM\Column]
    private ?bool $blocked = false;

    #[ORM\Column]
    private ?string $eventId;

    #[ORM\ManyToOne(targetEntity: Courses::class, inversedBy: 'sessions')]
    #[ORM\JoinColumn(name: 'id_course', referencedColumnName: 'id', nullable: true)]
    #[Groups(["sessionFrom", "apiSessionsList"])]
    private ?Courses $course = null;

    #[ORM\OneToMany(targetEntity: Resources::class, mappedBy: "session", cascade: ["persist"])]
    #[Groups(["sessionFrom"])]
    private ?Collection $resources = null;

    #[ORM\OneToMany(targetEntity: Participations::class, mappedBy: "session")]
    private ?Collection $participations = null;

    #[ORM\ManyToOne(targetEntity: Users::class, inversedBy: 'sessions')]
    #[ORM\JoinColumn(name: 'id_user', referencedColumnName: 'id', nullable: true)]
    #[Groups(["apiSessionsList"])]
    private ?Users $user = null;


    public function __construct()
    {
        $this->resources = new ArrayCollection();
        $this->participations = new ArrayCollection();
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

    public function getDate(): \DateTime
    {
        return $this->date;
    }

    public function setDate(\DateTime $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getStartTime(): \DateTime
    {
        return $this->startTime;
    }

    public function setStartTime(\DateTime $startTime): self
    {
        $this->startTime = $startTime;

        return $this;
    }

    public function getEndTime(): \DateTime
    {
        return $this->endTime;
    }

    public function setEndTime(\DateTime $endTime): self
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

    public function getMaxPlaces(): ?int
    {
        return $this->maxPlaces;
    }

    public function setMaxPlaces(int $maxPlaces): self
    {
        $this->maxPlaces = $maxPlaces;

        return $this;
    }


    public function getParticipations(): ?Collection
    {
        return $this->participations;
    }

    public function addParticipation(Participations $participation): self
    {
        if (!$this->participations->contains($participation)) {
            $this->participations->add($participation);
            $participation->setSession($this);
        }

        return $this;
    }

    public function removeParticipation(Participations $participation): self
    {
        if ($this->participations->removeElement($participation)) {
            if ($participation->getSession() === $this) {
                $participation->setSession(null);
            }
        }

        return $this;
    }

    public function getBlocked(): ?bool
    {
        return $this->blocked;
    }

    public function setBlocked(bool $blocked): self
    {
        $this->blocked = $blocked;

        return $this;
    }

    public function isBlocked(): ?bool
    {
        return $this->blocked;
    }

    public function getEventId(): ?string
    {
        return $this->eventId;
    }

    public function setEventId(string $eventId): self
    {
        $this->eventId = $eventId;

        return $this;
    }
}
