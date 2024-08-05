package ru.kolomych.polyq.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kolomych.polyq.dto.QueueDto;
import ru.kolomych.polyq.dto.QueueEntryDto;
import ru.kolomych.polyq.mapper.QueueEntryMapper;
import ru.kolomych.polyq.mapper.QueueMapper;
import ru.kolomych.polyq.service.QueueService;

// TODO learn how to use mapstruct to create this stuff efficiently with amplicode
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueService queueService;
    private final QueueEntryMapper queueEntryMapper;
    private final QueueMapper queueMapper;

    // TODO add DTO
    @GetMapping("/{id}")
    public ResponseEntity<QueueDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(queueMapper.toDto(queueService.getOne(id)));
    }

    // for some reason some users get added to queue, and others not. don't know the reason yet
    @PostMapping("/{id}")
    public ResponseEntity<QueueDto> addEntry(@PathVariable Long id, @RequestBody @Valid QueueEntryDto queueEntryDto) {
        return ResponseEntity.ok(queueMapper.toDto(
                queueService.addEntry(id, queueEntryMapper.toEntity(queueEntryDto))
        ));
    }
}
