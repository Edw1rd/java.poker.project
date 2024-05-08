package com.poker.rules;

import com.poker.cards.Card;
import java.util.*;

public class PokerHandEvaluator {
    // Метод для определения комбинации
    public static String getHandType(List<Card> cards) {
        if (isRoyalFlush(cards)) {
            return "Royal Flush";
        } else if (isStraightFlush(cards)) {
            return "Straight Flush";
        } else if (isFourOfAKind(cards)) {
            return "Four of a Kind";
        } else if (isFullHouse(cards)) {
            return "Full House";
        } else if (isFlush(cards)) {
            return "Flush";
        } else if (isStraight(cards)) {
            return "Straight";
        } else if (isThreeOfAKind(cards)) {
            return "Three of a Kind";
        } else if (isTwoPair(cards)) {
            return "Two Pair";
        } else if (isOnePair(cards)) {
            return "One Pair";
        } else {
            return "High Card";
        }
    }

    // Исправление ошибки: правильное определение метода
    private static boolean isFullHouse(List<Card> cards) {
        return isThreeOfAKind(cards) && isOnePair(cards);
    }

    // Методы для проверки комбинаций
    private static boolean isRoyalFlush(List<Card> cards) {
        return isStraightFlush(cards) && cards.stream().anyMatch(c -> c.getRank() == Card.Rank.ACE);
    }

    private static boolean isStraightFlush(List<Card> cards) {
        return isFlush(cards) && isStraight(cards);
    }

    private static boolean isFourOfAKind(List<Card> cards) {
        return hasSameRank(cards, 4);
    }

    private static boolean isFlush(List<Card> cards) {
        Card.Suit suit = cards.get(0).getSuit();
        return cards.stream().allMatch(c -> c.getSuit() == suit);
    }

    private static boolean isStraight(List<Card> cards) {
        List<Card.Rank> sortedRanks = getSortedRanks(cards);
        for (int i = 1; i < sortedRanks.size(); i++) {
            if (sortedRanks.get(i).ordinal() != sortedRanks.get(i - 1).ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isThreeOfAKind(List<Card> cards) {
        return hasSameRank(cards, 3);
    }

    private static boolean isTwoPair(List<Card> cards) {
        return getRankCount(cards).values().stream().filter(count -> count == 2).count() == 2;
    }

    private static boolean isOnePair(List<Card> cards) {
        return hasSameRank(cards, 2);
    }

    // Вспомогательные методы
    private static boolean hasSameRank(List<Card> cards, int count) {
        return getRankCount(cards).values().stream().anyMatch(c -> c == count);
    }

    private static Map<Card.Rank, Integer> getRankCount(List<Card> cards) {
        Map<Card.Rank, Integer> rankCount = new HashMap<>();
        for (Card card : cards) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
        }
        return rankCount;
    }

    private static List<Card.Rank> getSortedRanks(List<Card> cards) {
        List<Card.Rank> sortedRanks = new ArrayList<>();
        for (Card card : cards) {
            sortedRanks.add(card.getRank());
        }
        Collections.sort(sortedRanks);
        return sortedRanks;
    }

    // Метод для сравнения рук
    public static int compareHands(List<Card> hand1, List<Card> hand2) {
        // Получаем типы комбинаций
        String type1 = getHandType(hand1);
        String type2 = getHandType(hand2);

        // Сравниваем по силе комбинаций
        List<String> handTypes = Arrays.asList(
            "High Card",
            "One Pair",
            "Two Pair",
            "Three of a Kind",
            "Straight",
            "Flush",
            "Full House",
            "Four of a Kind",
            "Straight Flush",
            "Royal Flush"
        );

        int index1 = handTypes.indexOf(type1);
        int index2 = handTypes.indexOf(type2);

        return Integer.compare(index1, index2);  // Возвращаем результат сравнения
    }
}