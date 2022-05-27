package com.app.repository;

import com.app.entity.Card;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CardRepository extends ReactiveCrudRepository<Card, Long> {

    Flux<Card> findCardsByDeckId(Long deckId);
}
