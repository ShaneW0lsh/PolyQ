package ru.kolomych.polyq.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kolomych.polyq.dto.QueueEntryDto;
import ru.kolomych.polyq.model.QueueEntry;
import ru.kolomych.polyq.model.User;
import ru.kolomych.polyq.service.UserService;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class QueueEntryMapper {

    @Autowired
    private UserService userService;

    @Mapping(source = "userUsername", target = "user", qualifiedByName = "mapUserByUsername")
    public abstract QueueEntry toEntity(QueueEntryDto queueEntryDto);

    @Mapping(source = "user.username", target = "userUsername")
    public abstract QueueEntryDto toDto(QueueEntry queueEntry);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userUsername", target = "user.username")
    public abstract QueueEntry partialUpdate(QueueEntryDto queueEntryDto, @MappingTarget QueueEntry queueEntry);

    @SuppressWarnings("unused")
    @Named("mapUserByUsername")
    protected User mapUserByUsername(String username) {
        return userService.find(username);
    }
}