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
                'label' => 'Numéro question'
            ])
            ->add('optiona',options:[
                'label' => 'Option A'
            ])
            ->add('optionb',options:[
                'label' => 'Option B'
            ])
            ->add('optionc',options:[
                'label' => 'Option C'
            ])
            ->add('optiond',options:[
                'label' => 'Option D'
            ])
            ->add('correctOption',options:[
                'label' => 'Réponse'
            ])
            ->add('question',options:[
                'label' => 'Questions'
            ])

            ->add('test', EntityType::class, [
               'class' => Tests::class ,
               'choice_label' => 'id',
               'placeholder' => 'id test'
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
