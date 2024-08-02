package ru.kolomych.polyq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kolomych.polyq.model.Queue;
import ru.kolomych.polyq.service.QueueService;

// TODO learn how to use mapstruct to create this stuff efficiently with amplicode
@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    @GetMapping("/{id}")
    public Queue getOne(@PathVariable Long id) {
        return queueService.getOne(id);
    }
}
