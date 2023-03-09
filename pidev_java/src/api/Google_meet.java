package api;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.Calendar.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.ConferenceData;
import com.google.api.services.calendar.model.CreateConferenceRequest;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import entities.Session;
import utils.Log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Google_meet {

  private static final String MEET_PREFIX = "https://meet.google.com/";
  private static final String APPLICATION_NAME = "Myâlo";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(
      CalendarScopes.CALENDAR);
  private static final String CREDENTIALS_FILE_PATH = "./client_secret_164638965534-8di2jvotm8rfqiup4cou1ratog7h66o8.apps.googleusercontent.com.json";

  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
      throws IOException {

    InputStream in = Google_meet.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null)
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);

    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();

    Credential credential = null;

    try {
      credential = flow.loadCredential("user");
    } catch (IOException e) {
      credential = authorizeNewUser(flow);
    }

    if (credential.getExpiresInSeconds() <= 60)
      credential.refreshToken();

    return credential;

  }

  private static Credential authorizeNewUser(GoogleAuthorizationCodeFlow flow) throws IOException {
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

  public static void create_meet_event(Session session, List<String> participants, String link) {
    try {

      final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
          .setApplicationName(APPLICATION_NAME)
          .build();

      LocalDate date = session.get_date_localDate();
      LocalTime start_time = session.get_start_time_localTime();
      LocalTime end_time = session.get_end_time_localTime();

      String date_string = String.format("%04d-%02d-%02d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());
      String start_time_string = String.format("%02d:%02d:00-07:00", start_time.getHour(), start_time.getMinute());
      String end_time_string = String.format("%02d:%02d:00-07:00", end_time.getHour(), end_time.getMinute());

      String start_date_time_string = String.format("%sT%s", date_string, start_time_string);
      String end_date_time_string = String.format("%sT%s", date_string, end_time_string);

      EventDateTime startDateTime = new EventDateTime()
          .setDateTime(new DateTime(start_date_time_string))
          .setTimeZone("Africa/Tunis");
      EventDateTime endDateTime = new EventDateTime()
          .setDateTime(new DateTime(end_date_time_string))
          .setTimeZone("Africa/Tunis");

      List<EventAttendee> attendees = new ArrayList<>();
      participants.stream().map(participant -> {
        EventAttendee attendee = new EventAttendee();
        attendee.setEmail(participant);
        return attendee;
      }).collect(Collectors.toList());

      CreateConferenceRequest createRequest = new CreateConferenceRequest()
          .setRequestId("1234567890");
      ConferenceData conferenceData = new ConferenceData()
          .setCreateRequest(createRequest);

      Event event = new Event()
          .setSummary("Myâlo Seance Meet")
          .setDescription(link)
          .setStart(startDateTime)
          .setEnd(endDateTime)
          .setConferenceData(conferenceData)
          .setAttendees(attendees);

      Log.file(session);

      event = service.events().insert("armen2001.bakir@gmail.com", event).execute();

    } catch (Exception e) {
      Log.file(e);

    }
  }

  public static void main(String... args) throws IOException, GeneralSecurityException {

    LocalDate date_1 = LocalDate.of(2023, 3, 10);
    LocalTime time_1 = LocalTime.of(12, 10, 00);
    LocalTime time_2 = LocalTime.of(13, 30, 00);

    Session session = new Session();
    session.set_date(date_1);
    session.set_start_time(time_1);
    session.set_end_time(time_2);
    session.set_topics("hello world");

    List<String> attendees = Arrays.asList("armen.bakir@esprit.tn");
    create_meet_event(session, attendees, "");

  }
}