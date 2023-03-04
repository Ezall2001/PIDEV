package utils;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTime_helpers {

  public static Date localDate_sqlDate_converter(LocalDate local_date) {
    return Date.valueOf(local_date);
  }

  public static LocalDate localDate_sqlDate_converter(Date sql_date) {
    return sql_date.toLocalDate();
  }

  public static Time localTime_sqlTime_converter(LocalTime local_time) {
    return Time.valueOf(local_time);
  }

  public static LocalTime localTime_sqlTime_converter(Time sql_time) {
    return sql_time.toLocalTime();
  }
}
