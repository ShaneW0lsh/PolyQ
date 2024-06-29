package ru.kolomych.polyq.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    @NotEmpty(message = "username field can't be empty")
    private String username;

    private String firstName;
    private String lastName;

    private String password;

    private Collection<RoleDTO> roles;
}
