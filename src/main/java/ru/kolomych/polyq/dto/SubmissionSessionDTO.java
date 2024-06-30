package ru.kolomych.polyq.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
public class SubmissionSessionDTO {

    private Long id;

    private String discipline;

    @JsonAlias("date_and_time")
    private LocalDateTime dateAndTime;

    private Collection<TeacherDTO> teachers;
}
