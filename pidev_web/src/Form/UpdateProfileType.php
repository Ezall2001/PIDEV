<?php

namespace App\Form;

use App\Entity\Users;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Range;
use Symfony\Component\Validator\Constraints\Image;
use Symfony\Component\Form\Extension\Core\Type\FileType;

class UpdateProfileType extends AbstractType
{

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
            // ->add('ImageFile', FileType::class, [
            //     'mapped' => false
            // ])
            ->add('age', IntegerType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Veuillez entrer votre âge.']),
                    new Range([
                        'min' => 18,
                        'max' => 100,
                        'minMessage' => 'Vous devez avoir au moins 19 ans.'
                    ]),
                ],
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Users::class,
        ]);
    }
}
