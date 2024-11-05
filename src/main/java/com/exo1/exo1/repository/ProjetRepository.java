package com.exo1.exo1.repository;

import com.exo1.exo1.entity.Projet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProjetRepository extends JpaRepository<Projet, Long> {
    @Query("SELECT p FROM Projet p LEFT JOIN FETCH p.tasks WHERE p.id = :projetId")
    Optional<Projet> findProjetByIdWithTasks(Long projetId);

    @Query("SELECT p FROM Projet p LEFT JOIN FETCH p.tasks")
    Page<Projet> findAllWithTasks(Pageable pageable);
}
