<?php

namespace App\Entity;

use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\UsersRepository;
use Symfony\Component\Security\Core\User\UserInterface;

#[ORM\Entity(repositoryClass: UsersRepository::class)]
#[UniqueEntity(fields: ['email'], message: 'Un compte existe déjà avec cette adresse e-mail')]
class Users implements UserInterface
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]

    private ?int $id;

    #[ORM\Column(length: 100)]
    private ?string $firstName = null;

    #[ORM\Column(length: 100)]
    private ?string $lastName = null;

    #[ORM\Column(length: 500)]
    private ?string $bio = null;

    #[ORM\Column(length: 300)]
    private ?string $avatarPath = null;

    #[ORM\Column]
    private ?int $age;

    #[ORM\Column]
    private ?int $score = 0;

    #[ORM\Column(length: 200)]
    private ?string $email = null;

    #[ORM\Column(length: 1000)]
    private ?string $password = null;

    #[ORM\Column(length: 50)]
    private ?string $type = 'STUDENT';

    #[ORM\Column(type: 'json')]
    private $roles = [];

    #[ORM\Column(type: 'string', length: 100, nullable: true)]
    private $resetToken;

    #[ORM\Column]
    private ?int $warnings = 0;

    #[ORM\Column]
    private ?string $walletId;

    #[ORM\Column(type: 'boolean')]
    private $is_verified = false;

    #[ORM\Column(type: 'datetime_immutable')]
    private $createdAt;

    #[ORM\OneToOne(targetEntity: TestResults::class, mappedBy: 'users')]
    private ?TestResults $result = null;


    #[ORM\OneToMany(targetEntity: Sessions::class,  mappedBy: "user")]
    private ?Collection $sessions = null;

    #[ORM\OneToMany(targetEntity: Participations::class,  mappedBy: "user")]
    private ?Collection $participations = null;
    #[ORM\Column(name: "blockedUntil", type: "datetime")]
    private ?\DateTimeInterface $blockedUntil = null;



    #[ORM\Column]
    private ?int $blocked = 0;

    public function getBlocked(): ?int
    {
        return $this->blocked;
    }

    public function setBlocked(?int $blocked): self
    {
        $this->blocked = $blocked;;

        return $this;
    }


    // #[ORM\OneToMany(targetEntity: Questions::class, mappedBy: 'users')]
    // public ?Collection $question = null;

    // #[ORM\OneToMany(targetEntity: Answers::class, mappedBy: 'users')]
    // public ?Collection $answer = null;

    // #[ORM\OneToMany(targetEntity: Votes::class, mappedBy: 'users')]
    // public ?Collection $vote = null;

    public function __construct()
    {
        //$this->session = new ArrayCollection();
        $this->question = new ArrayCollection();
        $this->answer = new ArrayCollection();
        $this->vote = new ArrayCollection();
    }

    #[ORM\OneToMany(targetEntity: Questions::class, mappedBy: 'users')]
    public ?Collection $question = null;

    #[ORM\OneToMany(targetEntity: Answers::class, mappedBy: 'users')]
    public ?Collection $answer = null;

    #[ORM\OneToMany(targetEntity: Votes::class, mappedBy: 'users')]
    public ?Collection $vote = null;

    public function getId(): ?int
    {
        return $this->id;
    }


    public function getFirstName(): ?string
    {

        $firstName =
            $this->firstName;
        return ucfirst(($firstName));
    }

    public function setFirstName(string $firstName): self
    {
        $this->firstName = $firstName;

        return $this;
    }
    public function getBlockedUntil(): ?\DateTimeInterface
    {
        return $this->blockedUntil;
    }

    public function setBlockedUntil(string $blockedUntil): self
    {
        $this->blockedUntil = new \DateTime($blockedUntil);
        return $this;
    }
    public function getAnswer(): Collection
    {
        return $this->answer;
    }
    public function getQuestion(): Collection
    {
        return $this->question;
    }
    public function getVote(): Collection
    {
        return $this->vote;
    }

    public function getWalledId(): ?string
    {
        return $this->walletId;
    }

    public function setWalledId(string $walletId): self
    {
        $this->walletId = $walletId;

        return $this;
    }

    public function getLastName(): ?string
    {
        $lastName =
            $this->lastName;
        return ucfirst(($lastName));
    }

    public function setLastName(string $lastName): self
    {
        $this->lastName = $lastName;

        return $this;
    }

    public function getBio(): ?string
    {
        return $this->bio;
    }

    public function setBio(?string $bio): self
    {
        $this->bio = $bio;

        return $this;
    }

    // public function getAvatarPath(): ?string
    // {
    //     return $this->avatarPath;
    // }

    public function getAvatarPath(): ?string
    {

        // if (strpos($this->avatarPath, 'server/profile_avatars/') !== false) {
        //     // remove "server/profile_avatars/" string from the path
        //     $filename = str_replace('server/profile_avatars/', '', $this->avatarPath);
        //     return $filename;
        // }


        return $this->avatarPath;
    }


    public function setAvatarPath(?string $avatarPath): self
    {
        $this->avatarPath = $avatarPath;

        return $this;
    }

    public function getAge(): ?int
    {
        return $this->age;
    }

    public function setAge(int $age): self
    {
        $this->age = $age;

        return $this;
    }

    public function getScore(): ?int
    {
        return $this->score;
    }

    public function setScore(int $score): self
    {
        $this->score = $score;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

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

    public function getWarnings(): ?int
    {
        return $this->warnings;
    }

    public function setWarnings(?int $warnings): self
    {
        $this->warnings = $warnings;

        return $this;
    }

    public function getSessions(): Collection
    {
        return $this->sessions;
    }
    // public function getAnswer(): Collection
    // {
    //     return $this->answer;
    // }
    // public function getQuestion(): Collection
    // {
    //     return $this->question;
    // }
    // public function getVote(): Collection
    // {
    //     return $this->vote;
    // }

    // public function getResult(): ?TestResults
    // {
    //     return $this->result;
    // }

    // public function setResult(TestResults $result): self
    // {
    //     $this->result = $result;

    //     return $this;
    // }

    /**
     * @return Collection<int, Sessions>
     */
    public function getSession(): Collection
    {
        return $this->sessions;
    }

    public function addSession(Sessions $sessions): self
    {
        if (!$this->sessions->contains($sessions)) {
            $this->sessions->add($sessions);
            $sessions->setUser($this);
        }

        return $this;
    }

    public function removeSession(Sessions $sessions): self
    {
        if ($this->sessions->removeElement($sessions)) {
            // set the owning side to null (unless already changed)
            if ($sessions->getUser() === $this) {
                $sessions->setUser(null);
            }
        }

        return $this;
    }

    // public function addQuestion(Questions $question): self
    // {
    //     if (!$this->question->contains($question)) {
    //         $this->question->add($question);
    //         $question->setUser($this);
    //     }

    //     return $this;
    // }

    // public function removeQuestion(Questions $question): self
    // {
    //     if ($this->question->removeElement($question)) {
    //         // set the owning side to null (unless already changed)
    //         if ($question->getUser() === $this) {
    //             $question->setUser(null);
    //         }
    //     }

    //     return $this;
    // }

    // public function addAnswer(Answers $answer): self
    // {
    //     if (!$this->answer->contains($answer)) {
    //         $this->answer->add($answer);
    //         $answer->setUser($this);
    //     }

    //     return $this;
    // }

    // public function removeAnswer(Answers $answer): self
    // {
    //     if ($this->answer->removeElement($answer)) {
    //         // set the owning side to null (unless already changed)
    //         if ($answer->getUser() === $this) {
    //             $answer->setUser(null);
    //         }
    //     }

    //     return $this;
    // }

    // public function addVote(Votes $vote): self
    // {
    //     if (!$this->vote->contains($vote)) {
    //         $this->vote->add($vote);
    //         $vote->setUser($this);
    //     }

    //     return $this;
    // }

    // public function removeVote(Votes $vote): self
    // {
    //     if ($this->vote->removeElement($vote)) {
    //         // set the owning side to null (unless already changed)
    //         if ($vote->getUser() === $this) {
    //             $vote->setUser(null);
    //         }
    //     }

    //     return $this;
    // }

    public function getUserIdentifier(): string
    {
        return (string) $this->email;
    }

    public function getUsername(): string
    {
        return (string) $this->email;
    }

    public function getRoles(): array
    {
        $roles = $this->roles;
        $roles[] = 'ROLE_USER';

        return array_unique($roles);
    }

    public function setRoles(array $roles): self
    {
        $this->roles = $roles;

        return $this;
    }

    public function getSalt(): ?string
    {
        return null;
    }

    public function eraseCredentials()
    {
        // $this->plainPassword = null;
    }

    public function getResetToken(): ?string
    {
        return $this->resetToken;
    }

    public function setResetToken(?string $resetToken): self
    {
        $this->resetToken = $resetToken;

        return $this;
    }
    public function getIsVerified(): ?bool
    {
        return $this->is_verified;
    }

    public function setIsVerified(bool $is_verified): self
    {
        $this->is_verified = $is_verified;

        return $this;
    }
    public function getCreatedAt(): ?\DateTimeImmutable
    {
        return $this->createdAt;
    }

    public function setCreatedAt(): self
    {
        $this->createdAt = new \DateTimeImmutable();

        return $this;
    }

    private static $levels = ['NEWCOMER', 'BEGINNER', 'COMPETENT', 'PROFICIENT', 'EXPERT'];
    public static function computeLevelBreakpointScore($levelIndex)
    {
        $levelIndex = (int) $levelIndex;
        return pow(($levelIndex + 1), 2) * 100;
    }

    public function getLevel()
    {
        $level = $this->computeLevel();
        return ucfirst(strtolower($level));
    }

    public function computeLevel()
    {
        $levelIndex = 0;
        while ($this->score > self::computeLevelBreakpointScore($levelIndex)) {
            $levelIndex++;
        }
        return self::$levels[$levelIndex];
    }

    public function computeCurrentLevelScore()
    {
        $levelIndex = 0;
        $levelBreakpointScore = self::computeLevelBreakpointScore($levelIndex);
        $currentLevelScore = $this->score;

        while ($currentLevelScore > $levelBreakpointScore) {
            $currentLevelScore -= $levelBreakpointScore;
            $levelIndex++;
            $levelBreakpointScore = self::computeLevelBreakpointScore($levelIndex);
        }

        return $currentLevelScore;
    }

    public static function computeLevelBreakpointScoreForLevel($levelName)
    {
        $levelIndex = array_search(strtoupper($levelName), self::$levels);
        return pow(($levelIndex + 1), 2) * 100;
    }

    public function setScoreBar()
    {
        $currentScore = $this->computeCurrentLevelScore();
        $currentBreakpointScore = self::computeLevelBreakpointScoreForLevel($this->computeLevel());
        $barWidth = ($currentScore / $currentBreakpointScore) * 100;

        return $barWidth;
    }
    public function addQuestion(Questions $question): self
    {
        if (!$this->question->contains($question)) {
            $this->question->add($question);
            $question->setUser($this);
        }

        return $this;
    }

    public function removeQuestion(Questions $question): self
    {
        if ($this->question->removeElement($question)) {
            // set the owning side to null (unless already changed)
            if ($question->getUser() === $this) {
                $question->setUser(null);
            }
        }

        return $this;
    }

    public function addAnswer(Answers $answer): self
    {
        if (!$this->answer->contains($answer)) {
            $this->answer->add($answer);
            $answer->setUser($this);
        }

        return $this;
    }

    public function removeAnswer(Answers $answer): self
    {
        if ($this->answer->removeElement($answer)) {
            // set the owning side to null (unless already changed)
            if ($answer->getUser() === $this) {
                $answer->setUser(null);
            }
        }

        return $this;
    }

    public function addVote(Votes $vote): self
    {
        if (!$this->vote->contains($vote)) {
            $this->vote->add($vote);
            $vote->setUser($this);
        }

        return $this;
    }

    public function removeVote(Votes $vote): self
    {
        if ($this->vote->removeElement($vote)) {
            // set the owning side to null (unless already changed)
            if ($vote->getUser() === $this) {
                $vote->setUser(null);
            }
        }

        return $this;
    }
    public function __toString(): string
    {
        return $this->firstName;
    }
    public function isBlocked(): bool
    {
        if (!$this->blocked) {
            // L'utilisateur n'est pas bloqué
            return false;
        }

        if (!$this->blockedUntil instanceof \DateTimeInterface) {
            // L'utilisateur est bloqué indéfiniment 
            return true;
        }

        // Vérifier si l'utilisateur est bloqué jusqu'à une date et une heure ultérieures
        return $this->blockedUntil > new \DateTime();
    }
}
