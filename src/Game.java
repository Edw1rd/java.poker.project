import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.poker.cards.Card;
import com.poker.cards.Deck;

public class Game {
    private JFrame frame;
    private JPanel panel;
    private Table table;
    private ImageIcon icon;
    private Deck deck;  // Добавляем колоду для игры в покер

    public Game() {
        // Инициализация окна
        frame = new JFrame("Gaming Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1500);

        // Создаем колоду и перемешиваем
        deck = new Deck();
        deck.shuffle();

        // Инициализация стола
        table = new Table();

        // Инициализация панели с изображением
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (icon != null) {  // Проверяем, что изображение установлено
                    int x = (getWidth() - icon.getIconWidth()) / 2;
                    int y = (getHeight() - icon.getIconHeight()) / 2;
                    icon.paintIcon(this, g, x, y);
                }
            }
        };
        
        // Установка фокуса и обработчиков событий
        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_SPACE:
                        startGame();  // Начать игру
                        break;
                    case KeyEvent.VK_F:
                        table.flop();  // Флоп
                        break;
                    case KeyEvent.VK_T:
                        table.turn();  // Терн
                        break;
                    case KeyEvent.VK_R:
                        table.river();  // Ривер
                        break;
                }
                panel.repaint();  // Перерисовываем панель
            }
        });

        // Добавление панели в окно
        frame.add(panel);
        frame.setVisible(true);

        // Инициализация иконки
        icon = new ImageIcon("Poker_table.jpg");
    }

    void startGame() {
        // Логика начала игры
        System.out.println("Game started!");
        // Раздаем первые пять карт
        for (int i = 0; i < 5; i++) {
            Card card = deck.drawCard();
            System.out.println("Drew card: " + card);
        }
    }

    public static void main(String[] args) {
        new Game();  // Запуск игры
    }
}
