import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game {
    private JFrame frame;
    private Table table;
    private ImageIcon icon;

    public Game() {
        frame = new JFrame("gaming table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1500);

        table = new Table();

        // Создаем панель для размещения компонентов
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Отрисовываем изображение стола в центре панели
                int x = (getWidth() - icon.getIconWidth()) / 2;
                int y = (getHeight() - icon.getIconHeight()) / 2;
                icon.paintIcon(this, g, x, y);
            }
        };
        frame.add(panel);

        // Создаем метку для отображения изображения стола
        icon = new ImageIcon("Poker_table.jpg");

        // Добавляем обработчик клавиш для управления игрой
        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_SPACE:
                        startGame();
                        break;
                    case KeyEvent.VK_F:
                        table.flop();
                        break;
                    case KeyEvent.VK_T:
                        table.turn();
                        break;
                    case KeyEvent.VK_R:
                        table.river();
                        break;
                }
                panel.repaint(); // Перерисовываем панель
            }
        });

        frame.setVisible(true);
    }

    private void startGame() {
        // Логика начала игры
        table.startGame();
    }

    public static void main(String[] args) {
        new Game();
    }
}