package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.TaskDto;
import com.exo1.exo1.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
    Task toEntity(TaskDto taskDto);
    List<TaskDto> toDtos(List<Task> tasks);
    List<Task> toEntities(List<TaskDto> taskDtos);
}
