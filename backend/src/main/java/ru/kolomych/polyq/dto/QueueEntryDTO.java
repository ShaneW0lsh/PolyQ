package ru.kolomych.polyq.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QueueEntryDTO {
    private Long id;

    private Long ordinal;

    private UserDTO user;

    private LocalDateTime dateAndTime;
}
