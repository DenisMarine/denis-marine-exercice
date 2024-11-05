package com.exo1.exo1.mapper;


import com.exo1.exo1.dto.UserDto;
import com.exo1.exo1.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProjetMapper.class})
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    List<UserDto> toDtos(List<User> users);
    List<User> toEntities(List<UserDto> userDtos);
}
