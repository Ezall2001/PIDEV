package entities;
import types.Getter_setter_map;
import types.Getter_setter_pair;
import java.util.List;

public class Subject extends Base_entity {
  private Integer id;
  private String name ;
  private String description ,difficulty ;
  

  public Subject() {
    Getter_setter_map map = build_getter_setter_map(
    );
    set_getter_setter_map(map);
  }

  public Subject(int id, String name, String description, String difficulty) {
    this(name, description ,difficulty);
     this.id = id;
        
       
    
  }

  public Subject( String name, String description, String difficulty) {
    this();
    set_name(name);
    set_description(description);
    set_difficulty (difficulty) ;
  }

 

  public Integer get_id() {
    return id;
  }

  public void set_id(Integer id) {
    this.id = id;
  }

  public String get_name() {
    return name;
  }

  public void set_name(String name) {
    this.name = name;
  }

  public String get_description() {
    return description;
  }

  public void set_description(String description) {
    this.description = description;
  }


  public String get_difficulty() {
    return difficulty;
}


    public void set_difficulty (String difficulty) {
    this.difficulty  = difficulty;
    }  

   // public void add_courses(Course courses) {
  //    this.courses.add(courses);
      
 // }
  //public List  <Course> get_Courses(){
     // return courses;
 // }

  @Override
  public String toString() {
      return "Matiere{" + "id=" + id + ",nom_matiere =" + name +  
      ", description=" + description + ", difficulter=" + difficulty + '}';
  }
}