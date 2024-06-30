package ru.kolomych.polyq.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolomych.polyq.dto.QueueDTO;
import ru.kolomych.polyq.dto.SubmissionSessionDTO;
import ru.kolomych.polyq.model.Queue;
import ru.kolomych.polyq.model.SubmissionSession;
import ru.kolomych.polyq.service.SubmissionSessionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/submission-sessions")
public class SubmissionSessionController {

    private final SubmissionSessionService submissionSessionService;
    private final ModelMapper modelMapper;

    public SubmissionSessionController(SubmissionSessionService submissionSessionService, ModelMapper modelMapper) {
        this.submissionSessionService = submissionSessionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<SubmissionSessionDTO> getSubmissionSessions() {
        return submissionSessionService.getSubmissionSessions().stream()
                .map(this::convertToSubmissionSessionDTO).collect(Collectors.toList());
    }

    @GetMapping
    public SubmissionSessionDTO getSubmissionSession(@RequestParam("id") Long id) {
        return convertToSubmissionSessionDTO(submissionSessionService.getSubmissionSession(id));
    }

//    @GetMapping("/queue")

    @PostMapping
    public List<QueueDTO> createSubmissionSession(@RequestBody SubmissionSessionDTO submissionSessionDTO) {
        SubmissionSession submissionSession = submissionSessionService.createSubmissionSession(convertToSubmissionSession(submissionSessionDTO));
        return submissionSession.getQueues().stream().map(this::convertToQueueDTO).toList();
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> updateSubmissionSession(@RequestBody SubmissionSessionDTO submissionSessionDTO) {
        submissionSessionService.updateSubmissionSession(convertToSubmissionSession(submissionSessionDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteSubmissionSession(@RequestParam("id") Long id) {
        submissionSessionService.deleteSubmissionSession(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private SubmissionSessionDTO convertToSubmissionSessionDTO(SubmissionSession submissionSession) {
        return modelMapper.map(submissionSession, SubmissionSessionDTO.class);
    }

    private SubmissionSession convertToSubmissionSession(SubmissionSessionDTO submissionSessionDTO) {
        return modelMapper.map(submissionSessionDTO, SubmissionSession.class);
    }

    private QueueDTO convertToQueueDTO(Queue queue) {
        return modelMapper.map(queue, QueueDTO.class);
    }
}
