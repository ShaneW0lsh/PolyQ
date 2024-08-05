package ru.kolomych.polyq.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.kolomych.polyq.dto.QueueDto;
import ru.kolomych.polyq.model.Queue;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {QueueEntryMapper.class})
public interface QueueMapper {

    @Mapping(source = "teacherMiddleName", target = "teacher.middleName")
    @Mapping(source = "teacherLastName", target = "teacher.lastName")
    @Mapping(source = "teacherFirstName", target = "teacher.firstName")
    Queue toEntity(QueueDto queueDto);

    @AfterMapping
    default void linkEntries(@MappingTarget Queue queue) {
        queue.getEntries().forEach(entry -> entry.setQueue(queue));
    }

    @InheritInverseConfiguration(name = "toEntity")
    QueueDto toDto(Queue queue);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "toEntity")
    Queue partialUpdate(QueueDto queueDto, @MappingTarget Queue queue);
}