package entities;

import java.util.Arrays;
import java.security.MessageDigest;
import utils.Log;

public class User {
  public static enum Type {
    ADMIN, STUDENT
  };

  public static enum Level {
    NEWCOMMER, BEGINNER, COMPETENT, PROFICIENT, EXPERT
  };

  private Integer id;
  private String first_name, last_name, bio, avatar_path;
  private Integer age, score;
  private String email, password, hashed_password;
  private Type type;
  private Class_esprit class_esprit;

  public User() {
  }

  // AUTH USER
  public User(String email, String hashed_password) {
    set_email(email);
    set_hashed_password(hashed_password);
  }

  public User(String email) {
    set_email(email);
  }

  // GET ADMIN
  public User(Integer id, String first_name, String last_name) {
    set_first_name(first_name);
    set_last_name(last_name);
    type = Type.ADMIN;
  }

  // GET USER
  public User(Integer id, String first_name, String last_name, String bio, Integer age, Integer score, String email,
      String avatar_path, String type) {
    set_id(id);
    set_first_name(first_name);
    set_last_name(last_name);
    set_age(age);
    set_score(score);
    set_bio(bio);
    set_email(email);
    set_type(type);
  }

  // CREATE STUDENT
  public User(String first_name, String last_name, Integer age, String bio, String email,
      String password) {
    set_first_name(first_name);
    set_last_name(last_name);
    set_age(age);
    set_bio(bio);
    set_email(email);
    set_password(password);
    set_hashed_password();
    type = Type.STUDENT;
  }

  public Integer get_id() {
    return id;
  }

  public void set_id(Integer id) {
    this.id = id;
  }

  public String get_first_name() {
    return first_name;
  }

  public void set_first_name(String first_name) {
    this.first_name = first_name;
  }

  public String get_last_name() {
    return last_name;
  }

  public void set_last_name(String last_name) {
    this.last_name = last_name;
  }

  public String get_bio() {
    return bio;
  }

  public void set_bio(String bio) {
    this.bio = bio;
  }

  public String get_avatar_path() {
    return avatar_path;
  }

  public void set_avatar_path(String avatar_path) {
    this.avatar_path = avatar_path;
  }

  public Integer get_age() {
    return age;
  }

  public void set_age(Integer age) {
    this.age = age;
  }

  public Integer get_score() {
    return score;
  }

  public void set_score(Integer score) {
    this.score = score;
  }

  public String get_email() {
    return email;
  }

  public void set_email(String email) {
    this.email = email;
  }

  public String get_password() {
    return password;
  }

  public void set_password(String password) {
    this.password = password;
  }

  public String get_hashed_password() {
    return hashed_password;
  }

  public void set_hashed_password(String hashed_password) {
    this.hashed_password = hashed_password;
  }

  public void set_hashed_password() {
    this.hashed_password = hash_password();
  }

  public String hash_password() {
    try {
      MessageDigest message_digest = MessageDigest.getInstance("SHA-256");
      message_digest.update(password.getBytes());
      byte[] bytes = message_digest.digest();
      StringBuilder string_builder = new StringBuilder();
      for (byte b : bytes) {
        string_builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
      }
      return string_builder.toString();
    } catch (Exception e) {
      Log.file(e.getMessage());
      return null;
    }
  }

  public Boolean equals_to_user(User user) {
    return hashed_password.equals(user.get_hashed_password()) && email.equals(user.get_email());
  }

  public Type get_type() {
    return this.type;
  }

  public void set_type(String type) {
    try {
      this.type = Type.valueOf(type);
    } catch (Exception e) {
      Log.file(e.getMessage());
    }
  }

  public Class_esprit get_class_esprit() {
    return class_esprit;
  }

  public void set_class_esprit(Class_esprit class_esprit) {
    this.class_esprit = class_esprit;
  }

  public static Integer compute_level_breakpoint_score(Level level) {
    return compute_level_breakpoint_score(Arrays.asList(Level.values()).indexOf(level));
  }

  public static Integer compute_level_breakpoint_score(Integer level_index) {
    return (int) (Math.pow((level_index + 1), 2) * 100);
  }

  public Level compute_level() {
    Integer level_index = 0;
    while (score > compute_level_breakpoint_score(level_index))
      level_index++;
    return Level.values()[level_index];
  }

  public Integer compute_current_level_score() {
    Integer level_index = 0;
    Integer level_breakpoint_score = compute_level_breakpoint_score(level_index);
    Integer current_level_score = score;

    while (score > level_breakpoint_score) {
      current_level_score -= level_breakpoint_score;
      level_index++;
      level_breakpoint_score = compute_level_breakpoint_score(level_index);
    }

    return current_level_score;

  }

  @Override
  public String toString() {
    return "User [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", bio=" + bio
        + ", avatar_path=" + avatar_path + ", age=" + age + ", score=" + score + ", email=" + email + ", password="
        + password + ", type=" + type + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
    result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
    result = prime * result + ((bio == null) ? 0 : bio.hashCode());
    result = prime * result + ((avatar_path == null) ? 0 : avatar_path.hashCode());
    result = prime * result + ((age == null) ? 0 : age.hashCode());
    result = prime * result + ((score == null) ? 0 : score.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    result = prime * result + ((hashed_password == null) ? 0 : hashed_password.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (first_name == null) {
      if (other.first_name != null)
        return false;
    } else if (!first_name.equals(other.first_name))
      return false;
    if (last_name == null) {
      if (other.last_name != null)
        return false;
    } else if (!last_name.equals(other.last_name))
      return false;
    if (bio == null) {
      if (other.bio != null)
        return false;
    } else if (!bio.equals(other.bio))
      return false;
    if (avatar_path == null) {
      if (other.avatar_path != null)
        return false;
    } else if (!avatar_path.equals(other.avatar_path))
      return false;
    if (age == null) {
      if (other.age != null)
        return false;
    } else if (!age.equals(other.age))
      return false;
    if (score == null) {
      if (other.score != null)
        return false;
    } else if (!score.equals(other.score))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (password == null) {
      if (other.password != null)
        return false;
    } else if (!password.equals(other.password))
      return false;
    if (hashed_password == null) {
      if (other.hashed_password != null)
        return false;
    } else if (!hashed_password.equals(other.hashed_password))
      return false;
    if (type != other.type)
      return false;
    return true;
  }

}