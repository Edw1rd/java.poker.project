import java.util.ArrayList;
import java.util.List;

class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    @Override
    public String toString() {
    	return rank + " of " + suit;
    }
}

class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void addToHand(Card card) {
        hand.add(card);
    }
}

public class Table {
    private List<Card> deck;
    private List<Player> players;
    private List<Card> tableCards;

    public Table() {
    	// Initialize the table (deck, players, community cards)
        this.deck = new ArrayList<>();
        this.players = new ArrayList<>();
        this.tableCards = new ArrayList<>();
    }

    public void startGame() {
    	// Game start logic
    }
    
    public void dealCards() {
    	// Logic of dealing cards to players
    }

    public void flop() {
	// Logic of dealing cards to the table (flop)   
	// The flop is the first three community cards dealt simultaneously after the first round of betting.
	// After the flop, players have two hole cards in their hands and three open community cards on the table.
    	
    	
    }

    public void turn() {
    // Logic of dealing cards to the table (turn)
	// The turn is the fourth community card dealt after the second round of betting after the flop.
	// After the turn, players have two hole cards in their hands, and there are already four open community cards on the table.
    
    }

    public void river() {
    // Logic of dealing cards to the table (river)
	// The river is the fifth and final community card dealt after the third and final round of betting after the turn.
	// After the river, players have two hole cards in their hands, and there are five open community cards on the table.
    
    }

    public void determineWinner() {
    	// Logic for determining the winner
    }

}