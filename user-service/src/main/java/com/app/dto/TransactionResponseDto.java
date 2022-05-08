package com.app.dto;

import lombok.Data;

@Data
public class TransactionResponseDto {

	private Long userId;
	private Integer amount;
	private TransactionStatus status;
}
