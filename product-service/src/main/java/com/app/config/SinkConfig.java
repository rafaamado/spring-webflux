package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.dto.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

	//Sink to push the items
	@Bean
	public Sinks.Many<ProductDto> sink() {
		return Sinks.many().replay().limit(1);
	}
	
	//Sink to observe items
	@Bean
	public Flux<ProductDto> productBroacast(Sinks.Many<ProductDto> sink){
		return sink.asFlux();
	}
}
