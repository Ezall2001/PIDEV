<?php

namespace App\Form;
use App\Entity\Tests;
use App\Entity\TestQs;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TestQsType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('questionNumber',options:[
                'label' => 'Numéro question',
                'attr' => [
                    'class' => 'form-control',
                    'rows' => 5,
                    'placeholder' => 'Numéro question'
                ]
            ])
            ->add('optiona',options:[
                'label' => 'Option A',
                'attr' => [
                    'class' => 'form-control',
                    'rows' => 5,
                    'placeholder' => 'Option A'
                ]
            ])
            ->add('optionb',options:[
                'label' => 'Option B',
                'attr' => [
                    'class' => 'form-control',
                    'rows' => 5,
                    'placeholder' => 'Option B'
                ]
            ])
            ->add('optionc',options:[
                'label' => 'Option C',
                'attr' => [
                    'class' => 'form-control',
                    'rows' => 5,
                    'placeholder' => 'Option C'
                ]
            ])
            ->add('optiond',options:[
                'label' => 'Option D',
                'attr' => [
                    'class' => 'form-control',
                    'rows' => 5,
                    'placeholder' => 'Option D'
                ]
            ])
            ->add('correctOption',options:[
                'label' => 'Réponse',
                'attr' => [
                    'class' => 'form-control',
                    'rows' => 5,
                    'placeholder' => 'Réponse'
                ]
            ])
            ->add('question',options:[
                'label' => 'Question',
                'attr' => [
                    'class' => 'form-control',
                    'rows' => 5,
                    'placeholder' => 'Question'
                ]
            ])

            ->add('test', EntityType::class, [
               'class' => Tests::class ,
               
               'choice_label' => 'id',
               'placeholder' => 'ID Test',
               'attr' => [
                'class' => 'form-select'
            ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TestQs::class,
        ]);
    }
}
