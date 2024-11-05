package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.ProjetDto;
import com.exo1.exo1.entity.Projet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface ProjetMapper {
    ProjetDto toDto(Projet projet);
    Projet toEntity(ProjetDto projetDto);
    List<ProjetDto> toDtos(List<Projet> projets);
    List<Projet> toEntities(List<ProjetDto> projetDtos);
}
