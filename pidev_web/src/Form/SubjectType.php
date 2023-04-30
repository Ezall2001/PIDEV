<?php

namespace App\Form;
use App\Entity\Subjects;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
class SubjectType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('name')
            ->add('description')
        
            ->add('classes_esprit', ChoiceType::class, [
                'choices' => [
                    'A3' => 'A3',
                    'A2' => 'A2',
                    'A1' => 'A1',
                    'B1' => 'B1',
                    'B2' => 'B2',
                    'B3' => 'B3',
                ],
                'placeholder' => 'choisir la classe', 
               
                'expanded' => false,
            ])
            ->add('enregistrer', SubmitType::class);
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Subjects::class,
        ]);
    }
}
