package ru.kolomych.polyq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolomych.polyq.model.Teacher;
import ru.kolomych.polyq.repository.TeacherRepository;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Transactional(readOnly = true)
    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Teacher getTeacher(Long id) {

    }
}
