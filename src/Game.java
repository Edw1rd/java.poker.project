import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.poker.cards.Deck;
import com.poker.cards.Card;

public class Game {
    private JFrame frame; // Основное окно игры
    private JPanel currentPanel; // Текущая панель
    private Table table; // Экземпляр игрового стола
    private Deck deck; // Колода карт
    private ImageIcon backgroundStart; // Фон для начального экрана
    private ImageIcon backgroundRules; // Фон для экрана с правилами
    private ImageIcon backgroundCombinations; // Фон для экрана с комбинациями

    private int initialChips = 0; // Начальное количество фишек у игроков

    public Game() {
        frame = new JFrame("PokerGame"); // Создаем только один `JFrame`
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Окно по центру

        deck = new Deck(); // Создаем и перемешиваем колоду карт
        deck.shuffle();

        table = new Table(); // Инициализация игрового стола

        // Загрузка фона для начального экрана
        Image originalImageStart = new ImageIcon("poker_1.jpg").getImage();
        Image scaledImageStart = originalImageStart.getScaledInstance(1400, 900, Image.SCALE_SMOOTH); // Уменьшаем размер изображения
        backgroundStart = new ImageIcon(scaledImageStart);

        // Загрузка фона для экрана с правилами
        Image originalImageRules = new ImageIcon("rules_background.jpg").getImage();
        Image scaledImageRules = originalImageRules.getScaledInstance(1400, 900, Image.SCALE_SMOOTH); // Уменьшаем размер изображения
        backgroundRules = new ImageIcon(scaledImageRules);

        // Загрузка фона для экрана с комбинациями
        Image originalImageCombinations = new ImageIcon("combinations_background.jpg").getImage();
        Image scaledImageCombinations = originalImageCombinations.getScaledInstance(1400, 900, Image.SCALE_SMOOTH); // Уменьшаем размер изображения
        backgroundCombinations = new ImageIcon(scaledImageCombinations);

        showStartScreen(); // Показываем начальный экран

        frame.setVisible(true); // Окно должно быть видимым
    }

