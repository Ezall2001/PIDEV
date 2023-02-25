package entities;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import utils.DateTime_helpers;

public class Session {
  private Integer id;
  private Double price;
  private LocalDate date;
  private LocalTime start_time, end_time;

  public Session() {

  }

  public Session(Integer id, Double price, LocalDate date, LocalTime start_time, LocalTime end_time) {
    this(price, date, start_time, end_time);
    set_id(id);
  }

  public Session(Double price, LocalDate date, LocalTime start_time, LocalTime end_time) {
    this();
    set_price(price);
    set_date(date);
    set_start_time(start_time);
    set_end_time(end_time);
  }

  public Session(Integer id, Double price, Date date, Time start_time, Time end_time) {
    this(price, date, start_time, end_time);
    set_id(id);
  }

  public Session(Double price, Date date, Time start_time, Time end_time) {
    this();
    set_price(price);
    set_date(date);
    set_start_time(start_time);
    set_end_time(end_time);
  }

  public Integer get_id() {
    return id;
  }

  public void set_id(Integer id) {
    this.id = id;
  }

  public Double get_price() {
    return price;
  }

  public void set_price(Double price) {
    this.price = price;
  }

  public LocalDate get_date_localDate() {
    return date;
  }

  public Date get_date_sqlDate() {
    return DateTime_helpers.localDate_sqlDate_converter(date);
  }

  public void set_date(LocalDate date) {
    this.date = date;
  }

  public void set_date(Date date) {
    this.date = DateTime_helpers.localDate_sqlDate_converter(date);
  }

  public LocalTime get_start_time_localTime() {
    return start_time;
  }

  public Time get_start_time_sqlTime() {
    return DateTime_helpers.localTime_sqlTime_converter(start_time);
  }

  public void set_start_time(LocalTime start_time) {
    this.start_time = start_time;
  }

  public void set_start_time(Time heurTime) {
    this.start_time = DateTime_helpers.localTime_sqlTime_converter(heurTime);
  }

  public LocalTime get_end_time_localTime() {
    return end_time;
  }

  public Time get_end_time_sqlTime() {
    return DateTime_helpers.localTime_sqlTime_converter(end_time);
  }

  public void set_end_time(LocalTime end_time) {
    this.end_time = end_time;
  }

  public void set_end_time(Time heurTime) {
    this.end_time = DateTime_helpers.localTime_sqlTime_converter(heurTime);
  }

  @Override
  public String toString() {
    return "Session [id=" + id + ", price=" + price + ", date=" + date + ", start_time=" + start_time + ", end_time="
        + end_time + "]";
  }

}