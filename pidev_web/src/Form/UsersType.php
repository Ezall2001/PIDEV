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
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\Email;
use Symfony\Component\Validator\Constraints\Range;

class UsersType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('firstName', TextType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Veuillez entrer votre prénom.']),
                    new Length([
                        'min' => 3,
                        'maxMessage' => 'Le prénom doit comporter au moins 3 caractères.'
                    ]),
                ],
            ])
            ->add('lastName', TextType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Veuillez entrer votre nom de famille.']),
                    new Length([
                        'min' => 3,
                        'maxMessage' => 'Le nom de famille doit comporter au moins 3 caractères.'
                    ]),
                ],
            ])
            ->add('bio', TextType::class, [
                'constraints' => [
                    new Length([
                        'min' => 50,
                        'minMessage' => 'La biographie doit comporter au moins 100 caractères.'
                    ]),
                ],
            ])
            ->add('avatarPath', TextType::class, [
                'constraints' => [
                    new Length([
                        'max' => 255,
                        'maxMessage' => 'Le chemin de l\'avatar ne doit pas dépasser {{ limit }} caractères.'
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
}
