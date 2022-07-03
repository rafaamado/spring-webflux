package com.app.controller;

import com.app.controller.response.DeckResponse;
import com.app.dto.DeckDto;
import com.app.entity.Deck;
import com.app.service.DeckService;
import com.app.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/decks")
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;

    @GetMapping
    public Flux<Deck> findAllDecks(Authentication authentication) {
        return deckService.findDecksByUserId(AuthUtil.getCurrentUserId(authentication));
    }

    @GetMapping("/pageable")
    public Mono<Page<Deck>> getAllDecks(@RequestParam int page, @RequestParam int size) {
        return deckService.findAllDecks(PageRequest.of(page, size));
    }


    @GetMapping("/{deckId}")
    public Mono<DeckResponse> findDeckById(@PathVariable Long deckId, Authentication authentication) {
        return deckService.findDeckById(deckId, AuthUtil.getCurrentUserId(authentication));
    }

    @PostMapping
    public Mono<Deck> saveDeck(@RequestBody DeckDto deckDto, Authentication authentication) {
        deckDto.setUserId(AuthUtil.getCurrentUserId(authentication));
        return deckService.save(deckDto);
    }

    @PutMapping("/{id}")
    public Mono<Deck> updateDeck(@PathVariable Long id, @RequestBody Mono<Deck> deck) {
        return deckService.update(id, deck);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteDeck(@PathVariable Long id) {
        return deckService.delete(id);
    }

}
