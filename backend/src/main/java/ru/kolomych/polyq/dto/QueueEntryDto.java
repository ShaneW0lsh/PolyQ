package ru.kolomych.polyq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.kolomych.polyq.model.QueueEntry}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueEntryDto {

    // ordinal should 100% be managed on server.
    Long ordinal;

    @JsonProperty("user_username")
    String userUsername;

    @JsonProperty("date_and_time")
    LocalDateTime dateAndTime;
}