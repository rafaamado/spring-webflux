package com.app.service;

import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.app.dto.ProductDto;
import com.app.repository.ProductRepository;
import com.app.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository repository;
	
	public Flux<ProductDto> getAll(){
		return this.repository.findAll()
			.map(EntityDtoUtil::toDto);
	}
	
	public Flux<ProductDto> getProductByPriceRange(int min, int max){
		return this.repository.findByPriceBetween(Range.closed(min, max))
			.map(EntityDtoUtil::toDto);
	}
	
	public Mono<ProductDto> getProductById(String id){
		return this.repository.findById(id)
				.map(EntityDtoUtil::toDto);
	}
	
	public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono){
		return productDtoMono
			.map(EntityDtoUtil::toEntity)
			.flatMap(repository::insert)
			.map(EntityDtoUtil::toDto);
	}
	
	public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono){
		return this.repository.findById(id)
				.flatMap(p -> productDtoMono
								.map(EntityDtoUtil::toEntity)
								.doOnNext(e -> e.setId(id)))
				.flatMap(repository::save)
				.map(EntityDtoUtil::toDto);
	}
	
	public Mono<Void> deleteProduct(String id) {
		return this.repository.deleteById(id);
	}
}
