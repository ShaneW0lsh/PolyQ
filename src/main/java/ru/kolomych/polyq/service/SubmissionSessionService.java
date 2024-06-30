package ru.kolomych.polyq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolomych.polyq.model.SubmissionSession;
import ru.kolomych.polyq.model.Teacher;
import ru.kolomych.polyq.repository.SubmissionSessionRepository;
import ru.kolomych.polyq.repository.TeacherRepository;
import ru.kolomych.polyq.util.BadRequestException;
import ru.kolomych.polyq.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubmissionSessionService {

    private final SubmissionSessionRepository submissionSessionRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public SubmissionSessionService(SubmissionSessionRepository submissionSessionRepository, TeacherRepository teacherRepository) {
        this.submissionSessionRepository = submissionSessionRepository;
        this.teacherRepository = teacherRepository;
    }

    @Transactional(readOnly = true)
    public List<SubmissionSession> getSubmissionSessions() {
        return submissionSessionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SubmissionSession getSubmissionSession(Long id) throws NotFoundException {
        return submissionSessionRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("SubmissionSession with \"id\" %d was not found", id)));
    }

    // If teacher in submissionSession has an id, new teacher with this name should not be added to DB, rather it should be used instead
    // If teacher does not have an id, it means it is not in DB yet => add it to DB
    @Transactional
    public void createSubmissionSession(SubmissionSession submissionSession) {
        List<Teacher> existingTeachers = new ArrayList<>();
        for (Teacher teacher : submissionSession.getTeachers()) {
            Optional<Teacher> oldTeacher = teacherRepository.findByFullName(teacher.getLastName() + ' ' + teacher.getFirstName() + ' ' + teacher.getMiddleName());
            oldTeacher.ifPresentOrElse(
                    existingTeachers::add,
                    () -> existingTeachers.add(teacher)
            );
        }
        submissionSession.setTeachers(existingTeachers);
        submissionSessionRepository.save(submissionSession);
    }

    // TODO: create a separate service for mapping submissionession to teachers,
    //  because right now update doesn't work like it's supposed to do with teachers

    @Transactional
    public void updateSubmissionSession(SubmissionSession submissionSession) {
        Long id = submissionSession.getId();
        if (id == null) {
            throw new BadRequestException("SubmissionSession \"id\" field should not be null");
        }

        Optional<SubmissionSession> existingSubmissionSession = submissionSessionRepository.findById(id);

        existingSubmissionSession.ifPresentOrElse(
                value -> {
                    if (submissionSession.getDiscipline() == null)
                        submissionSession.setDiscipline(value.getDiscipline());
                    if (submissionSession.getDateAndTime() == null)
                        submissionSession.setDateAndTime(value.getDateAndTime());
                    if (submissionSession.getTeachers() == null)
                        submissionSession.setTeachers(value.getTeachers());
                },
                () -> {
                    throw new NotFoundException(
                            String.format("SubmissionSession with \"id\" = %d was not found", id)
                    );
                }
        );
        submissionSessionRepository.save(submissionSession);
    }
}
