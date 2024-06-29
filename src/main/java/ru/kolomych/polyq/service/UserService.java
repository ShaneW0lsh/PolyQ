package ru.kolomych.polyq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolomych.polyq.model.Role;
import ru.kolomych.polyq.model.User;
import ru.kolomych.polyq.repository.UserRepository;
import ru.kolomych.polyq.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException("User with this username was not found!"));
    }

    @Transactional
    public void saveUser(User user) throws NotFoundException {
        Optional<User> oldUser = userRepository.findByUsername(user.getUsername());
        oldUser.ifPresent(value -> user.setId(value.getId()));

        List<Role> existingRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            existingRoles.add(roleService.getRoleByName(role.getName()));
        }

        user.setRoles(existingRoles);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String username) throws NotFoundException {
        User user = findUserByUsername(username);
        userRepository.delete(user);
    }
}
