package ru.kolomych.polyq.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class QueueDTO {

    private Long id;

    private TeacherDTO teacher;

    private Collection<QueueEntryDTO> entries;
}
