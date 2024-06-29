package ru.kolomych.polyq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolomych.polyq.model.SubmissionSession;
import ru.kolomych.polyq.repository.SubmissionSessionRepository;
import ru.kolomych.polyq.util.NotFoundException;

import java.util.List;

@Service
public class SubmissionSessionService {

    private final SubmissionSessionRepository submissionSessionRepository;

    @Autowired
    public SubmissionSessionService(SubmissionSessionRepository submissionSessionRepository) {
        this.submissionSessionRepository = submissionSessionRepository;
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

    @Transactional
    public void saveSubmissionSession(SubmissionSession submissionSession) {
        submissionSessionRepository.save(submissionSession);
    }
}
