package ru.kolomych.polyq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kolomych.polyq.model.Teacher;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t WHERE CONCAT(t.lastName, ' ', t.firstName, ' ', t.middleName) = ?1")
    Optional<Teacher> findByFullName(String fullName);
}
