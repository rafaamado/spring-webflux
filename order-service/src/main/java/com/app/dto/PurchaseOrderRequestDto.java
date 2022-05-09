package com.app.dto;

import lombok.Data;

@Data
public class PurchaseOrderRequestDto {
	private Long userId;
	private String productId;
}
