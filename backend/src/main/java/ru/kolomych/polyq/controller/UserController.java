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

/*
    TODO: POST and PATCH mappings, as well as
        deletes should also return DTO of the User
*/
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<UserDTO> getAll() {
        return userService.findAll().stream().map(this::convertToUserDTO).toList();
    }

    @GetMapping
    public UserDTO get(@RequestParam("username") String username) {
        return convertToUserDTO(userService.find(username));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody UserDTO userDTO) {
        userService.create(convertToUser(userDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> update(@RequestBody UserDTO userDTO) {
        userService.update(convertToUser(userDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@RequestParam("username") String username) {
        userService.delete(username);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
