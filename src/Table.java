import javax.swing.*;  // Импорт для Swing-компонентов
import java.awt.*;  // Импорт для AWT-компонентов
import java.awt.event.*;  // Импорт для обработки событий
import java.util.*;  // Импорт для коллекций и Random

public class Table {
    private JFrame frame;
    private JPanel tablePanel;  // Основная панель для покерного стола
    private JPanel playerPanel;  // Панель для карт игрока
    private JPanel flopPanel;   // Панель для флопа
    private JPanel turnPanel;   // Панель для терна
    private JPanel riverPanel;  // Панель для ривера
    private ImageIcon icon;  // Изображение стола

    private Random random = new Random();  // Инициализация объекта Random

    public Table() {
        // Инициализация окна
        frame = new JFrame("Poker Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Основная панель стола с BorderLayout
        tablePanel = new JPanel(new BorderLayout());
        frame.add(tablePanel);

        // Панель для фона
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (icon != null) {
                    int x = (getWidth() - icon.getIconWidth()) / 2;
                    int y = (getHeight() - icon.getIconHeight()) / 2;
                    icon.paintIcon(this, g, x, y);  // Отрисовка изображения стола
                }
            }
        };
        icon = new ImageIcon("Poker_table.jpg");  // Загрузка фона стола
        tablePanel.add(backgroundPanel, BorderLayout.CENTER);  // Добавляем в центр

        // Панель для карт игроков
        playerPanel = new JPanel(new FlowLayout());
        tablePanel.add(playerPanel, BorderLayout.SOUTH);

        // Панели для флопа, терна, ривера
        flopPanel = new JPanel();
        turnPanel = new JPanel();
        riverPanel = new JPanel();

        tablePanel.add(flopPanel, BorderLayout.NORTH);  // Флоп сверху
        tablePanel.add(turnPanel, BorderLayout.WEST);  // Терн слева
        tablePanel.add(riverPanel, BorderLayout.EAST);  // Ривер справа

        // Обработка событий клавиатуры
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_SPACE:
                        startGame();  // Начать игру
                        break;
                    case KeyEvent.VK_F:
                        flop();  // Флоп
                        break;
                    case KeyEvent.VK_T:
                        turn();  // Терн
                        break;
                    case KeyEvent.VK_R:
                        river();  // Ривер
                        break;
                }
                frame.repaint();  // Перерисовка после изменения
            }
        });

        frame.setVisible(true);  // Отображаем окно
    }

    private void startGame() {
        System.out.println("Game started!");
        dealCards();  // Раздача карт игрокам
    }

    private void dealCards() {
        // Раздаем карты игрокам
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        playerPanel.removeAll();  // Очищаем старые карты
        for (int i = 0; i < 2; i++) {
            String suit = suits[random.nextInt(suits.length)];  // Генерируем случайный костюм
            String rank = ranks[random.nextInt(ranks.length)];  // Генерируем случайный ранг
            String cardName = rank + "_of_" + suit + ".jpg";  // Имя файла карты
            ImageIcon cardIcon = new ImageIcon(cardName);
            JLabel cardLabel = new JLabel(cardIcon);  // Метка с изображением карты
            playerPanel.add(cardLabel);  // Добавляем в панель карт игрока
        }

        playerPanel.revalidate();  // Обновляем компоновку
        playerPanel.repaint();  // Перерисовываем
    }

    protected void flop() {
        System.out.println("Flop");
        flopPanel.removeAll();  // Очищаем флоп
        // Добавляем 3 карты на флоп (пример)
        for (int i = 0; i < 3; i++) {
            JLabel cardLabel = new JLabel(new ImageIcon("Flop_Card_" + (i + 1) + ".jpg"));
            flopPanel.add(cardLabel);
        }
        flopPanel.revalidate();  // Обновляем
        flopPanel.repaint();  // Перерисовываем
    }

    protected void turn() {
        System.out.println("Turn");
        turnPanel.removeAll();  // Очищаем терн
        // Добавляем карту терна (пример)
        JLabel cardLabel = new JLabel(new ImageIcon("Turn_Card.jpg"));
        turnPanel.add(cardLabel);  // Добавляем в панель терна
        turnPanel.revalidate();
        turnPanel.repaint();
    }

    protected void river() {
        System.out.println("River");
        riverPanel.removeAll();  // Очищаем ривер
        // Добавляем карту ривера (пример)
        JLabel cardLabel = new JLabel(new ImageIcon("River_Card.jpg"));
        riverPanel.add(cardLabel);
        riverPanel.revalidate();
        riverPanel.repaint();
    }

    public static void main(String[] args) {
        new Table();  // Запускаем покерный стол
    }
}