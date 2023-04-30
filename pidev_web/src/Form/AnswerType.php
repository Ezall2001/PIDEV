<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use App\Entity\Users;

class AnswerType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        
            
        
                ->add('message', TextareaType::class, [
                    'label' => 'Votre réponse :',
                    'required' => false,
                    'attr' => [
                        'class' => 'form-control',
                        'placeholder' => 'Ici vous pouvez ajouter votre réponse',
                    ],
                    'constraints' => [
                        new NotBlank([
                            'message' => '',
                        ]),
                        new Length([
                            'min' => 20,
                            'minMessage' => '',
                        ]),
                    ],
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
