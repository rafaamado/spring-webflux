package com.app.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ProductDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductStreamController {
	
	private final Flux<ProductDto> flux;
	
	@GetMapping(value = "/stream/{maxPrice}", produces =  MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductDto> getProductUpdates(@PathVariable int maxPrice){
		return this.flux
					.filter(dto -> dto.getPrice() <= maxPrice);
	}

}
