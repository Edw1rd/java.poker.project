import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

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

    private JPanel topPanel; // Добавляем поле topPanel

    private JLabel[] communityCardLabels; // Метки для общих карт

    public Game() {
        frame = new JFrame("PokerGame"); // Создаем только один `JFrame`
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Окно по центру

        deck = new Deck(); // Создаем и перемешиваем колоду карт
        deck.shuffle();

        table = new Table(); // Инициализация игрового стола

        // Загрузка фона для начального экрана
        backgroundStart = loadImage("poker_1.jpg", 1400, 900);
        // Загрузка фона для экрана с правилами
        backgroundRules = loadImage("rules_background.jpg", 1400, 900);
        // Загрузка фона для экрана с комбинациями
        backgroundCombinations = loadImage("combinations_background.jpg", 1400, 900);

        showStartScreen(); // Показываем начальный экран

        frame.setVisible(true); // Окно должно быть видимым
    }

    private ImageIcon loadImage(String path, int width, int height) {
        Image originalImage = new ImageIcon(path).getImage();
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
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
        while (true) {
            String input = JOptionPane.showInputDialog(frame, "Enter the initial number of chips for each player:", "Initial number of chips", JOptionPane.PLAIN_MESSAGE);
            try {
                initialChips = Integer.parseInt(input);
                if (initialChips < 500) {
                    JOptionPane.showMessageDialog(frame, "The starting number of chips must be a positive number greater than or equal to 500.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    startGame(); // We start the game after entering the number of chips
                    break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Инициализация topPanel

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            showConfirmationDialog("Are you sure you want to go back?", this::showStartScreen);
        });
        topPanel.add(backButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> showSettingsScreen());
        topPanel.add(settingsButton);

        frame.add(topPanel, BorderLayout.NORTH);

        JPanel tablePanel = table.getTablePanel();
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            dealCardsToPlayers(); // Раздать карты игрокам
            dealCommunityCards(); // Раздать карты общего поля

            // Отобразить карты на экране
            table.displayPlayerCards();
            displayCommunityCards();

            table.startGame();

            tablePanel.remove(startButton); // Удалить кнопку после начала игры
            tablePanel.revalidate();
            tablePanel.repaint();
        });

        tablePanel.add(startButton, BorderLayout.SOUTH);

        setPanel(tablePanel);
    }

    private void displayCommunityCards() {
        JPanel communityPanel = new JPanel(new FlowLayout());
        communityCardLabels = new JLabel[5]; // Инициализация массива меток для карт

        for (int i = 0; i < communityCardLabels.length; i++) {
            communityCardLabels[i] = new JLabel();
            communityPanel.add(communityCardLabels[i]);
        }

        frame.add(communityPanel, BorderLayout.CENTER);

        // Показать первые три карты
        for (int i = 0; i < 3; i++) {
            Card card = ((List<Card>) table.getCommunityCards()).get(i);
            ImageIcon cardImage = loadImage(card.getImagePath(), 100, 150); // Путь к изображению карты и ее размер
            communityCardLabels[i].setIcon(cardImage);
        }

        frame.revalidate();
        frame.repaint();
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
                + "\r\n"
                + "This is the round after the players receive their pocket cards and before the first 3 community cards (flop) are dealt.\r\n"
                + "\r\n"
                + "Flop\r\n"
                + "\r\n"
                + "This is the first round of community cards in which 3 cards are revealed.\r\n"
                + "\r\n"
                + "Turn\r\n"
                + "\r\n"
                + "This is the second round of community cards in which the fourth community card is revealed.\r\n"
                + "\r\n"
                + "River\r\n"
                + "\r\n"
                + "This is the final round of community cards in which the fifth community card is revealed.\r\n"
                + "\r\n"
                + "Showdown\r\n"
                + "\r\n"
                + "This is the stage of revealing cards. The remaining players reveal their pocket cards and the winner is determined."
                + " The winner is the player with the highest poker combination."
        );

        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
        rulesText.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(rulesText);
        rulesPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showStartScreen());
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

        JTextArea combinationsText = new JTextArea("Combinations in poker:\n"

                + "\n"
                
                + "1. High Card: The highest card in the hand when no other combinations are formed.\n"
        		
                + "\n"
                
                + "2. One Pair: Two cards of the same rank.\n"

                + "\n"
                
                + "3. Two Pair: Two pairs of cards of the same rank.\n"

                + "\n"
                
                + "4. Three of a Kind: Three cards of the same rank.\n"

                + "\n"
                
                + "5. Straight: Five consecutive cards of different suits.\n"

                + "\n"
                
                + "6. Flush: Five cards of the same suit in any order.\n"

                + "\n"
                
                + "7. Full House: A combination of Three of a Kind and One Pair.\n"

                + "\n"
                
                + "8. Four of a Kind: Four cards of the same rank.\n"

                + "\n"
                
                + "9. Straight Flush: Five consecutive cards of the same suit.\n"

                + "\n"
                
                + "10. Royal Flush: A, K, Q, J, 10 all of the same suit."
        );

        combinationsText.setLineWrap(true);
        combinationsText.setWrapStyleWord(true);
        combinationsText.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(combinationsText);
        combinationsPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showStartScreen());
        combinationsPanel.add(backButton, BorderLayout.SOUTH);

        setPanel(combinationsPanel);
    }

    private void setPanel(JPanel panel) {
        if (currentPanel != null) {
            frame.remove(currentPanel);
        }
        currentPanel = panel;
        frame.add(currentPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}

