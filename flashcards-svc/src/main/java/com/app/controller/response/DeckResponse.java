package com.app.controller.response;

import com.app.entity.Card;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DeckResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Card> cards;
}
