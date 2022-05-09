package com.app.util;

import org.springframework.beans.BeanUtils;

import com.app.dto.OrderStatus;
import com.app.dto.PurchaseOrderResponseDto;
import com.app.dto.RequestContext;
import com.app.dto.TransactionRequestDto;
import com.app.dto.TransactionStatus;
import com.app.entity.PurchaseOrder;

public class EntityDtoUtil {
	
	public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
		PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
		BeanUtils.copyProperties(purchaseOrder, dto);
		dto.setOrderId(purchaseOrder.getId());
		return dto;
	}

	public static void setTransactionRequestDto(RequestContext requestContext) {
		TransactionRequestDto dto = new TransactionRequestDto();
		dto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
		dto.setAmount(requestContext.getProductDto().getPrice());
		requestContext.setTransactionRequestDto(dto);
	}
	
	
	public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
		purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
		purchaseOrder.setAmount(requestContext.getProductDto().getPrice());
		
		
		TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
		OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
		purchaseOrder.setStatus(orderStatus);
		return purchaseOrder; 
	}
}
