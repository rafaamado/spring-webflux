package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.TransactionRequestDto;
import com.app.dto.TransactionResponseDto;
import com.app.entity.UserTransaction;
import com.app.service.TransactionService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users/transactions")
@RequiredArgsConstructor
public class UserTransactionController {
	
	private final TransactionService transactionService;
	
	@PostMapping
	public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono){
		return requestDtoMono.flatMap(this.transactionService::createTransaction);
	}
	
	@GetMapping
	public Flux<UserTransaction> getByUserId(@RequestParam("userId") Long userId) {
		return this.transactionService.getByUserId(userId);
	}

}
