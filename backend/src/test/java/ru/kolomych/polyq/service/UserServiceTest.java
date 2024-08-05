package ru.kolomych.polyq.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kolomych.polyq.exception.BadRequestException;
import ru.kolomych.polyq.exception.NotFoundException;
import ru.kolomych.polyq.model.Role;
import ru.kolomych.polyq.model.User;
import ru.kolomych.polyq.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindAll() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(users, result);
        verify(userRepository).findAll();
    }

    @Test
    void testFindUser_UserExists() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userService.find(username);

        assertEquals(user, result);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testFindUser_UserNotFound() {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.find(username));
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testCreateUser_UserDoesNotExist() {
        User user = new User();
        user.setUsername("testUser");
        Role role = new Role("ROLE_USER");
        user.setRoles(List.of(role));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(roleService.getRoleByName(anyString())).thenReturn(role);

        userService.create(user);

        verify(userRepository).save(user);
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> userService.create(user));
        verify(userRepository, never()).save(user);
    }

    @Test
    void testUpdateUser_UserExists() {
        User existingUser = new User();
        existingUser.setUsername("testUser");
        existingUser.setFirstName("OldFirstName");

        User updatedUser = new User();
        updatedUser.setUsername("testUser");
        updatedUser.setFirstName("NewFirstName");

        when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(existingUser));

        userService.update(updatedUser);

        assertEquals("NewFirstName", existingUser.getFirstName());
        verify(userRepository).save(existingUser);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(user));
        verify(userRepository, never()).save(user);
    }

    @Test
    void testDeleteUser_UserExists() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        userService.delete(username);

        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.delete(username));
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setRoles(List.of(new Role("ROLE_USER")));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        org.springframework.security.core.userdetails.UserDetails userDetails = userService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
}