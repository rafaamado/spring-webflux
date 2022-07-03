package com.app.repository;

import com.app.entity.Deck;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DeckRepository extends ReactiveCrudRepository<Deck, Long> {

    Flux<Deck> findAllBy(Pageable pageable);
    Flux<Deck> findDecksByUserId(Long userId);
}
