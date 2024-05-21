import com.poker.cards.Deck;
import com.poker.cards.Card;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Table {
    private JFrame frame;
    private JPanel tablePanel;
    private ImageIcon tableBackground;
    private Deck deck;
    private List<Player> players;
    private List<Card> communityCards;

    public Table() {
        frame = new JFrame("Poker Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        tablePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (tableBackground != null) {
                    int x = (getWidth() - tableBackground.getIconWidth()) / 2;
                    int y = (getHeight() - tableBackground.getIconHeight()) / 2;
                    tableBackground.paintIcon(this, g, x, y);
                }
            }
        };

        Image originalImage = new ImageIcon("poker_table.jpg").getImage();
        Image scaledImage = originalImage.getScaledInstance(1400, 900, Image.SCALE_SMOOTH);
        tableBackground = new ImageIcon(scaledImage);

        frame.add(tablePanel, BorderLayout.CENTER);

        deck = new Deck();
        players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());
        communityCards = new ArrayList<>();
    }

    public int getNumPlayers() {
        return players.size();
    }

    public Player getPlayer(int index) {
        if (index >= 0 && index < players.size()) {
            return players.get(index);
        } else {
            return null;
        }
    }

    public void startGame() {
        deck.shuffle();
        dealCards();
    }

    private void dealCards() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                Card card = deck.drawCard();
                player.addCard(card);
            }
        }
    }

    public void addCommunityCard(Card card) {
        communityCards.add(card);
    }

    public void displayPlayerCards() {
        for (Player player : players) {
            System.out.println("Player " + players.indexOf(player) + " cards:");
            player.displayCards();
        }
    }

    public void displayCommunityCards() {
        System.out.println("Community cards:");
        for (Card card : communityCards) {
            System.out.println(card);
        }
    }

    public JPanel getTablePanel() {
        return tablePanel;
    }

    public static void main(String[] args) {
        Table table = new Table();
        table.startGame();
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }
		
}