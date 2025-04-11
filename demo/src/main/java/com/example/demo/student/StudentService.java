package com.example.demo.student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();

    void addStudent(Student student);

    void updateStudent(Long id, String name, String email);

    void deleteStudentById(Long id);
}
