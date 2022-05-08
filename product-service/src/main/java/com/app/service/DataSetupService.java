package com.app.service;

import org.springframework.boot.CommandLineRunner;

import com.app.dto.ProductDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

	private final ProductService service;
	
	@Override
	public void run(String... args) throws Exception {
		ProductDto p1 = new ProductDto("4k-tv", 1000);
		ProductDto p2 = new ProductDto("camera", 750);
		ProductDto p3 = new ProductDto("phone", 800);
		ProductDto p4 = new ProductDto("headphone", 100);
		
		Flux.just(p1, p2, p3, p4)
			.flatMap(p -> service.insertProduct(Mono.just(p)))
			.subscribe(System.out::println);
	}

}
