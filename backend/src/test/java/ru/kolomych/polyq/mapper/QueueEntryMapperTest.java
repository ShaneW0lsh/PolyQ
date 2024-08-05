package ru.kolomych.polyq.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kolomych.polyq.dto.QueueEntryDto;
import ru.kolomych.polyq.model.Queue;
import ru.kolomych.polyq.model.QueueEntry;
import ru.kolomych.polyq.model.Role;
import ru.kolomych.polyq.model.User;
import ru.kolomych.polyq.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueueEntryMapperTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private QueueEntryMapper queueEntryMapper = Mappers.getMapper(QueueEntryMapper.class);

    private User user;
    private QueueEntry queueEntry;
    private QueueEntryDto queueEntryDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setId(1L);
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setPassword("password");
        user.setRoles(List.of(new Role("ROLE_USER")));

        queueEntry = new QueueEntry();
        queueEntry.setOrdinal(1L);
        queueEntry.setUser(user);
        queueEntry.setDateAndTime(LocalDateTime.now());

        queueEntryDto = new QueueEntryDto();
        queueEntryDto.setOrdinal(1L);
        queueEntryDto.setUserUsername("testUser");
        queueEntryDto.setDateAndTime(LocalDateTime.now());
    }

    @Test
    void testToDto() {
        QueueEntryDto dto = queueEntryMapper.toDto(queueEntry);
        assertNotNull(dto);
        assertEquals(queueEntry.getOrdinal(), dto.getOrdinal());
        assertEquals(queueEntry.getUser().getUsername(), dto.getUserUsername());
        assertEquals(queueEntry.getDateAndTime(), dto.getDateAndTime());
    }

    @Test
    void testToEntity() {
        when(userService.find(anyString())).thenReturn(user);

        QueueEntry entity = queueEntryMapper.toEntity(queueEntryDto);

        assertNotNull(entity);
        assertEquals(queueEntryDto.getOrdinal(), entity.getOrdinal());
        assertEquals(queueEntryDto.getDateAndTime(), entity.getDateAndTime());

        assertEquals(queueEntryDto.getUserUsername(), entity.getUser().getUsername());
        assertEquals(user.getId(), entity.getUser().getId());
        assertEquals(user.getFirstName(), entity.getUser().getFirstName());
        assertEquals(user.getLastName(), entity.getUser().getLastName());
        assertEquals(user.getPassword(), entity.getUser().getPassword());
        assertEquals(user.getRoles(), entity.getUser().getRoles());

        verify(userService, times(1)).find(queueEntryDto.getUserUsername());
    }
}
