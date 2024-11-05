package com.exo1.exo1.repository;

import com.exo1.exo1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.task WHERE u.id = :id")
    Optional<User> findByIdWithTask(@Param("id") Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.projets WHERE u.id = :id")
    Optional<User> findByIdWithProjets(@Param("id") Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.projets")
    Page<User> findAllWithProjets(Pageable pageable);
}
