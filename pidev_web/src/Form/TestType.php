<?php

namespace App\Form;
use App\Entity\Courses;
use App\Entity\Tests;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class TestType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('type', ChoiceType::class, [
                'choices' => [
                    'Cours' => "COURSE",
                ],
                'attr' => [
                    'class' => 'form-select disabled'
                ],
                'disabled' => false
                ])
            ->add('minPoints', options:[
                'label' => 'Seuil',
                'attr' => [
                    'class' => 'form-control',
                    'rows' => 5,
                    'placeholder' => 'Seuil'
                ]
            ])
            ->add('duration', options:[
                'label' => 'Durée',
                'attr' => [
                    'class' => 'form-control',
                    'rows' => 5,
                    'placeholder' => 'Durée'
                ]
            ])

            ->add('course', EntityType::class, [
                'class' => Courses::class ,
                'choice_label' => 'id',
                'placeholder' => 'ID Cours',
                'attr' => [
                    'class' => 'form-select'
                ]
             ])
            
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Tests::class,
        ]);
    }
}
