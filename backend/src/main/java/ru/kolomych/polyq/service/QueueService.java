package ru.kolomych.polyq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kolomych.polyq.model.Queue;
import ru.kolomych.polyq.repository.QueueRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;

    public Queue getOne(Long id) {
        Optional<Queue> queueOptional = queueRepository.findById(id);
        return queueOptional.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Entity with id `%s` not found".formatted(id))
        );
    }
}
