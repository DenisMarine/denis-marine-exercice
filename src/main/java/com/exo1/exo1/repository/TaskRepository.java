package com.exo1.exo1.repository;

import com.exo1.exo1.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t.id, t.title, t.status FROM Task t WHERE t.id=:taskId")
    Optional<Task> findTaskById(Long taskId);

    @Query("SELECT t.id, t.title, t.status FROM Task t")
    Page<Task> findAllTasks(Pageable pageable);
}
