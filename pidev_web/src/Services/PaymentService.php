<?php

namespace App\Services;

class PaymentService
{

  static private string $apiBaseUrl = 'https://api.preprod.konnect.network/api/v2/';
  static private string $webhook = 'http://localhost:8000/validateParticipation';
  private string $headers;

  public function __construct()
  {
    $this->headers = "Content-Type: application/json\r\n" .
      "x-api-key: " . $_ENV['KONNECT_API_KEY'] . "\r\n";
  }



  public function init($walletId, $amount, $participationId)
  {
    $url = $this::$apiBaseUrl . 'payments/init-payment';
    $body = [
      'receiverWalletId' => $walletId,
      'amount' => $amount * 1000,
      'webhook' => $this::$webhook,
      'orderId' => $participationId,
      'silentWebhook' => false,
      'firstName' => '',
      'lastName' => '',
      'token' => 'TND',
      'type' => 'immediate',
      'lifespan' => 10,
      'feesIncluded' => true,
      'acceptedPaymentMethods' => ['wallet', 'bank_card', 'e-DINAR'],
    ];


    $options = [
      'http' => [
        'method' => 'POST',
        'header' => $this->headers,
        'content' => json_encode($body),
      ],
    ];

    $context = stream_context_create($options);
    $response = file_get_contents($url, false, $context);

    $result = json_decode($response, true);
    return $result;
  }

  public function getPayment(string $paymentRef): array
  {

    $url = $this::$apiBaseUrl . 'payments/' . $paymentRef;

    $options = [
      'http' => [
        'method' => 'GET',
        'header' => $this->headers,
      ],
    ];

    $context = stream_context_create($options);
    $response = file_get_contents($url, false, $context);

    $result = json_decode($response, true);
    return $result['payment'];
  }
}
