package com.app.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class UserTransaction {
	
	@Id
	private Long id;
	private Long userId;
	private Integer amount;
	private LocalDateTime transactionDate;
}
