package TERMINALSYSTEM;

import USER_INTERFACE.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TerminalSystem extends JPanel {
    private GUI gui;
    protected ItemPanel itempanel;
    public TerminalSystem(GUI gui) {
        this.gui = gui;
        this.itempanel = new ItemPanel();
        gui.add(itempanel);
    }

    public class ItemPanel extends JPanel implements ActionListener {
        public ItemPanel() {
            this.setLayout(null);
            this.setBounds(0, 110, 1200, 600);
            this.setOpaque(true);
            this.setBackground(Color.RED);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        }
        public void actionPerformed(ActionEvent e) {

        }
    }
}
