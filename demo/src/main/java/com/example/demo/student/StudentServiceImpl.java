package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        var studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new IllegalArgumentException("Student with email " + student.getEmail() + " already exists");
        }
        studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        var student = studentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Student with id " + id + " does not exist")
        );

        if (Objects.nonNull(name) && !name.equals(student.getName())) {
            student.setName(name);
        }

        if (Objects.nonNull(email) && !email.equals(student.getEmail())) {
            if (studentRepository.findStudentByEmail(email).isPresent()) {
                throw new IllegalArgumentException("Student with email " + student.getEmail() + " already exists");
            }
            student.setEmail(email);
        }
    }

    @Override
    public void deleteStudentById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student with id " + id + " does not exist");
        }
        studentRepository.deleteById(id);
    }
}
