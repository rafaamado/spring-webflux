package com.app.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.controller.request.AuthRequest;
import com.app.controller.response.AuthResponse;
import com.app.exception.InvalidCredentialsException;
import com.app.repository.UserRepository;
import com.app.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JWTUtil jwtUtil;

	public Mono<AuthResponse> login(AuthRequest ar) {
		return this.userRepository.findUserByEmail(ar.getEmail())
				.filter(user -> bCryptPasswordEncoder.matches(ar.getPassword(), user.getPassword()))
				.map(user -> new AuthResponse(jwtUtil.generateToken(user)))
				.switchIfEmpty(Mono.error(new InvalidCredentialsException("Invalid credentials")));				

	}
}
