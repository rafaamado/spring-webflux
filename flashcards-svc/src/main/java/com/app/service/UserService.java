package com.app.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.UserDto;
import com.app.entity.User;
import com.app.exception.NotFoundException;
import com.app.exception.UserAlreadyExists;
import com.app.mapper.UserMapper;
import com.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserMapper userMapper;

	public Flux<User> listAll(){
		return userRepository.findAll();
	}
	
	public Mono<User> findByEmail(String email) {
		return userRepository.findUserByEmail(email)
				.switchIfEmpty(Mono.error(new NotFoundException("No user with email " + email)));
	}
	
	public Mono<User> register(UserDto userDto){
		return userRepository.findUserByEmail(userDto.getEmail())
				.doOnNext((user) -> {throw new UserAlreadyExists("User with email " + userDto.getEmail() + " already exits"); })
				.switchIfEmpty(createUser(userDto));
		
	}

	
	private Mono<User> createUser(UserDto userDto){
		String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
		
		User user = userMapper.toEntity(userDto);
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}
}
