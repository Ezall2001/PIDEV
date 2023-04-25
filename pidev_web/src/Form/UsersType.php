<?php

namespace App\Form;

use App\Entity\Users;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\Email;
use Symfony\Component\Validator\Constraints\Range;
use Symfony\Component\Validator\Constraints\Image;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Validator\Constraints\Callback;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Validator\Constraints as Assert;


class UsersType extends AbstractType
{
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('firstName', TextType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Veuillez entrer votre prénom.']),
                    new Length([
                        'min' => 3,
                        'minMessage' => 'Le prénom doit comporter au moins 3 caractères.'
                    ]),
                ],
            ])
            ->add('lastName', TextType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Veuillez entrer votre nom de famille.']),
                    new Length([
                        'min' => 3,
                        'minMessage' => 'Le nom de famille doit comporter au moins 3 caractères.'
                    ]),
                ],
            ])
            ->add('bio', TextareaType::class, [
                'constraints' => [
                    new Length([
                        'min' => 50,
                        'minMessage' => 'La biographie doit comporter au moins 100 caractères.'
                    ]),
                ],
            ])
            ->add('avatarPath', FileType::class, [
                'constraints' => [
                    new Image([
                        'mimeTypes' => ['image/jpeg', 'image/png', 'image/gif'],
                        'mimeTypesMessage' => 'Le fichier doit être une image au format JPEG, PNG ou GIF.',
                    ]),
                ],
            ])
            ->add('age', IntegerType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Veuillez entrer votre âge.']),
                    new Range([
                        'min' => 18,
                        'max' => 100,
                        'minMessage' => 'Vous devez avoir au moins 19 ans.'
                    ]),
                ],
            ])
            ->add('email', EmailType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Veuillez entrer votre adresse e-mail.']),
                    new Email(['message' => 'L\'adresse e-mail n\'est pas valide.']),
                    new Callback([$this, 'validateUniqueEmail']),
                    new Assert\Regex([
                        'pattern' => '/@esprit\.tn$/i', // Use regex to check if email ends with "@esprit.tn"
                        'message' => 'L\'adresse e-mail doit appartenir au domaine @esprit.tn.',
                    ]),
                ],
            ])
            ->add('password', PasswordType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Veuillez entrer votre mot de passe.']),
                    new Length([
                        'min' => 8,
                        'minMessage' => 'Le mot de passe doit comporter au moins 8 caractères.'
                    ]),
                ],
            ])
            ->add('submit', SubmitType::class, ['label' => 'Submit']);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Users::class,
        ]);
    }
    public function validateUniqueEmail($email, ExecutionContextInterface $context)
    {
        $existingUser = $this->entityManager->getRepository(Users::class)->findOneBy(['email' => $email]);

        if ($existingUser) {
            $context->buildViolation('Cette adresse e-mail est déjà utilisée.')
                ->atPath('email')
                ->addViolation();
        }
    }
}
