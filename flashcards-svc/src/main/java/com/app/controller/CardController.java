package com.app.controller;

import com.app.dto.CardDto;
import com.app.entity.Card;
import com.app.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/decks/{deckId}/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public Mono<Card> createCard(@PathVariable Long deckId, @RequestBody Mono<CardDto> card){
        return cardService.createCard(deckId, card);
    }

}
