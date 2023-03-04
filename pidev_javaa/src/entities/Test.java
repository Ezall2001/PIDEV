package entities;

public class Test {

    private int id, min_points, duration;

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
        return "Test [id=" + id + ", min_points=" + min_points + ", duration=" + duration + "]";
    }

}
