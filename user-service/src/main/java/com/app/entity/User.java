package com.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("users")
public class User {
	
	@Id
	private Long id;
	private String name;
	private Integer balance;
}
