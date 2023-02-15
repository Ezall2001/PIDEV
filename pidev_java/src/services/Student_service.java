package services;

import java.util.Arrays;
import java.util.List;

import entities.Student;
import types.Service_response;

public class Student_service extends Base_service {

    public Service_response<Integer> add_student(Student student) {
        return super.add(student, null);
    }

    public Service_response<Integer> modify_session_by_id(Student search_student,
            Student modification_student) {
        return super.modify(
                search_student,
                modification_student,
                Arrays.asList("id"),
                Arrays.asList("first_name", "last_name", "age", "picture_path", "password", "description"));
    }

    public Service_response<Integer> delete_student_by_id(Student student) {
        return super.delete(student, Arrays.asList("email"));
    }

    public Service_response<List<Student>> find_student_by_id(Student student) {
        return super.find(student, Arrays.asList("id"));
    }

    public Service_response<List<Student>> find_student_by_id(Integer id) {
        Student student = new Student();
        student.set_id(id);
        return find_student_by_id(student);
    }

    public Service_response<Integer> delete_student_by_id(Integer id) {
        Student student = new Student();
        student.set_id(id);
        return delete_student_by_id(student);
    }
}
