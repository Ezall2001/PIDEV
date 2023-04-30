<?php

namespace App\Form;
use App\Entity\Subjects;
use Doctrine\DBAL\Types\StringType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\SearchType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Choice;


class SearchFormType extends AbstractType

{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('name', SearchType::class, [
            'label' => 'Search by name',
            'attr' => [
                'placeholder' => 'Enter search terms...',
            ],
            'required' => false,
        ])
        ->add('classes_esprit', SearchType::class, [
            'label' => 'Search by classes_esprit',
            'attr' => [
                'placeholder' => 'Enter search terms...',
            ],
            'required' => false,
        ]);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
          
        ]);
    }
}
