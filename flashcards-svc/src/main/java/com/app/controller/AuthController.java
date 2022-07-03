package com.app.controller;

import com.app.controller.request.AuthRequest;
import com.app.controller.response.AuthResponse;
import com.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	
	private final AuthService authService;

	@PostMapping("/login")
	public Mono<AuthResponse> login(@RequestBody AuthRequest ar) {
		return authService.login(ar);
	}

	// If you use hasRole('ADMIN'), in your ADMIN Enum must be ROLE_ADMIN instead of ADMIN.
	// If you use hasAuthority('ADMIN'), your ADMIN Enum must be ADMIN.

	@GetMapping()
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	public Mono<Authentication> test(Authentication authentication) {
		log.info("Authentication a: {}", authentication);
		return Mono.just(authentication);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public Mono<Authentication> testPut() {
		return ReactiveSecurityContextHolder
				.getContext()
				.map(context -> context.getAuthentication());
	}

}
