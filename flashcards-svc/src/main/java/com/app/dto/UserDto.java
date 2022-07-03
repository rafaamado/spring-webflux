package com.app.dto;

import com.app.entity.UserRole;

import lombok.Data;

@Data
public class UserDto {
	private Long id;
	private String email;
    private String password;
    private UserRole role;
}
