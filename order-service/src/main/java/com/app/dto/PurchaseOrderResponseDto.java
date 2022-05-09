package com.app.dto;

import lombok.Data;

@Data
public class PurchaseOrderResponseDto {
	private Long orderId;
	private Long userId;
	private String productId;
	private Integer amount;
	private OrderStatus status;
}
