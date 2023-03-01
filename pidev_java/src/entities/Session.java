package entities;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import utils.DateTime_helpers;

public class Session {
  private Integer id;
  private Double price;
  private LocalDate date;
  private LocalTime start_time, end_time;
  private String topics;
  private List<Resource> resources;
  private User user;
  private Course course;

  public Session() {

  }

  public Session(Double price, LocalDate date, LocalTime start_time, LocalTime end_time, String topics) {
    set_price(price);
    set_date(date);
    set_start_time(start_time);
    set_end_time(end_time);
    set_topics(topics);
  }

  public Session(Integer id, Double price, Date date, Time start_time, Time end_time, String topics) {
    set_id(id);
    set_price(price);
    set_date(date);
    set_start_time(start_time);
    set_end_time(end_time);
    set_topics(topics);
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

  public String get_price_string() {
    return String.format("%.3f DT", price);
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

  public String get_date_string() {
    return date.toString();
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

  public String get_start_time_string() {
    return start_time.toString();
  }

  public void set_start_time(LocalTime start_time) {
    this.start_time = start_time;
  }

  public void set_start_time(Time start_time) {
    this.start_time = DateTime_helpers.localTime_sqlTime_converter(start_time);
  }

  public LocalTime get_end_time_localTime() {
    return end_time;
  }

  public Time get_end_time_sqlTime() {
    return DateTime_helpers.localTime_sqlTime_converter(end_time);
  }

  public String get_end_time_string() {
    return end_time.toString();
  }

  public void set_end_time(LocalTime end_time) {
    this.end_time = end_time;
  }

  public void set_end_time(Time start_time) {
    this.end_time = DateTime_helpers.localTime_sqlTime_converter(start_time);
  }

  public String get_time_interval() {
    return String.format("%02d:%02d -> %02d:%02d", start_time.getHour(), start_time.getMinute(), end_time.getHour(),
        end_time.getMinute());
  }

  public String get_topics() {
    return topics;
  }

  public void set_topics(String topics) {
    this.topics = topics;
  }

  public List<Resource> get_resources() {
    return resources;
  }

  public void set_resources(List<Resource> resources) {
    this.resources = resources;
  }

  public User get_user() {
    return user;
  }

  public void set_user(User user) {
    this.user = user;
  }

  public Course get_course() {
    return course;
  }

  public void set_course(Course course) {
    this.course = course;
  }

  @Override
  public String toString() {
    return "Session [id=" + id + ", price=" + price + ", date=" + date + ", start_time=" + start_time + ", end_time="
        + end_time + ", topics=" + topics + ", resources=" + resources + ", user=" + user + ", course="
        + course + "]";
  }

}