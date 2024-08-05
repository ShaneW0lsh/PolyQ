package ru.kolomych.polyq.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolomych.polyq.deprecateddto.QueueDTO;
import ru.kolomych.polyq.deprecateddto.SubmissionSessionDTO;
import ru.kolomych.polyq.model.Queue;
import ru.kolomych.polyq.model.SubmissionSession;
import ru.kolomych.polyq.service.SubmissionSessionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/submission-session")
public class SubmissionSessionController {

    private final SubmissionSessionService submissionSessionService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<SubmissionSessionDTO> getMany() {
        return submissionSessionService.getSubmissionSessions().stream()
                .map(this::convertToSubmissionSessionDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SubmissionSessionDTO get(@PathVariable Long id) {
        return convertToSubmissionSessionDTO(submissionSessionService.getSubmissionSession(id));
    }

    // TODO for queue there should be it's own controller, because queues can be big,
    //  and it's bad to return the whole queue object, we should just return queue id in submissionSessionDto
//    @GetMapping("/queue")

    @PostMapping
    public List<QueueDTO> create(@RequestBody SubmissionSessionDTO submissionSessionDTO) {
        SubmissionSession submissionSession = submissionSessionService.createSubmissionSession(convertToSubmissionSession(submissionSessionDTO));
        return submissionSession.getQueues().stream().map(this::convertToQueueDTO).toList();
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> update(@RequestBody SubmissionSessionDTO submissionSessionDTO) {
        submissionSessionService.updateSubmissionSession(convertToSubmissionSession(submissionSessionDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
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
