package ru.kolomych.polyq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolomych.polyq.model.Role;
import ru.kolomych.polyq.model.User;
import ru.kolomych.polyq.repository.UserRepository;
import ru.kolomych.polyq.util.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException(String.format("User with \"username\" %s was not found", username)));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(String.format("Username `%s` not found", username));
        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(), user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
