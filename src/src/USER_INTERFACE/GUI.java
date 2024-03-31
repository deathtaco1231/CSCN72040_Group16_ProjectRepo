package USER_INTERFACE;

import MEMENTO.Caretaker;
import MEMENTO.Originator;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUI extends JFrame {
    protected ImageIcon logo = new ImageIcon("grocerylogo.png");
    public ReceiptMain recmain;
    public ReceiptBottom recbottom;
    protected TopPanel toppanel;
    protected Originator originator = new Originator();
    protected Caretaker caretaker = new Caretaker();

    public GUI() {
        this.setTitle("TomatoTown Point Of Sale Terminal");
        this.setLayout(null);
        this.setSize(1600, 900); // TOTAL SIZE OF THE WINDOW AT ALL TIMES
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(logo.getImage());
        this.toppanel = new TopPanel();
        this.add(toppanel);
        this.recmain = new ReceiptMain();
        this.add(recmain);
        this.recbottom = new ReceiptBottom();
        this.add(recbottom);
        this.setVisible(true);
    }
    public class ReceiptMain extends JPanel { // Read-only receipt screen at the right of the frame
        public JTextArea text; // Just contains one label that we will manually manipulate to make it look like a linear receipt
        String list = "";

        public ReceiptMain() {
            this.setLayout(null);
            this.setBounds(1200, 50, 385, 675);
            this.setOpaque(true);
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 8));
            this.text = new JTextArea("Items will go here as they are added!\n");
            text.setFont(new Font("Serif", Font.BOLD, 16));
            text.setBackground(Color.GREEN);
            text.setEditable(false);
            text.setCaretColor(new Color(0, 0, 0, 0)); //This is so the caret cursor does not appear in the panel
            text.setVisible(true);
            this.add(text);
            text.setBounds(5, 5, 375, 665);

            //Create the first memento as an empty string
            originator.set("");
            caretaker.addMemento(originator.storeInMemento());
        }
        public void print(String text) {
            //First, retrieve the current contents
            list = originator.restoreFromMemento(caretaker.getMemento(caretaker.getNumItems() - 1)); //Subtract 1 for size to last index conversion

            //Second, add the new item (text) to the string
            list += text;

            //Third set the originator to the newly built list
            originator.set(list);

            //Finally, store the new state(list) into a memento and give it to the caretaker
            caretaker.addMemento(originator.storeInMemento());

            this.text.setText(list);
            this.repaint();
        } // Each time we add or remove stuff, we will just pass it a string to display

        public boolean undo(int i) {
            int numItems = caretaker.getNumItems();

            //Get the previous version of the list stored within the caretaker's second last index
            if (numItems > i) {
                list = originator.restoreFromMemento(caretaker.getMemento((numItems - 1) - (2 * i - 1)));
            }
            else {
                list = originator.restoreFromMemento(caretaker.getMemento(numItems - (i)));
            }

            caretaker.addMemento(originator.storeInMemento());

            this.text.setText(list);
            this.repaint();

            //Return true if there are no more items in the undone list
            return list.lines().findAny().isEmpty();
        }

    }
    public class ReceiptBottom extends JPanel { // This is for the price, tax and other dynamic info at the bottom of the screen under the receipt
        protected JTextArea text; //Changed this and the label in recmain to text areas, similar functionality as label, except it allows for multiple lines
        double subtotal = 0;
        public ReceiptBottom() {
            this.setLayout(null);
            this.setBounds(1200, 715, 385, 125);
            this.setOpaque(true);
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 10));
            text = new JTextArea("Price will be displayed here!\n");
            text.setFont(new Font("Serif", Font.BOLD, 16));
            text.setBackground(Color.CYAN);
            text.setEditable(false);
            text.setCaretColor(new Color(0, 0, 0, 0));
            text.setVisible(true);
            this.add(text);
            text.setBounds(5, 5, 375, 665); // reminder that for ALL objects inside panels, these bounds are for the panel, not the whole window
        }

        public void print() {
            String itemsText = originator.restoreFromMemento(caretaker.getMemento(caretaker.getNumItems() - 1));

            Pattern pricePattern = Pattern.compile("PRICE \\$(\\d+\\.\\d{2})");
            Matcher matcher = pricePattern.matcher(itemsText);

            double subtotal = 0.0;
            int numItems = 0;

            // Find all price occurrences and add them to the subtotal
            while (matcher.find()) {
                subtotal += Double.parseDouble(matcher.group(1));
                numItems++;
            }

            // Calculate tax and total due
            double tax = subtotal * 0.13;
            double totalDue = subtotal + tax;

            String receiptText = String.format("Items: %d\nSubtotal: $%.2f\nTax: $%.2f\nTotal Due: $%.2f",
                    numItems, subtotal, tax, totalDue);

            text.setText(receiptText);
        }
    }
    public static class TopPanel extends JPanel { // This will be where the register operator info is stored
        protected JLabel text;
        public TopPanel() {
            this.setLayout(null);
            this.setBounds(0, 0, 1200, 120);
            this.setOpaque(true);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
            this.setBackground(Color.LIGHT_GRAY);
            text = new JLabel("Cashier info displayed here!");
            text.setFont(new Font("Serif", Font.BOLD, 16));
            text.setHorizontalAlignment(JLabel.LEFT);
            text.setVerticalAlignment(JLabel.TOP);
            text.setHorizontalTextPosition(JLabel.LEFT);
            text.setVerticalTextPosition(JLabel.TOP);
            text.setVisible(true);
            this.add(text);
            text.setBounds(5, 5, 1190, 110);
        }
    }
}