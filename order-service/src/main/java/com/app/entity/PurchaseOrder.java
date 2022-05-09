package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.app.dto.OrderStatus;

import lombok.Data;

@Entity
@Data
public class PurchaseOrder {
	
	@Id
	@GeneratedValue 
	private Long id;
	private String productId;
	private Long userId;
	private Integer amount;
	private OrderStatus status;
}
