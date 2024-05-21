import java.util.ArrayList;

import com.poker.cards.Card;

public class Bot {
    private String name;
    private int chips;
    private ArrayList<Card> hand;

    public Bot(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.hand = new ArrayList<>();
    }

    public void receiveCard(Card card) {
        hand.add(card);
    }

    public void makeDecision(Table table) {
        // Здесь реализуется логика принятия решения для бота
        // Например, можно использовать простые стратегии, такие как всегда вызывать или всегда ставить в зависимости от карт в руке и общих картах на столе
        // Реализуйте логику в зависимости от своих требований
    }

    // Другие методы, если необходимо
}