<?php

namespace App\Services;

use Google\Client as GoogleClient;
use Google\Service\Calendar\Event as GoogleCalendarEvent;
use Google\Service\Calendar\ConferenceData as GoogleCalendarConferenceData;
use Google\Service\Calendar\CreateConferenceRequest as GoogleCalendarCreateConferenceRequest;
use Google\Service\Calendar as Google_Service_Calendar;
use Google\Service\Calendar\EventAttendee as Google_Service_Calendar_EventAttendee;

class CalendarService
{

  private static string $clientFile = "admin/client_secret_164638965534-u9auci4rillqigq71jaeo2rqorfntdnf.apps.googleusercontent.com.json";

  private $client;

  public function __construct()
  {
    $this->client = new GoogleClient();
    $this->client->setApplicationName('Myalo');
    $this->client->setAuthConfig($this::$clientFile);
    $this->client->addScope(Google_Service_Calendar::CALENDAR_EVENTS);
    $this->client->setDeveloperKey('AIzaSyBHIsIBu4-oaWTLQldPNK2KkCpd6Yf6XI4');
  }

  private function buildDateTime(\DateTime $date, \DateTime $time): string
  {
    $date = new \DateTime($date->format('Y-m-d') . ' ' . $time->format('H:i:s'), new \DateTimeZone('Africa/Tunis'));
    return $date->format('Y-m-d\TH:i:sP');
  }

  public function authUser()
  {
    if (!isset($_GET['code'])) {
      $authUrl = $this->client->createAuthUrl();
      return $authUrl;
    }

    $token = $this->client->fetchAccessTokenWithAuthCode($_GET['code']);
    $this->client->setAccessToken($token);
    return null;
  }

  public function createEvent($session)
  {
    $service = new Google_Service_Calendar($this->client);

    $event = new GoogleCalendarEvent([
      'summary' => $session->getCourse()->getName() . ' with ' . $session->getUser()->getFirstName(),
      'description' => $session->getTopics(),
      'start' => [
        'dateTime' => $this->buildDateTime($session->getDate(), $session->getStartTime()),
        'timeZone' => 'Africa/Tunis',
      ],
      'end' => [
        'dateTime' => $this->buildDateTime($session->getDate(), $session->getEndTime()),
        'timeZone' => 'Africa/Tunis',
      ],
      'conferenceData' => new GoogleCalendarConferenceData([
        'createRequest' => new GoogleCalendarCreateConferenceRequest([
          'conferenceSolutionKey' => [
            'type' => 'hangoutsMeet',
          ],
          'requestId' => uniqid(),
        ]),
      ]),
    ]);

    $calendarId = 'primary';
    $event = $service->events->insert($calendarId, $event, [
      'conferenceDataVersion' => 1,
    ]);

    $meetUrl = $event->getConferenceData()->getEntryPoints()[0]->getUri();
    $eventId = $event->getId();

    return [$meetUrl, $eventId];
  }

  public function addAttendee($eventId, $email)
  {
    $service = new Google_Service_Calendar($this->client);
    $calendarId = 'primary';


    $event = $service->events->get($calendarId, $eventId);


    $newAttendee = new Google_Service_Calendar_EventAttendee([
      'email' => $email,
    ]);
    $attendees = $event->getAttendees();
    $attendees[] = $newAttendee;
    $event->setAttendees($attendees);


    $service->events->patch($calendarId, $eventId, $event);
  }
}
