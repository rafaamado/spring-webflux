package com.app.service;

import org.springframework.stereotype.Service;

import com.app.dto.PurchaseOrderResponseDto;
import com.app.repository.PurchaseOrderRepository;
import com.app.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
	
	private final PurchaseOrderRepository orderRepository;
	
	public Flux<PurchaseOrderResponseDto> getProductByUserId(long userId) {
		return Flux.fromStream(() ->  this.orderRepository.findByUserId(userId).stream()) //blocking
				.map(EntityDtoUtil::getPurchaseOrderResponseDto)
				.subscribeOn(Schedulers.boundedElastic());
	}

}
