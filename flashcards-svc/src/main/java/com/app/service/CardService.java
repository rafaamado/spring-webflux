package com.app.service;

import com.app.dto.CardDto;
import com.app.entity.Card;
import com.app.exception.NotFoundException;
import com.app.mapper.CardMapper;
import com.app.repository.CardRepository;
import com.app.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CardService {

    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public Mono<Card> createCard(Long deckId, Mono<CardDto> cardDto){
        return deckRepository.findById(deckId)
                .switchIfEmpty(Mono.error(new NotFoundException("Deck with id " + deckId + " not exists")))
                .flatMap(d -> cardDto)
                .map(card -> cardMapper.toEntity(card, deckId))
                .flatMap(cardRepository::save);
    }

}
