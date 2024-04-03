package TERMINALSYSTEM;

import CATEGORY.Category;
import FILEHANDLING.FileHandling;
import ITEM.Item;
import ITERATOR.ItemIterator;
import USER_INTERFACE.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class TerminalSystem extends JPanel {
    protected ItemIterator itemIterator;
    private final GUI gui;
    protected CategoryPanel categoryPanel;
    protected ArrayList<Category> categories = new ArrayList<>(); // All categories are held here
    protected ActionPanel actionPanel;
    protected int numTimesUndoPressed = 0;
    protected double lastItemPrice;

    public TerminalSystem(GUI gui) throws FileNotFoundException {
        this.gui = gui;
        initCategories();
        this.categoryPanel = new CategoryPanel();
        gui.add(categoryPanel);
        this.actionPanel = new ActionPanel();
        gui.add(actionPanel);
        gui.repaint();
    }
    public void initCategories() throws FileNotFoundException {
        Category banners = new Category("Banners", "bannerCategory.jpg"); // Each of the 4 current categories is created here as individual objects
        Category guns = new Category("Weapons", "gunCategory.jpg");
        Category food = new Category("Foodstuff", "foodCategory.png");
        Category drinks = new Category("Drink Products", "drinksCategory.png");
        banners.initItemList("BannerItems.txt"); // Each category initializes its own item list
        guns.initItemList("GunItems.txt");
        food.initItemList("FoodItems.txt");
        drinks.initItemList("DrinkItems.txt");
        categories.add(banners); // Add each category to the categories list, we will remember the order that they are added for when we create buttons for each category later
        categories.add(guns);
        categories.add(food);
        categories.add(drinks);
    }

    protected void generateButton(int i, JButton button) {
        int column = i % 3;
        int row = i / 3;

        int x = 20 + column * 380;
        int y = 13 + row * 300;

        button.setBounds(x, y, 360, 280);

        //The rest is just default button properties to work in the panel plus java's built in command pattern
        button.setVisible(true);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
    }
    /* This will be where categories and items are displayed, or there will be a subclass for items
        which is just the same thing but for displaying items and not categories. For now, we will
        limit it to 2 rows of 3 for both items and categories before adding arrows for scrolling
        or anything like that. My idea was to use a button array, and just do some matching with what each
        button represents and what was on the screen to determine what the user clicked each time.
     */
    public class CategoryPanel extends JPanel implements ActionListener {
        protected JButton[] buttons = new JButton[categories.size()]; //Category buttons get stored in here
        private final ArrayList<ItemPanel> itemPanels = new ArrayList<>(); //Item panels get stored in here
        protected ItemPanel activeItemPanel;

        public CategoryPanel() {
            this.setLayout(null);
            this.setBounds(0, 110, 1200, 600);
            this.setOpaque(true);
            this.setBackground(Color.RED);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

            initButtons();
            initItemPanels();
        }

        public void initButtons() {
            JButton button;

            //The math in the loop determines dynamically where in the panel each button will go
            for (int i = 0; i < categories.size(); i++) {
                button = categories.get(i).getButton();

                generateButton(i, button);

                button.addActionListener(this);

                //Save the button for later reference, then add to the panel
                buttons[i] = button;
                this.add(button);
            }
        }

        private void initItemPanels() {
            for (Category category : categories) {
                ItemPanel itemPanel = new ItemPanel(category);
                itemPanels.add(itemPanel);
                gui.add(itemPanel);
            }
        }

        public void actionPerformed(ActionEvent e) {
            //Determine which button called the action then display its items
            for (int i = 0; i < buttons.length; i++) {
                if (e.getSource() == buttons[i]) {
                    ItemPanel itemPanel = itemPanels.get(i);
                    itemPanel.setVisible(true);
                    activeItemPanel = itemPanels.get(i);
                    this.setVisible(false);
                    gui.repaint();
                    actionPanel.buttons[0].setEnabled(true);
                    break;
                }
            }
        }
    }

    public class ItemPanel extends JPanel implements ActionListener{
        private final Category category;
        private final JButton[] buttons;

        public ItemPanel(Category category) {
            this.category = category;
            this.buttons = new JButton[category.itemCount()];
            this.setLayout(null);
            this.setBounds(0, 110, 1200, 600);
            this.setOpaque(true);
            this.setBackground(Color.RED);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
            this.setVisible(false);

            initButtons();
        }

        private void initButtons() {
            JButton button;

            //The math in the loop determines dynamically where in the panel each button will go
            for (int i = 0; i < category.itemCount(); i++) {
                button = new JButton(category.getItem(i).getName());
                Image img = category.getItem(i).getImageIcon().getImage(); // TEST LINE
                Image newImg = img.getScaledInstance( 150, 150,  java.awt.Image.SCALE_SMOOTH ); // TEST LINE
                button.setIcon(new ImageIcon(newImg)); // TEST LINE
                //button.setIcon(category.getItem(i).getImageIcon());

                generateButton(i, button);

                button.addActionListener(this);

                //Save the button for later reference, then add to the panel
                buttons[i] = button;
                this.add(button);
            }
        }

        public void actionPerformed(ActionEvent e) {
            //Determine which button called the action then display its items
            for (int i = 0; i < buttons.length; i++) {
                if (e.getSource() == buttons[i]) {
                    gui.recmain.print(category.getItem(i).toString());
                    lastItemPrice = category.getItem(i).getPrice();
                    gui.recbottom.print();
                    actionPanel.buttons[1].setEnabled(true);
                    numTimesUndoPressed = 0;
                    break;
                }
            }
        }
    }

    public class ActionPanel extends JPanel implements ActionListener{
        private final JButton[] buttons = new JButton[5]; //Stores the buttons in the bottom action panel (Back, Remove Item, Enter Code)
        public JTextField input;
        private String transactionStarted, fname, cname;

        public ActionPanel() {
            this.setLayout(null);
            this.setBounds(0, 710, 1200, 130);
            this.setOpaque(true);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
            this.setBackground(Color.LIGHT_GRAY);
            input = new JTextField();
            this.add(input);
            input.setBounds(615, 50, 60, 25);
            input.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            JLabel inputLabel = new JLabel("-->");
            inputLabel.setFont(new Font("Arial", Font.BOLD, 20));
            inputLabel.setForeground(new Color(0x7C0101));
            this.add(inputLabel);
            inputLabel.setBounds(585, 50, 75, 25);

            initButtons();
        }

        private void initButtons() {
            buttons[0] = new JButton("Back");
            buttons[0].setEnabled(false);
            buttons[1] = new JButton("Undo");
            buttons[1].setEnabled(false);
            buttons[2] = new JButton("Enter Code");
            buttons[3] = new JButton("Complete Order");
            buttons[3].setEnabled(false);
            buttons[4] = new JButton("Start Order");

            for (int i = 0; i < buttons.length; i++)
            {
                if (i == 3 || i == 4)
                    generateButton(i + 1, buttons[i]);
                else
                    generateButton(i, buttons[i]);
                this.add(buttons[i]);
            }
        }

        private void generateButton(int i, JButton button) {
            int x = 20 + i * 190;
            int y = 13;

            button.setBounds(x, y, 180, 90);

            button.setVisible(true);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setForeground(Color.BLACK);

            button.addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            //Determine which button called the action then display its items
            //for (int i = 0; i < buttons.length; i++) {
                Object source = e.getSource();
                if (source.equals(buttons[0])) {
                    goBack();
                   // break;
                } else if (source.equals(buttons[1])) {
                    numTimesUndoPressed++;
                    if (gui.recmain.undo(numTimesUndoPressed)) //Returns true if no items are left
                    {
                        buttons[1].setEnabled(false);
                    }
                    gui.recbottom.print();
                   // break;
                } else if (source.equals(buttons[2])) {
                    enterCode();
                    //break;
                } else if (source.equals(buttons[3])) {
                    buttons[3].setEnabled(false);
                    buttons[4].setEnabled(true);
                    gui.toppanel.configDate("completed");
                    try {
                        printReceipt();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (source.equals(buttons[4])) {
                    buttons[3].setEnabled(true);
                    buttons[4].setEnabled(false);
                    gui.toppanel.configDate("started");
                    transactionStarted = gui.toppanel.getCurDate() + "\n";  //Storing the time that the transaction started for when the receipt gets printed
                }

           // }
        }

        private void goBack() {
            categoryPanel.activeItemPanel.setVisible(false);
            categoryPanel.setVisible(true);
            buttons[0].setEnabled(false);
            gui.repaint();
        }

        private void printReceipt() throws IOException {
            //Make sure the clerk has entered a filename, their name, and that there are items in the basket
            fname = gui.toppanel.getFname();
            cname = gui.toppanel.getClerkName() + "\n";
            String basket = gui.recmain.text.getText();
            String[] importantData = { fname, cname, basket};

            if (checkFields(importantData)) {
                String receipt = getReceipt(basket);    //Build the receipt

                //Print to screen as well as to file
                System.out.println(receipt);
                FileHandling.writeToFile(fname, receipt);
            }
            else {
                System.out.println("Error: No items in basket");
            }
        }

        private String getReceipt(String basket) {
            String receiptDivider = "---------------------------------------------------\n";    //Receipt divider for categories
            String transactionEnded = gui.toppanel.getCurDate() + "\n";                         //Date and time of completed order
            String receiptBottom = gui.recbottom.getReceiptBottom();                            //Order total and other details

            //Build the string we are going to print
            return "Tomato Town\n" + receiptDivider
                    + transactionStarted + transactionEnded + "Clerk: " + cname + receiptDivider
                    + basket + receiptDivider
                    + receiptBottom;
        }

        //Helper function for printing the receipt
        //Sets values to default, if there are missing fields
        private boolean checkFields(String[] data) {
            if (data[0].isEmpty()) {
                fname = "receipt.txt";
            }
            if (!data[0].contains(".txt")) {
                fname += ".txt";
            }
            if (data[1].isBlank()) {
                cname = "Unknown\n";
            }
            return data[2].contains("CODE");   //If there are items in the basket, true proceeds with transaction, false prints an error
        }

        private void enterCode() { // Uses Iterator Instead Of Direct List Access
            if (!input.getText().trim().isEmpty()) {
                for (int i = 0; i < categories.size(); i++) {
                    itemIterator = categories.get(i);
                    Iterator items = itemIterator.createIterator();
                    while (items.hasNext()) {
                        Item tmp = (Item) items.next();
                        if (Integer.parseInt(input.getText()) == tmp.getCode()) {
                            gui.recmain.print(tmp.toString());
                            lastItemPrice = tmp.getPrice();
                            gui.recbottom.print();
                            actionPanel.buttons[1].setEnabled(true);
                            numTimesUndoPressed = 0;
                            i = categories.size();
                            break;
                        }
                    }

                }
            }


        }
       /* private void enterCode() {
            for (int i = 0; i < categories.size(); i++) {
                for (int j = 0; j < categories.get(i).itemCount(); j++) {
                    if (!input.getText().trim().isEmpty()) {
                        if (Integer.parseInt(input.getText()) == categories.get(i).getItem(j).getCode()) {
                            gui.recmain.print(categories.get(i).getItem(j).toString());
                            lastItemPrice = categories.get(i).getItem(j).getPrice();
                            gui.recbottom.print();
                            actionPanel.buttons[1].setEnabled(true);
                            numTimesUndoPressed = 0;
                            j = categories.get(i).itemCount();
                            i = categories.size();
                        }
                    }
                }
            }
        }*/
    }
}
