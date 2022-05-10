package com.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
	public Mono<ResponseEntity<PurchaseOrderResponseDto>> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono) {
		return this.orderFulfillmentService.processOrder(requestDtoMono)
							.map(ResponseEntity::ok)
							.onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
							.onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
	}
	
	@GetMapping("/users/{userId}")
	public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable long userId){
		return this.queryService.getProductByUserId(userId);
	}
	

}
