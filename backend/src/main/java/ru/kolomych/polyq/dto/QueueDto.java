package ru.kolomych.polyq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Collection;

/**
 * DTO for {@link ru.kolomych.polyq.model.Queue}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueDto {

    Long id;

    @JsonProperty("teacher_first_name")
    String teacherFirstName;

    @JsonProperty("teacher_last_name")
    String teacherLastName;

    @JsonProperty("teacher_middle_name")
    String teacherMiddleName;

    Collection<QueueEntryDto> entries;
}