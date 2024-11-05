package com.exo1.exo1.service;

import com.exo1.exo1.dto.ProjetDto;
import com.exo1.exo1.entity.Projet;
import com.exo1.exo1.mapper.ProjetMapper;
import com.exo1.exo1.repository.ProjetRepository;
import com.exo1.exo1.repository.ProjetTaskCountViewRepository;
import com.exo1.exo1.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjetService {
    private JdbcTemplate jdbcTemplate;

    private final ProjetTaskCountViewRepository projetTaskCountViewRepository;
    private ProjetRepository projetRepository;
    private ProjetMapper projetMapper;
    private TaskRepository taskRepository;

    public void refreshMaterializedView() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW projet_task_count_view");
    }

    @CacheEvict(value = "projetsList", allEntries = true)
    @Cacheable(value = "projetsList")
    public List<ProjetDto> findAll(int page, int pageSize) {
        return projetMapper.toDtos(projetRepository.findAllWithTasks(PageRequest.of(page, pageSize)).getContent());
    }

    @CachePut(value = "projet", key="#id")
    public ProjetDto findById(long id) {
        return projetMapper.toDto(projetRepository.findProjetByIdWithTasks(id).orElse(null));
    }

    public ProjetDto save(ProjetDto projetDto) {
        Projet projet = projetMapper.toEntity(projetDto);
        projet.getTasks().forEach(t -> t.setProjet(projet));
        ProjetDto projetDto1 = projetMapper.toDto(projetRepository.save(projet));
        refreshMaterializedView();
        return projetDto1;
    }

    @CacheEvict(value = "projet", key = "#id")
    @CachePut(value = "projet", key = "#id")
    public ProjetDto update(Long id, ProjetDto projetDto) {
        Projet existingProjet = projetRepository.findProjetByIdWithTasks(id)
                .orElseThrow(() -> new NotFoundException("Projet not found with id " + id));
        projetDto.setId(existingProjet.getId());
        Projet projetUpdated = projetMapper.toEntity(projetDto);
        projetUpdated.getTasks().forEach(t -> {
            if(taskRepository.existsById(t.getId())) {
                t.setProjet(projetUpdated);
            }
        });
        ProjetDto projetDto1 = projetMapper.toDto(projetRepository.save(projetUpdated));
        refreshMaterializedView();
        return projetDto1;
    }

    @CacheEvict(value = "projet", key = "#id")
    public void delete(Long id) {
        projetRepository.deleteById(id);
        refreshMaterializedView();
    }
}
