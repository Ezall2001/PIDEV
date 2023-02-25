package config;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTime_outils {

  public static Date convertisseur_localDate_sqlDate(LocalDate local_date) {
    return Date.valueOf(local_date);
  }

  public static LocalDate convertisseur_localDate_sqlDate(Date date) {
    return date.toLocalDate();
  }

  public static Time convertisseur_localTime_sqlTime(LocalTime local_time) {
    return Time.valueOf(local_time);
  }

  public static LocalTime convertisseur_localTime_sqlTime(Time time) {
    return time.toLocalTime();
  }
}
