<?php

namespace App\Form;

use App\Entity\Users;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Callback;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Doctrine\Persistence\ManagerRegistry;

class LoginType extends AbstractType
{
    private $doctrine;

    public function __construct(ManagerRegistry $doctrine)
    {
        $this->doctrine = $doctrine;
    }

    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('email', EmailType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Veuillez entrer votre adresse e-mail.']),
                    new Callback([$this, 'validateEmail']),
                ],
            ])
            ->add('password', PasswordType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Veuillez entrer votre mot de passe.']),
                    new Callback([$this, 'validatePassword']),
                ],
            ])
            ->add('submit', SubmitType::class, ['label' => 'Submit']);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Users::class,
        ]);
    }

    public function validateEmail($email, ExecutionContextInterface $context)
    {
        $userRepository = $this->doctrine->getRepository(Users::class);
        $user = $userRepository->findOneBy(['email' => $email]);

        if ($user === null) {
            $context->buildViolation('L\'adresse e-mail saisie n\'existe pas dans notre système.')
                ->addViolation();
        }
    }

    public function validatePassword($password, ExecutionContextInterface $context)
    {
        $email = $context->getRoot()->get('email')->getData();
        $userRepository = $this->doctrine->getRepository(Users::class);
        $user = $userRepository->findOneBy(['email' => $email]);

        if ($user !== null && !password_verify($password, $user->getPassword())) {
            $context->buildViolation('Le mot de passe est incorrect.')
                ->addViolation();
        }
    }
}