    private void showStartScreen() {
        JPanel startPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundStart != null) { // Проверяем наличие фона
                    int x = (getWidth() - backgroundStart.getIconWidth()) / 2;
                    int y = (getHeight() - backgroundStart.getIconHeight()) / 2;
                    backgroundStart.paintIcon(this, g, x, y); // Рисуем фон
                }
            }
        };

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3)); // Панель внизу с 3 столбцами

        // Кнопка Правила
        JButton rulesButton = new JButton("Rules");
        rulesButton.setPreferredSize(new Dimension(150, 50)); // Устанавливаем размер кнопки
        rulesButton.addActionListener(e -> showRulesScreen()); // Переход к экрану с правилами
        bottomPanel.add(rulesButton);

        // Кнопка Начать игру
        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(150, 50)); // Устанавливаем размер кнопки
        startButton.addActionListener(e -> askInitialChips()); // Начать игру
        bottomPanel.add(startButton);

        // Кнопка Комбинации
        JButton comboButton = new JButton("Combinations");
        comboButton.setPreferredSize(new Dimension(150, 50)); // Устанавливаем размер кнопки
        comboButton.addActionListener(e -> showCombinationsScreen()); // Переход к экрану с комбинациями
        bottomPanel.add(comboButton);

        startPanel.add(bottomPanel, BorderLayout.SOUTH);

        setPanel(startPanel); // Установка начальной панели

        frame.requestFocusInWindow(); // Убеждаемся, что окно в фокусе
    }

    private void askInitialChips() {
        String input = JOptionPane.showInputDialog(frame, "Enter the initial number of chips for each player:", "Initial number of chips", JOptionPane.PLAIN_MESSAGE);
        try {
            initialChips = Integer.parseInt(input);
            if (initialChips < 500) {
                JOptionPane.showMessageDialog(frame, "The starting number of chips must be a positive number greater than or equal to 500.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                startGame(); // We start the game after entering the number of chips
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showConfirmationDialog(String message, Runnable action) {
        int option = JOptionPane.showConfirmDialog(frame, message, "Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            action.run();
        }
    }

    private void dealCardsToPlayers() {
        for (int i = 0; i < table.getNumPlayers(); i++) {
            Player player = table.getPlayer(i);
            for (int j = 0; j < 2; j++) { // Раздача двух карт каждому игроку
                Card card = deck.dealCard(); // Взять карту из колоды
                player.addCard(card); // Добавить карту игроку
            }
        }
    }

    private void dealCommunityCards() {
        for (int i = 0; i < 5; i++) { // Раздача пяти карт общего поля
            Card card = deck.dealCard(); // Взять карту из колоды
            table.addCommunityCard(card); // Добавить карту в общие карты
        }
    }

    public void startGame() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            showConfirmationDialog("Are you sure you want to go back?", this::showStartScreen);
        });
        topPanel.add(backButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> showSettingsScreen());
        topPanel.add(settingsButton);

        frame.add(topPanel, BorderLayout.NORTH);

        setPanel(table.getTablePanel());

        if (table != null) {
            dealCardsToPlayers(); // Раздать карты игрокам
            dealCommunityCards(); // Раздать карты общего поля

            // Отобразить карты на экране
            table.displayPlayerCards();
            table.displayCommunityCards();

            table.startGame();
        }
    }

    private void showSettingsScreen() {
        // Здесь вы можете добавить код для отображения экрана настроек
    }

    private void showRulesScreen() {
        JPanel rulesPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundRules != null) { // Проверяем наличие фона
                    int x = (getWidth() - backgroundRules.getIconWidth()) / 2;
                    int y = (getHeight() - backgroundRules.getIconHeight()) / 2;
                    backgroundRules.paintIcon(this, g, x, y); // Рисуем фон
                }
            }
        };

        JTextArea rulesText = new JTextArea("1. First rule.\n"
                + "Players at the table receive 2 personal cards, and 5 community cards are laid out on the table in several stages. "
                + "The player's goal is to either defeat his opponents when revealing personal cards after 5 community cards have been laid out, or to have all other players fold their cards at any stage."
                + " When opening cards, the player who has the best combination of 5 cards out of 7 cards (2 personal and 5 public) wins."
                + " It does not matter how many personal cards are used to collect a combination."
                + "\n2. Depending on the situation (bet of previous players, position at the table), "
                + "the following actions are possible:\n"
                + "Concepts of positions at the poker table\n"
                + "Check – skipping a move without making a bet.\n"
                + "Bet – bet corresponding to the big blind.\n"
                + "Call – equalizing the bet of the previous player who made the bet.\n"
                + "Raise - raising the bet if there was a call or check before.\n"
                + "Reraise – raising the raise, i.e. response to a raise.\n"
                + "All-in is a bet on all the chips in the stack.\n"
                + "Fold – pass, discard cards."
                + "\n3. Game stages:\r\n"
                + "\r\n"
                + "Pre-flop\r\n"
                + "Preflop, each player receives 2 cards face down. Then, the first round of betting begins, starting with the player sitting to the left of the player who made the big blind (this is called Under the Gun - UTG), and ending with the player sitting to the left of the big blind.\n"
                + "\r\n"
                + "Flop\r\n"
                + "The dealer deals the first three community cards (the flop), after which the second round of betting begins with the player sitting to the left of the dealer.\n"
                + "\r\n"
                + "Turn\r\n"
                + "The dealer deals the fourth community card (the turn), followed by a third round of betting with the player sitting to the left of the dealer.\n"
                + "\r\n"
                + "River\r\n"
                + "The dealer deals the fifth community card (the river), followed by a final round of betting.\n"
                + "\r\n"
                + "Showdown\r\n"
                + "After the final round of betting, players reveal their cards. The player with the best combination of five cards wins. If there are several players with identical combinations, the pot is divided equally among them.\n"
                + "\r\n"
                + "\n");

        rulesText.setFont(new Font("Arial", Font.PLAIN, 20));
        rulesText.setEditable(false);
        rulesText.setOpaque(false);
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(rulesText);
        scrollPane.setPreferredSize(new Dimension(780, 550)); // Устанавливаем размер

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showStartScreen());

        rulesPanel.add(scrollPane, BorderLayout.CENTER);
        rulesPanel.add(backButton, BorderLayout.SOUTH);

        setPanel(rulesPanel);
    }

    private void showCombinationsScreen() {
        JPanel combinationsPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundCombinations != null) { // Проверяем наличие фона
                    int x = (getWidth() - backgroundCombinations.getIconWidth()) / 2;
                    int y = (getHeight() - backgroundCombinations.getIconHeight()) / 2;
                    backgroundCombinations.paintIcon(this, g, x, y); // Рисуем фон
                }
            }
        };

        JTextArea combinationsText = new JTextArea("1. Straight Flush: five consecutive cards of the same suit.\n"
                + "2. Four of a Kind: four cards of the same rank.\n"
                + "3. Full House: three cards of the same rank and a pair.\n"
                + "4. Flush: five cards of the same suit, not in order.\n"
                + "5. Straight: five consecutive cards of different suits.\n"
                + "6. Three of a Kind: three cards of the same rank.\n"
                + "7. Two Pair: two pairs of cards of different ranks.\n"
                + "8. One Pair: two cards of the same rank.\n"
                + "9. High Card: the highest card when no combination is formed.\n");

        combinationsText.setFont(new Font("Arial", Font.PLAIN, 20));
        combinationsText.setEditable(false);
        combinationsText.setOpaque(false);
        combinationsText.setLineWrap(true);
        combinationsText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(combinationsText);
        scrollPane.setPreferredSize(new Dimension(780, 550)); // Устанавливаем размер

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showStartScreen());

        combinationsPanel.add(scrollPane, BorderLayout.CENTER);
        combinationsPanel.add(backButton, BorderLayout.SOUTH);

        setPanel(combinationsPanel);
    }

    private void setPanel(JPanel panel) {
        if (currentPanel != null) {
            frame.remove(currentPanel);
        }
        currentPanel = panel;
        frame.add(currentPanel);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}