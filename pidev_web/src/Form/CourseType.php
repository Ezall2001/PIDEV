<?php

namespace App\Form;
use App\Entity\Courses;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

use App\Entity\Subjects;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;

class CourseType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('name')
            ->add('description')
            ->add('difficulty', ChoiceType::class, [
                'choices' => [
                    'EASY' => 'EASY',
                    'MEDIUM' => 'MEDIUM',
                    'HARD' => 'HARD',
                ],
                'placeholder' => 'choisir la difficulter', 
            ])
            ->add('subject', EntityType::class, [
                'class' => Subjects::class,
                'choice_label' => 'name',
                'placeholder' => 'choisir le sujet',
            ])
            ->add('enregistrer', SubmitType::class);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Courses::class,
        ]);
    }
}

