package com.app.service;

import org.springframework.stereotype.Service;

import com.app.dto.UserDto;
import com.app.repository.UserRepository;
import com.app.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	public Flux<UserDto> all(){
		return this.userRepository.findAll()
						.map(EntityDtoUtil::toDto);
	}
	
	public Mono<UserDto> getUserById(Long userId){
		return this.userRepository.findById(userId)
				.map(EntityDtoUtil::toDto);
	}
	
	public Mono<UserDto> createUser(Mono<UserDto> userDtoMono){
		return userDtoMono
				.map(EntityDtoUtil::toEntity)
				.flatMap(this.userRepository::save)
				.map(EntityDtoUtil::toDto);
	}
	
	public Mono<UserDto> updateUser(Long id, Mono<UserDto> userDtoMono){
		return this.userRepository.findById(id)
					.flatMap(u -> userDtoMono
									.map(EntityDtoUtil::toEntity)
									.doOnNext(e -> e.setId(id)))
					.flatMap(this.userRepository::save)
					.map(EntityDtoUtil::toDto);
	}
	
	public Mono<Void> deleteUser(Long id) {
		return this.userRepository.deleteById(id);
	}
}
