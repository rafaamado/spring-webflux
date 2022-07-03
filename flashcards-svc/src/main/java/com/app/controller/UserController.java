package com.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserDto;
import com.app.entity.User;
import com.app.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public Flux<User> listUsers(){
		return userService.listAll();
	}
	
	@PostMapping("/registration")
	public Mono<User> createUser(@RequestBody UserDto user){
		return userService.register(user);
	}
	

}
