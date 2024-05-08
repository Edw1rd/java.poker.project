package com.poker.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;  // Список для хранения карт

    public Deck() {
        reset();  // Создаем начальную колоду
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        } else {
            throw new IllegalStateException("No more cards in the deck");
        }
    }

    public int size() {
        return cards.size();  // Возвращает количество оставшихся карт
    }

    public void reset() {
        // Пересоздание колоды при вызове метода
        cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();  // Перемешиваем новую колоду
    }
}