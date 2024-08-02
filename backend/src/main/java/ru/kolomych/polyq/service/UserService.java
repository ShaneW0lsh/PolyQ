package ru.kolomych.polyq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolomych.polyq.exception.BadRequestException;
import ru.kolomych.polyq.model.Role;
import ru.kolomych.polyq.model.User;
import ru.kolomych.polyq.repository.UserRepository;
import ru.kolomych.polyq.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User find(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(String.format("User with \"username\" %s was not found", username))
        );
    }

    @Transactional
    public void create(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        existingUser.ifPresent(value -> {
            throw new BadRequestException(String.format("User with username %s already exists", value.getUsername()));
        });

        // TODO extract this into a method, maybe util class or something
        List<Role> existingRoles = new ArrayList<>();
        for (Role role : user.getRoles())
            existingRoles.add(roleService.getRoleByName(role.getName()));
        user.setRoles(existingRoles);

        userRepository.save(user);
    }

    @Transactional
    public void update(User inputUser) {
        Optional<User> optionalSavedUser = userRepository.findByUsername(inputUser.getUsername());
        if (optionalSavedUser.isEmpty())
            throw new NotFoundException(String.format("User with username %s was not found", inputUser.getUsername()));

        User savedUser = optionalSavedUser.get();

        if (inputUser.getFirstName() != null) {
            savedUser.setFirstName(inputUser.getFirstName());
        }
        if (inputUser.getLastName() != null) {
            savedUser.setLastName(inputUser.getLastName());
        }
        if (inputUser.getUsername() != null) {
            savedUser.setUsername(inputUser.getUsername());
        }
        if (inputUser.getPassword() != null) {
            savedUser.setPassword(inputUser.getPassword());
        }
        if (inputUser.getRoles() != null) {
            List<Role> existingRoles = new ArrayList<>();
            for (Role role : inputUser.getRoles())
                existingRoles.add(roleService.getRoleByName(role.getName()));
            savedUser.setRoles(existingRoles);
        }
        userRepository.save(savedUser);
    }

    @Transactional
    public void delete(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresentOrElse(userRepository::delete, () -> {
            throw new NotFoundException(String.format("User with username %s was not found", username));
        });
    }

    // Might need to be transactional I got no idea
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(String.format("Username `%s` not found", username));
        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(), user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }
}
