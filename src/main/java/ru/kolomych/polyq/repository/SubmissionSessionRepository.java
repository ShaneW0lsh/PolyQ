package ru.kolomych.polyq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolomych.polyq.model.SubmissionSession;

@Repository
public interface SubmissionSessionRepository extends JpaRepository<SubmissionSession, Long> {
}
