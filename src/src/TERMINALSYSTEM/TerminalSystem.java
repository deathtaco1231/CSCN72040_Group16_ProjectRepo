package TERMINALSYSTEM;

import CATEGORY.Category;
import ITEM.Item;
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
    protected ArrayList<Category> categories = new ArrayList<Category>(); // All categories are held here
    public TerminalSystem(GUI gui) throws FileNotFoundException {
        this.gui = gui;
        this.categoryPanel = new CategoryPanel();
        initCategories();
        gui.add(categoryPanel);
    }
    public void initCategories() throws FileNotFoundException {
        Category banners = new Category("Banners", "test.png"); // Each of the 4 current categories is created here as individual objects
        Category guns = new Category("Weapons", "test.png");
        Category food = new Category("Foodstuff", "test.png");
        Category drinks = new Category("Drink Products", "test.png");
        banners.initItemList("BannerItems.txt"); // Each categry initalizes its own item list
        guns.initItemList("GunItems.txt");
        food.initItemList("FoodItems.txt");
        drinks.initItemList("DrinkItems.txt");
        categories.add(banners); // Add each category to the categories list, we will remember the order that they are added for when we create buttons for each category later
        categories.add(guns);
        categories.add(food);
        categories.add(drinks);
    }

    /* This will be where categories and items are displayed, or there will be a subclass for items
        which is just the same thing but for displayig items and not categories. For now we will
        limit it to 2 rows of 3 for both items and categories before adding arrows for scrolling
        or anything like that. My idea was to use a button array, and just do some matching with what each
        button represents and what was on the screen to determine what the user clicked each time.
     */
    public class CategoryPanel extends JPanel implements ActionListener {
        public CategoryPanel() {
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
