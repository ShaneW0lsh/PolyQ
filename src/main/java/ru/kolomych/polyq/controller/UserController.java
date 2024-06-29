package ru.kolomych.polyq.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kolomych.polyq.dto.UserDTO;
import ru.kolomych.polyq.model.User;
import ru.kolomych.polyq.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(this::convertToUserDTO).toList();
    }

    @GetMapping
    public UserDTO getUser(@RequestParam("username") String username) {
        return convertToUserDTO(userService.findUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserDTO userDTO) {
        User user = convertToUser(userDTO);
        userService.saveUser(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/multiple")
    public ResponseEntity<HttpStatus> createUsers(@RequestBody List<UserDTO> userDTOList) {
        for (UserDTO userDTO : userDTOList) {
            userService.saveUser(convertToUser(userDTO));
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<HttpStatus> changeUser(@RequestBody UserDTO userDTO) {
        User user = convertToUser(userDTO);
        userService.saveUser(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam("username") String username) {
        userService.deleteUser(username);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
