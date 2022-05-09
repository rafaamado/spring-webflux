package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PurchaseOrderRequestDto;
import com.app.dto.PurchaseOrderResponseDto;
import com.app.service.OrderFulfillmentService;
import com.app.service.OrderQueryService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class PurchaseController {
	
	private final OrderFulfillmentService orderFulfillmentService;
	private final OrderQueryService queryService;
	
	@PostMapping
	public Mono<PurchaseOrderResponseDto> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono) {
		return this.orderFulfillmentService.processOrder(requestDtoMono);
	}
	
	@GetMapping("/users/{userId}")
	public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable long userId){
		return this.queryService.getProductByUserId(userId);
	}
	

}
