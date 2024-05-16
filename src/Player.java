import com.poker.cards.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private List<Card> hand;  // Рука игрока
    private int chips;  // Баланс фишек игрока

    public Player(int initialChips) {
        hand = new ArrayList<>();  // Создание нового списка для хранения карт в руке игрока
        chips = initialChips;  // Инициализация баланса фишек игрока
    }

    // Метод для добавления карты в руку игрока
    public void addCard(Card card) {
        hand.add(card);
    }

    // Метод для очистки руки игрока (используется в начале новой игры)
    public void clearHand() {
        hand.clear();
    }

    // Метод для получения карт в руке игрока
    public List<Card> getHand() {
        return hand;
    }

    // Метод для установки ставки
    public int placeBet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("У игрока " + chips + " фишек. Введите размер вашей ставки:");
        int betAmount = scanner.nextInt();
        if (betAmount > chips) {
            System.out.println("У вас недостаточно фишек для такой ставки. Пожалуйста, сделайте меньшую ставку.");
            return placeBet();  // Рекурсивный вызов метода, если ставка больше, чем у игрока есть фишек
        } else {
            chips -= betAmount;  // Уменьшение баланса фишек на сумму ставки
            System.out.println("Вы поставили " + betAmount + " фишек.");
            return betAmount;
        }
    }

    // Метод для получения выигрыша
    public void receiveWinnings(int winnings) {
        chips += winnings;  // Увеличение баланса фишек на сумму выигрыша
        System.out.println("Вы получили " + winnings + " фишек выигрыша.");
    }

    // Метод для получения текущего баланса фишек игрока
    public int getChips() {
        return chips;
    }
    public Player() {
        this(0); // Вызываем конструктор с параметрами, передавая 0 в качестве начального количества фишек
    }

	public void displayCards() {
		// TODO Auto-generated method stub
		
	}
}