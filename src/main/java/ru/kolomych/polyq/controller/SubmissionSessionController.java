package ru.kolomych.polyq.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import org.springframework.web.bind.annotation.*;
import ru.kolomych.polyq.dto.SubmissionSessionDTO;
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

    @PostMapping
    public ResponseEntity<HttpStatus> createSubmissionSession(@RequestBody SubmissionSessionDTO submissionSessionDTO) {
        submissionSessionService.createSubmissionSession(convertToSubmissionSession(submissionSessionDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> updateSubmissionSession(@RequestBody SubmissionSessionDTO submissionSessionDTO) {
        submissionSessionService.updateSubmissionSession(convertToSubmissionSession(submissionSessionDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private SubmissionSessionDTO convertToSubmissionSessionDTO(SubmissionSession submissionSession) {
        return modelMapper.map(submissionSession, SubmissionSessionDTO.class);
    }

    private SubmissionSession convertToSubmissionSession(SubmissionSessionDTO submissionSessionDTO) {
        return modelMapper.map(submissionSessionDTO, SubmissionSession.class);
    }
}
