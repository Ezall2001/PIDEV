package entities;

public class Test {

    private int id, min_points, duration;
    private Subject subject;
    private Course course;

    private enum Type {
        matiere, cours;
    }

    private Type type;

    public Test() {
    }

    public Test(int id, int min_points, int duration, String type) {
        this.id = id;
        this.min_points = min_points;
        this.duration = duration;
        this.setType(type);
    }

    public String getType() {
        return type.name();
    }

    public void setType(String type) {
        this.type = Type.valueOf(type);
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public int get_min_points() {
        return min_points;
    }

    public void set_min_points(int min_points) {
        this.min_points = min_points;
    }

    public int get_duration() {
        return duration;
    }

    public void set_duration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        String type = getType();
        return "ID : " + id + " Seuil :" + min_points + " Durée :" + duration + " Type :" + type;
    }

}
