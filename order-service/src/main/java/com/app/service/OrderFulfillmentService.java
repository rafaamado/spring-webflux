package com.app.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.app.client.ProductClient;
import com.app.client.UserClient;
import com.app.dto.PurchaseOrderRequestDto;
import com.app.dto.PurchaseOrderResponseDto;
import com.app.dto.RequestContext;
import com.app.repository.PurchaseOrderRepository;
import com.app.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {
	
	private final PurchaseOrderRepository orderRepository;
	private final ProductClient productClient;
	private final UserClient userClient;
	
	
	public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDto){
		return requestDto.map(RequestContext::new)
			.flatMap(this::productRequestResponse)
			.doOnNext(EntityDtoUtil::setTransactionRequestDto)
			.flatMap(this::userRequestResponse)
			.map(EntityDtoUtil::getPurchaseOrder)
			.map(this.orderRepository::save) //blocking 
			.map(EntityDtoUtil::getPurchaseOrderResponseDto)
			.subscribeOn(Schedulers.boundedElastic()); // use when there is a blocking step
	}
	
	
	/* Optional way with publishOn insted of SubscribeOn
	public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDto){
		return requestDto.map(RequestContext::new)
			.flatMap(this::productRequestResponse)
			.doOnNext(EntityDtoUtil::setTransactionRequestDto)
			.flatMap(this::userRequestResponse)
			.map(EntityDtoUtil::getPurchaseOrder)
			.publishOn(Schedulers.boundedElastic())
			.map(this.orderRepository::save) //blocking 
			.map(EntityDtoUtil::getPurchaseOrderResponseDto);
			 
	} */
	
	private Mono<RequestContext> productRequestResponse(RequestContext rc) {
		return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
			.doOnNext(rc::setProductDto)
			//.retry(5)
			.retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
			.thenReturn(rc);
	}
	
	private Mono<RequestContext> userRequestResponse(RequestContext rc) {
		return this.userClient.authorizeTransaction(rc.getTransactionRequestDto())
				.doOnNext(rc::setTransactionResponseDto)
				.thenReturn(rc);
	}
	
	

}
