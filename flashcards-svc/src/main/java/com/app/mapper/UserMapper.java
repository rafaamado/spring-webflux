package com.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.dto.UserDto;
import com.app.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
	User toEntity(UserDto userDto);
	
	UserDto toDto(User user);

}
