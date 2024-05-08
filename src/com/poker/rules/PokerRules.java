package com.poker.rules;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import com.poker.cards.Card;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PokerRules {
    // Метод для определения силы руки
    public String evaluateHand(List<Card> cards) {
        // Пример простой логики для определения комбинации
        if (isFlush(cards)) {
            return "Flush";
        } else if (isStraight(cards)) {
            return "Straight";
        } else if (isPair(cards)) {
            return "Pair";
        }
        return "High Card";  // По умолчанию, если нет других комбинаций
    }

    private boolean isFlush(List<Card> cards) {
        // Проверка, все ли карты одной масти
        Card.Suit suit = cards.get(0).getSuit();
        for (Card card : cards) {
            if (card.getSuit() != suit) {
                return false;
            }
        }
        return true;
    }

    private boolean isStraight(List<Card> cards) {
        // Пример логики проверки на стрит
        // Сначала отсортируем карты по рангу
        List<Card> sortedCards = new ArrayList<>(cards);
        Collections.sort(sortedCards, Comparator.comparingInt(c -> c.getRank().ordinal()));
        // Проверка на последовательность
        for (int i = 1; i < sortedCards.size(); i++) {
            if (sortedCards.get(i).getRank().ordinal() != sortedCards.get(i - 1).getRank().ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isPair(List<Card> cards) {
        // Проверка на наличие пары
        Map<Card.Rank, Integer> rankCount = new HashMap<>();
        for (Card card : cards) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
        }
        return rankCount.values().stream().anyMatch(count -> count >= 2);
    }
}