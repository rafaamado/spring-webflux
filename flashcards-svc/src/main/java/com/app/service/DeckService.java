package com.app.service;

import com.app.controller.response.DeckResponse;
import com.app.dto.DeckDto;
import com.app.entity.Deck;
import com.app.exception.NotFoundException;
import com.app.mapper.DeckMapper;
import com.app.repository.CardRepository;
import com.app.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;
    private final DeckMapper deckMapper;

    public Flux<Deck> findAll(){
        return deckRepository.findAll();
    }

    public Mono<Page<Deck>> findAllDecks(PageRequest pageRequest){
        return deckRepository.findAllBy(pageRequest)
                .collectList()
                .zipWith(this.deckRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }

    public Flux<Deck> findDecksByUserId(Long userId){
        return deckRepository.findDecksByUserId(userId);
    }

    public Mono<DeckResponse> findDeckById(Long deckId, Long currentUserId){
        Mono<DeckResponse> monoDeck = deckRepository.findById(deckId)
                .filter(deck -> deck.getUserId().equals(currentUserId))
                .switchIfEmpty(Mono.error(new NotFoundException("Deck with id " + deckId + " not found")))
                .map(deckMapper::toResponseDto);

        return Mono.zip(monoDeck,
                cardRepository.findCardsByDeckId(deckId).collectList(),
                (deck, cards) -> {
                    deck.setCards(cards);
                    return deck;
                });
    }

    public Mono<Deck> save(DeckDto deckDto){
        return Mono.just(deckDto)
                .map(deckMapper::toEntity)
                .flatMap(deckRepository::save);
    }

    public Mono<Deck> update(Long id, Mono<Deck> deckMono){
        return deckRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Deck with id " + id + " not found")))
                .flatMap(d -> deckMono.map(deck -> {
                    d.setName(deck.getName());
                    d.setDescription(deck.getDescription());
                    return d;
                }))
                .flatMap(this.deckRepository::save);
    }

    public Mono<Void> delete(Long id){
        return deckRepository.deleteById(id);
    }
}
