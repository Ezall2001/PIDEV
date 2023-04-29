<?php

namespace App\Form;

use App\Entity\Users;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\FormError;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Symfony\Component\Validator\Constraints\Callback;
use Doctrine\ORM\EntityManagerInterface;


class ResetPasswordRequestType extends AbstractType
{
    private $em;

    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;
    }

    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('email', EmailType::class, [
                'label' => 'Entrez votre e-mail',
                'attr' => [
                    'placeholder' => 'exemple.exemple@esprit.tn',
                    'class' => 'form-control'
                ],
                'constraints' => [
                    new Callback(function ($value, ExecutionContextInterface $context) {
                        $this->validateEmailExists($value, $context, $this->em);
                    }),
                ],
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            // Configure your form options here
        ]);
    }

    public function validateEmailExists($value, ExecutionContextInterface $context, $em)
    {
        $email = $value;
        $user = $em->getRepository(Users::class)->findOneBy(['email' => $email]);
        if (!$user) {
            $context->buildViolation('Cet email n\'existe pas.')
                ->addViolation();
        }
    }
}
