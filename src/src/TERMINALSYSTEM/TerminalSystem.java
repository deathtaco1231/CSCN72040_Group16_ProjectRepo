package TERMINALSYSTEM;

import CATEGORY.Category;
import USER_INTERFACE.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TerminalSystem extends JPanel {
    private GUI gui;
    protected CategoryPanel categoryPanel;
    protected ArrayList<Category> categories = new ArrayList<>(); // All categories are held here
    public TerminalSystem(GUI gui) throws FileNotFoundException {
        this.gui = gui;
        initCategories();
        this.categoryPanel = new CategoryPanel();
        gui.add(categoryPanel);
        gui.repaint();
    }
    public void initCategories() throws FileNotFoundException {
        Category banners = new Category("Banners", "test.png"); // Each of the 4 current categories is created here as individual objects
        Category guns = new Category("Weapons", "test.png");
        Category food = new Category("Foodstuff", "test.png");
        Category drinks = new Category("Drink Products", "test.png");
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
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
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
                    this.setVisible(false);
                    gui.repaint();
                    break;
                }
            }
        }
    }

    public class ItemPanel extends JPanel implements ActionListener{
        private Category category;
        private JButton[] buttons;

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
                button.setIcon(category.getItem(i).getImageIcon());

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
                    break;
                }
            }
        }
    }
}
