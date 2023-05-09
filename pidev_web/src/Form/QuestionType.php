<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Length;
use App\Entity\Subjects;
use App\Entity\Users;

class QuestionType extends AbstractType
{ 
    
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        
        ->add('title', TextType::class, [
            'attr' => [
                'class' => 'form-control',
                'placeholder' => '',
            ],
            'constraints' => [
                new NotBlank([
                    'message' => '',
                ]),
                new Length([
                    'min' => 10,
                    'minMessage' => '',
                ]),
            ],
        ])
        ->add('description', TextareaType::class, [
            'label' => 'Votre réponse :',
            'required' => false,
            'attr' => [
                'class' => 'form-control',
                'placeholder' => '',
            ],
            'constraints' => [
                new NotBlank([
                    'message' => '',
                ]),
                new Length([
                    'min' => 20,
                    'minMessage' => '',
                ]),
            ]])
            ->add('subject', EntityType::class, [
                'class' => Subjects::class,
                'label' => 'Subject',
                'attr' => [
                    'class' => 'form-control'
                ],
                'required' => true,
                'placeholder' => 'choisir matière',
                'choice_attr' => [
                    'choisir matière' => ['disabled' => 'disabled']
                ],
                'constraints' => [
                    new NotBlank([
                        'message' => '',
                    ])
                ]
            ])
            
            



              
                
            ->add('ajouter',SubmitType::class)     
            ->getForm();   ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
           
        ]);
    }
}