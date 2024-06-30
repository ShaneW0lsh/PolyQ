package ru.kolomych.polyq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolomych.polyq.model.Queue;
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

    private final String SUBMISSION_SESSION_NOT_FOUND = "SubmissionSession with \"id\" = %d was not found!";

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
                new NotFoundException(String.format(SUBMISSION_SESSION_NOT_FOUND, id)));
    }

    // If teacher in submissionSession has an id, new teacher with this name should not be added to DB, rather it should be used instead
    // If teacher does not have an id, it means it is not in DB yet => add it to DB
    @Transactional
    public SubmissionSession createSubmissionSession(SubmissionSession submissionSession) {
        List<Teacher> existingTeachers = getExistingTeachers(submissionSession);
        submissionSession.setTeachers(existingTeachers);

        List<Queue> queues = new ArrayList<>();
        for (Teacher teacher : submissionSession.getTeachers()) {
            Queue queue = new Queue();
            queue.setSubmissionSession(submissionSession);
            queue.setTeacher(teacher);
            queues.add(queue);
        }
        submissionSession.setQueues(queues);

        submissionSessionRepository.save(submissionSession);
        return submissionSession;
    }

    private List<Teacher> getExistingTeachers(SubmissionSession submissionSession) {
        List<Teacher> existingTeachers = new ArrayList<>();
        for (Teacher teacher : submissionSession.getTeachers()) {
            Optional<Teacher> oldTeacher = teacherRepository.findByFullName(
                    teacher.getLastName() + ' ' + teacher.getFirstName() + ' ' + teacher.getMiddleName()
            );
            oldTeacher.ifPresentOrElse(
                    existingTeachers::add,
                    () -> existingTeachers.add(teacher)
            );
        }
        return existingTeachers;
    }

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
                    else
                        submissionSession.setTeachers(getExistingTeachers(submissionSession));
                },
                () -> {
                    throw new NotFoundException(
                            String.format(SUBMISSION_SESSION_NOT_FOUND, id)
                    );
                }
        );
        submissionSessionRepository.save(submissionSession);
    }

    @Transactional
    public void deleteSubmissionSession(Long id) {
        Optional<SubmissionSession> submissionSession = submissionSessionRepository.findById(id);
        submissionSession.ifPresentOrElse(
                value -> submissionSessionRepository.deleteById(id),
                () -> {
                    throw new NotFoundException(
                            String.format(SUBMISSION_SESSION_NOT_FOUND, id)
                    );
                }
        );
    }
}