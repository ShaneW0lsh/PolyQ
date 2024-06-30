package ru.kolomych.polyq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolomych.polyq.model.QueueEntry;

@Repository
public interface QueueEntryRepository extends JpaRepository<QueueEntry, Long> {
}
