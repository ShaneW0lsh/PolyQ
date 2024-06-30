package ru.kolomych.polyq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolomych.polyq.model.Teacher;
import ru.kolomych.polyq.repository.TeacherRepository;
import ru.kolomych.polyq.util.NotFoundException;

import java.util.List;
import java.util.Optional;

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
    public Teacher getTeacher(String fullName) throws NotFoundException {
        return teacherRepository.findByFullName(fullName).orElseThrow(() ->
                new NotFoundException(String.format("Teacher with \"name\" %s was not found", fullName))
        );
    }

    @Transactional
    public void saveTeacher(Teacher teacher) {
        Optional<Teacher> oldTeacher = teacherRepository.findByFullName(
                teacher.getLastName() + ' ' + teacher.getFirstName() + ' ' + teacher.getMiddleName()
        );
        oldTeacher.ifPresentOrElse(
                value -> {
                    teacher.setId(value.getId());
                    teacherRepository.save(value);
                },
                () -> teacherRepository.save(teacher)
        );
    }
}
