package com.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.client.ProductClient;
import com.app.client.UserClient;
import com.app.dto.ProductDto;
import com.app.dto.PurchaseOrderRequestDto;
import com.app.dto.PurchaseOrderResponseDto;
import com.app.dto.UserDto;
import com.app.service.OrderFulfillmentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {
	
	@Autowired
	private UserClient userClient;
	@Autowired
	private ProductClient productClient;
	@Autowired
	private OrderFulfillmentService fulfillmentService;

	@Test
	void contextLoads() {
		// zip -> emits one user and one product
		Flux<PurchaseOrderResponseDto> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
			.map(t -> buildDto(t.getT1(),  t.getT2()))
			.flatMap(dto -> this.fulfillmentService.processOrder(Mono.just(dto)))
			.doOnNext(System.out::println);
		
		StepVerifier.create(dtoFlux)
			.expectNextCount(2)
			.verifyComplete();
	}
	
	private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto) {
		PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
		dto.setUserId(userDto.getId());
		dto.setProductId(productDto.getId());
		return dto;
	}

}
