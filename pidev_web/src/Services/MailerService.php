<?php

namespace App\Services;

use Symfony\Component\Mailer\MailerInterface;
use Symfony\Bridge\Twig\Mime\TemplatedEmail;
use Symfony\Component\Mime\Email;

class MailerService
{

  static private string $from = 'esprit.myalo@gmail.com';
  static private string $signature = "<table cellpadding='0' cellspacing='0' class='table__StyledTable-sc-1avdl6r-0 jWJRxL' style='vertical-align: -webkit-baseline-middle; font-size: medium; font-family: Arial;'><tbody><tr><td><table cellpadding='0' cellspacing='0' class='table__StyledTable-sc-1avdl6r-0 jWJRxL' style='vertical-align: -webkit-baseline-middle; font-size: medium; font-family: Arial;'><tbody><tr><td width='150' style='vertical-align: middle;'><span class='template3__ImageContainer-sc-vj949k-0 kElCTu' style='margin-right: 20px; display: block;'><img src='https://drive.google.com/uc?id=11-yNS69Sb-sWYbJyWmaPRuXVsfPvvWB3' role='presentation' width='130' class='image__StyledImage-sc-hupvqm-0 fQKUvi' style='max-width: 130px;'></span></td><td style='vertical-align: middle;'><h2 color='#000000' class='name__NameContainer-sc-1m457h3-0 hkyYrA' style='margin: 0px; font-size: 18px; color: rgb(0, 0, 0); font-weight: 600;'><span>Eya</span><span>&nbsp;</span><span>Harbi</span></h2><p color='#000000' font-size='medium' class='job-title__Container-sc-1hmtp73-0 iJcqpv' style='margin: 0px; color: rgb(0, 0, 0); font-size: 14px; line-height: 22px;'><span>Student Manager</span></p><p color='#000000' font-size='medium' class='company-details__CompanyContainer-sc-j5pyy8-0 bEBXsp' style='margin: 0px; font-weight: 500; color: rgb(0, 0, 0); font-size: 14px; line-height: 22px;'><span>Myalò</span></p></td><td width='30'><div style='width: 30px;'></div></td><td color='#97A8F8' direction='vertical' width='1' height='auto' class='color-divider__Divider-sc-1h38qjv-0 dcKmvZ' style='width: 1px; border-bottom: none; border-left: 1px solid rgb(151, 168, 248);'></td><td width='30'><div style='width: 30px;'></div></td><td style='vertical-align: middle;'><table cellpadding='0' cellspacing='0' class='table__StyledTable-sc-1avdl6r-0 jWJRxL' style='vertical-align: -webkit-baseline-middle; font-size: medium; font-family: Arial;'><tbody><tr height='25' style='vertical-align: middle;'><td width='30' style='vertical-align: middle;'><table cellpadding='0' cellspacing='0' class='table__StyledTable-sc-1avdl6r-0 jWJRxL' style='vertical-align: -webkit-baseline-middle; font-size: medium; font-family: Arial;'><tbody><tr><td style='vertical-align: bottom;'><span color='#97A8F8' width='11' class='contact-info__IconWrapper-sc-mmkjr6-1 hBHfIp' style='display: inline-block; background-color: rgb(151, 168, 248);'><img src='https://cdn2.hubspot.net/hubfs/53/tools/email-signature-generator/icons/phone-icon-2x.png' color='#97A8F8' alt='mobilePhone' width='13' class='contact-info__ContactLabelIcon-sc-mmkjr6-0 dGVIJx' style='display: block; background-color: rgb(151, 168, 248);'></span></td></tr></tbody></table></td><td style='padding: 0px; color: rgb(0, 0, 0);'><a href='tel:93456017' color='#000000' class='contact-info__ExternalLink-sc-mmkjr6-2 bibcmr' style='text-decoration: none; color: rgb(0, 0, 0); font-size: 12px;'><span>93456017</span></a></td></tr><tr height='25' style='vertical-align: middle;'><td width='30' style='vertical-align: middle;'><table cellpadding='0' cellspacing='0' class='table__StyledTable-sc-1avdl6r-0 jWJRxL' style='vertical-align: -webkit-baseline-middle; font-size: medium; font-family: Arial;'><tbody><tr><td style='vertical-align: bottom;'><span color='#97A8F8' width='11' class='contact-info__IconWrapper-sc-mmkjr6-1 hBHfIp' style='display: inline-block; background-color: rgb(151, 168, 248);'><img src='https://cdn2.hubspot.net/hubfs/53/tools/email-signature-generator/icons/email-icon-2x.png' color='#97A8F8' alt='emailAddress' width='13' class='contact-info__ContactLabelIcon-sc-mmkjr6-0 dGVIJx' style='display: block; background-color: rgb(151, 168, 248);'></span></td></tr></tbody></table></td><td style='padding: 0px;'><a href='mailto:esprit.myalo@gmail.com' color='#000000' class='contact-info__ExternalLink-sc-mmkjr6-2 bibcmr' style='text-decoration: none; color: rgb(0, 0, 0); font-size: 12px;'><span>esprit.myalo@gmail.com</span></a></td></tr><tr height='25' style='vertical-align: middle;'><td width='30' style='vertical-align: middle;'><table cellpadding='0' cellspacing='0' class='table__StyledTable-sc-1avdl6r-0 jWJRxL' style='vertical-align: -webkit-baseline-middle; font-size: medium; font-family: Arial;'><tbody><tr><td style='vertical-align: bottom;'><span color='#97A8F8' width='11' class='contact-info__IconWrapper-sc-mmkjr6-1 hBHfIp' style='display: inline-block; background-color: rgb(151, 168, 248);'><img src='https://cdn2.hubspot.net/hubfs/53/tools/email-signature-generator/icons/address-icon-2x.png' color='#97A8F8' alt='address' width='13' class='contact-info__ContactLabelIcon-sc-mmkjr6-0 dGVIJx' style='display: block; background-color: rgb(151, 168, 248);'></span></td></tr></tbody></table></td><td style='padding: 0px;'><span color='#000000' class='contact-info__Address-sc-mmkjr6-3 fhjLwd' style='font-size: 12px; color: rgb(0, 0, 0);'><span>el Ghazela</span></span></td></tr></tbody></table></td></tr></tbody></table></td></tr><tr><td><table cellpadding='0' cellspacing='0' class='table__StyledTable-sc-1avdl6r-0 jWJRxL' style='width: 100%; vertical-align: -webkit-baseline-middle; font-size: medium; font-family: Arial;'><tbody><tr><td height='30'></td></tr><tr><td color='#97A8F8' direction='horizontal' width='auto' height='1' class='color-divider__Divider-sc-1h38qjv-0 dcKmvZ' style='width: 100%; border-bottom: 1px solid rgb(151, 168, 248); border-left: none; display: block;'></td></tr><tr><td height='30'></td></tr></tbody></table></td></tr><tr><td><table cellpadding='0' cellspacing='0' class='table__StyledTable-sc-1avdl6r-0 jWJRxL' style='width: 100%; vertical-align: -webkit-baseline-middle; font-size: medium; font-family: Arial;'><tbody><tr><td style='vertical-align: top;'></td><td style='text-align: right; vertical-align: top;'><table cellpadding='0' cellspacing='0' class='table__StyledTable-sc-1avdl6r-0 jWJRxL' style='display: inline-block; vertical-align: -webkit-baseline-middle; font-size: medium; font-family: Arial;'><tbody><tr style='text-align: right;'><td><a href='https://www.facebook.com/profile.php?id=100090591443416' color='#6a78d1' class='social-links__LinkAnchor-sc-py8uhj-2 jhqNFe' style='display: inline-block; padding: 0px; background-color: rgb(106, 120, 209);'><img src='https://cdn2.hubspot.net/hubfs/53/tools/email-signature-generator/icons/facebook-icon-2x.png' alt='facebook' color='#6a78d1' height='24' class='social-links__LinkImage-sc-py8uhj-1 kLZBAf' style='background-color: rgb(106, 120, 209); max-width: 135px; display: block;'></a></td><td width='5'><div></div></td></tr></tbody></table></td></tr></tbody></table></td></tr><tr><td height='30'></td></tr><tr><td style='text-align: center;'></td></tr></tbody></table>";

  public function __construct(private MailerInterface $mailer)
  {
  }

  public function send(array $emailObj)
  {
    $email = (new Email())
      ->from($this::$from)
      ->to($emailObj['to'])
      ->subject($emailObj['subject'])
      ->text($emailObj['body'])
      ->html($this::$signature);

    $this->mailer->send($email);
  }
  public function sendEya(
    string $to,
    string $subject,
    string $template,
    array $context
  ) {
    $email = (new TemplatedEmail())
      ->from($this::$from)
      ->to($to)
      ->subject($subject)
      ->htmlTemplate("emails/$template.html.twig")
      ->context($context)
      ->html($this::$signature);

    $this->mailer->send($email);
  }
}
