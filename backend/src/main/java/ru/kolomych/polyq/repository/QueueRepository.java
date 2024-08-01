package ru.kolomych.polyq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolomych.polyq.model.Queue;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
}