package ru.kolomych.polyq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.kolomych.polyq.exception.BadRequestException;
import ru.kolomych.polyq.exception.NotFoundException;
import ru.kolomych.polyq.model.Queue;
import ru.kolomych.polyq.model.QueueEntry;
import ru.kolomych.polyq.repository.QueueRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;

    @Transactional(readOnly = true)
    public Queue getOne(Long id) {
        Optional<Queue> queueOptional = queueRepository.findById(id);
        return queueOptional.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Queue with id `%s` not found".formatted(id))
        );
    }

    @Transactional(readOnly = true)
    public List<Queue> getAll() {
        return queueRepository.findAll();
    }

    @Transactional
    public Queue addEntry(Long queueId, QueueEntry entry) {
        Queue queue = queueRepository.findById(queueId).orElseThrow(
                () -> new NotFoundException("Queue with id %s was not found".formatted(queueId)));

        Long entryOrdinalValue = queue.getEntries().stream().map(QueueEntry::getOrdinal)
                .max(Long::compare)
                .map(aLong -> aLong + 1).orElse(1L);
        entry.setOrdinal(entryOrdinalValue);
        entry.setQueue(queue);

        boolean userIsInLine = queue.getEntries().stream()
                .map(e -> e.getUser().getId()).collect(Collectors.toSet()).contains(entry.getUser().getId());
        if (userIsInLine) {
            throw new BadRequestException("User with id `%s` is already in queue".formatted(entry.getUser().getId()));
        }

        queue.getEntries().add(entry);
        return queueRepository.save(queue);
    }
}
