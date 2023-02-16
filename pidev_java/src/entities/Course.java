package entities;


import types.Getter_setter_map;
import types.Getter_setter_pair;
import java.util.List;

public class Course extends Base_entity {
  private Integer id,id_subject;
  private String name ;
  private String description, difficulty;
   
    Subject subject ;
  
    
  

  public Course() {
    Getter_setter_map map = build_getter_setter_map(
    );
    set_getter_setter_map(map);
  }



  public Course(int id,String name ) {

    this.id = id;
    this.name = name;
    
}

public Course(int id, String name, String description, String difficulty) {
  
  this(name, description ,difficulty);
    set_id(id);
}
  public Course(int id, String name, String description, String difficulty,int id_subject) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.difficulty = difficulty;
    this.id_subject = id_subject;
   
  }

  public Course(String name, String description, String difficulty) {
    this();
    set_name(name);
    set_description(description);
    set_difficulty(difficulty); 
  }

 

  public int get_id() {
    return id;
}
public String get_name() {
    return name;
}

public String get_description() {
    return description;
}

public String get_difficulty() {
    return difficulty;
}

public void set_id(int id) {
    this.id = id;
    }
public void set_name(String name) {
this.name = name;
}
public void set_description(String description) {
this.description = description;
} 

public void set_difficulty (String difficulty) {
this.difficulty  = difficulty;
}  


@Override
public String toString() {
    return "cour{" + "id=" + id + ",nom_cour =" + name +  
    ", description=" + description + ", difficulter=" + difficulty + '}';
}
}
