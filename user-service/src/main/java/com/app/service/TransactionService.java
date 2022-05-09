package com.app.service;

import org.springframework.stereotype.Service;

import com.app.dto.TransactionRequestDto;
import com.app.dto.TransactionResponseDto;
import com.app.dto.TransactionStatus;
import com.app.entity.UserTransaction;
import com.app.repository.UserRepository;
import com.app.repository.UserTransactionRepository;
import com.app.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final UserRepository userRepository;
	private final UserTransactionRepository transactionRepository;
	
	public Mono<TransactionResponseDto> createTransaction(TransactionRequestDto requestDto){
		return this.userRepository.updateUserBalance(requestDto.getAmount(), requestDto.getUserId())
								.filter(Boolean::booleanValue)
								.map(b -> EntityDtoUtil.toEntity(requestDto))
								.flatMap(this.transactionRepository::save)
								.map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
								.defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
	}
	
	public Flux<UserTransaction> getByUserId(Long userId) {
		return this.transactionRepository.findByUserId(userId);
	}
	
}
