package ru.kolomych.polyq.deprecateddto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

// TODO don't send queueDTO, send it's id. for that we'll have to write mapper :(
@Getter
@Setter
public class SubmissionSessionDTO {

    private Long id;

    private String discipline;

    @JsonAlias("date_and_time")
    private LocalDateTime dateAndTime;

    private Collection<TeacherDTO> teachers;

    private Collection<QueueDTO> queues;
}
