package com.app.mapper;

import com.app.controller.response.DeckResponse;
import com.app.dto.DeckDto;
import com.app.entity.Deck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeckMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Deck toEntity(DeckDto deckDto);

    //DeckResponse toResponseDto(Deck deck, Flux<Card> cards);
    @Mapping(target = "cards", ignore = true)
    DeckResponse toResponseDto(Deck deck);
}
