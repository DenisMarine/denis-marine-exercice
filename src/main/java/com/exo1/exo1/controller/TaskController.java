package com.exo1.exo1.controller;

import com.exo1.exo1.dto.TaskDto;
import com.exo1.exo1.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name= "page-size", defaultValue = "10") int pageSize)
    {
        return ResponseEntity.ok(taskService.findAll(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TaskDto> save(@RequestBody TaskDto etudiantDto) {
        return ResponseEntity.ok(taskService.save(etudiantDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> update(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.update(id, taskDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
