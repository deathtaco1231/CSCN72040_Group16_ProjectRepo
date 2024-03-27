package USER_INTERFACE;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    protected ImageIcon logo = new ImageIcon("grocerylogo.png");
    protected RecieptMain recmain;
    protected RecieptBottom recbottom;
    protected TopPanel toppanel;

    public GUI() {
        this.setTitle("TomatoTown Point Of Sale Terminal");
        this.setLayout(null);
        this.setSize(1600, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(logo.getImage());
        this.toppanel = new TopPanel();
        this.add(toppanel);
        this.recmain = new RecieptMain();
        this.add(recmain);
        this.recbottom = new RecieptBottom();
        this.add(recbottom);
        this.setVisible(true);
    }
    public class RecieptMain extends JPanel {
        public JLabel text;

        public RecieptMain() {
            this.setLayout(null);
            this.setBounds(1200, 50, 385, 675);
            this.setOpaque(true);
            this.setBackground(Color.GREEN);
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 8));
            this.text = new JLabel("Items will go here as they are added!");
            text.setFont(new Font("Serif", Font.BOLD, 16));
            text.setHorizontalAlignment(JLabel.LEFT);
            text.setVerticalAlignment(JLabel.TOP);
            text.setHorizontalTextPosition(JLabel.LEFT);
            text.setVerticalTextPosition(JLabel.TOP);
            this.add(text);
            text.setVisible(true);
            text.setBounds(5, 5, 375, 665);
        }
        public void print(String text) {
            this.text.setText(text);
        }

    }
    public class RecieptBottom extends JPanel {
        protected JLabel text;
        public RecieptBottom() {
            this.setLayout(null);
            this.setBounds(1200, 715, 385, 125);
            this.setOpaque(true);
            this.setBackground(Color.CYAN);
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 10));
        }
    }
    public class TopPanel extends JPanel {
        public TopPanel() {
            this.setLayout(null);
            this.setBounds(0, 0, 1200, 120);
            this.setOpaque(true);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
            this.setBackground(Color.LIGHT_GRAY);
        }
    }
}
