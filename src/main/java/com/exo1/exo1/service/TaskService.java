package com.exo1.exo1.service;

import com.exo1.exo1.dto.TaskDto;
import com.exo1.exo1.entity.Task;
import com.exo1.exo1.mapper.TaskMapper;
import com.exo1.exo1.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final JdbcTemplate jdbcTemplate;
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;

    public void refreshMaterializedView() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW projet_task_count_view");
    }

    public List<TaskDto> findAll(int page, int pageSize) {
        return taskMapper.toDtos(taskRepository.findAllTasks(PageRequest.of(page, pageSize)).getContent());
    }

    public TaskDto findById(long id) {
        return taskMapper.toDto(taskRepository.findTaskById(id).orElse(null));
    }

    public TaskDto save(TaskDto taskDto) {
        TaskDto taskDto1 = taskMapper.toDto(taskRepository.save(taskMapper.toEntity(taskDto)));
        refreshMaterializedView();
        return taskDto1;
    }

    public TaskDto update(Long id, TaskDto taskDto) {
        Task existingTask = taskRepository.findTaskById(id)
                .orElseThrow(() -> new NotFoundException("Task not found with id " + id));
        taskDto.setId(existingTask.getId());
        TaskDto taskDto1 = taskMapper.toDto(taskRepository.save(taskMapper.toEntity(taskDto)));
        refreshMaterializedView();
        return taskDto1;
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
        refreshMaterializedView();
    }


}
