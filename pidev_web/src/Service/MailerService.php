<?php

namespace App\Service;



use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;


class MailerService
{
  public function __construct(private MailerInterface $mailer){
   
  }
    public function sendEmail( $to= 'najiba.gragba@esprit.tn',
    $message = 'Votre message ici.',
     $user = 'Votre nom',
    $subject='voila une réponse a était bien ajouter a votre question'): void
    { $content = <<<EOF
        <!DOCTYPE html>
        <html>
            <head>
                <meta charset="utf-8">
                <title>Votre sujet</title>
               
                <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #fff; /* blanc */
                }
                .container {
                    margin: 0 auto;
                    max-width: 600px;
                    background-color: #fff;
                    border-radius: 8px;
                    padding: 16px;
                    box-shadow: 0 0 8px rgba(0, 0, 0, 0.2);
                }
                .title {
                    font-size: 24px;
                    font-weight: bold;
                    margin-bottom: 24px;
                    color: #007bff; /* bleu */
                }
                .message {
                    font-size: 16px;
                    line-height: 1.5;
                    margin-bottom: 24px;
                }
                .user {
                    font-style: italic;
                }
            </style>
            
            
            </head>
            <body>
                <p class="message">Bonjour,</p>
                <p class="title message">la réponse est</p>
                <p class="message">$message</p>
                <p class="title message">Cordialement,</p>
                <p  class="title message"">$user</p>
                
            </body>
        </html>
        EOF;

    $email = (new Email())
        ->from('esprit.myalo@gmail.com')
        ->to($to)
        ->subject('Voici une réponse à votre question')
        ->text('Sending emails is fun again!')
        ->html($content);

    $this->mailer->send($email);
        $email = (new Email())
            ->from('esprit.myalo@gmail.com')
            ->to($to)
            //->cc('cc@example.com')
            //->bcc('bcc@example.com')
            //->replyTo('fabien@example.com')
            //->priority(Email::PRIORITY_HIGH)
            ->subject($subject)
            ->text('Sending emails is fun again!')
            ->html($content);

        $this->mailer->send($email);

        // ...
    }
}