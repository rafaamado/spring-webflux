package com.app.service;

import com.app.dto.CardDto;
import com.app.entity.Card;
import com.app.entity.Deck;
import com.app.exception.ForbiddenException;
import com.app.exception.NotFoundException;
import com.app.mapper.CardMapper;
import com.app.repository.CardRepository;
import com.app.repository.DeckRepository;
import com.app.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
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
                .zipWith(ReactiveSecurityContextHolder.getContext(), this::checkPermission)
                .switchIfEmpty(Mono.error(new NotFoundException("Deck with id " + deckId + " not exists")))
                .flatMap(d -> cardDto)
                .map(card -> cardMapper.toEntity(card, deckId))
                .flatMap(cardRepository::save);
    }

    private Deck checkPermission(Deck deck, SecurityContext securityContext){
        Long currentUserId = AuthUtil.getCurrentUserId(securityContext.getAuthentication());
        if (!deck.getUserId().equals(currentUserId))
            throw new ForbiddenException("Not allowed");
        return deck;
    }

}
