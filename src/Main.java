import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Игровой стол");

        JLabel label = new JLabel();

        ImageIcon icon = new ImageIcon("Poker_table.jpg");
        label.setIcon(icon);

        frame.add(label);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1800, 1600); 
        frame.setVisible(true); 
    }
}