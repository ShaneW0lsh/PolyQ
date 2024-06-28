package ru.kolomych.polyq.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {

    @NotEmpty(message = "name field can't be empty")
    private String name;
}
