package com.exampl.tasklist.web.mappers;

import com.exampl.tasklist.domain.user.User;
import com.exampl.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user); //in Dto

    User toEntity(UserDto dto); //with Entity

}
