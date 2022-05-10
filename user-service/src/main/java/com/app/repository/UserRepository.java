package com.app.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.User;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

	@Modifying
	@Query(
			"update users " + 
			"set balance = balance - :amount " +
			"where id = :userId " + 
			"and balance >= :amount "
	)
	Mono<Boolean> updateUserBalance(@Param("userId") Long userId, @Param("amount") Integer amount);
	// For some reason the parameter order matters when @Param is not specified!
}