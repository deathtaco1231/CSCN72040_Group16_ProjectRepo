package CATEGORY;
import FILEHANDLING.FileHandling;
import ITEM.*;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.*;

public class Category {
    protected String name;
    protected ImageIcon symbol;
    private final JButton button;
    protected ArrayList<Item> items = new ArrayList<>(); // Holds all items of this category
    public Category() {
        this.name = "UNNAMED";
        this.symbol = null;
        this.button = null;
    }
    public Category(String name, String symbol) {
        this.name = name;
        this.symbol = new ImageIcon(symbol); // This is used when displaying categories as the image

        // On construction, we can easily take the icon and name parameters and create a button instance within the object, this way we can couple the button with the category
        button = new JButton(name);
        button.setIcon(this.symbol);
    }
    public void initItemList(String fname) throws FileNotFoundException {
        // Each category gets its own scanner to scan items of its designated text file
        Scanner itemScanner = FileHandling.FileScanner(fname);
        assert itemScanner != null;
        int itemcount = itemScanner.nextInt(); // First line of every item file MUST be how many items are stored
        while (itemcount != 0) {
            items.add(new Item(itemScanner.nextInt(), itemScanner.next(), itemScanner.nextDouble(), itemScanner.next())); // Grabs data from each line
            itemcount--;
        }
    }
    public ImageIcon getSymbol() {
        return this.symbol;
    }
    public String getName() {
        return this.name;
    }
    public int itemCount() {
        return this.items.size();
    }
    public Item getItem(int index) {
        return this.items.get(index);
    }
    public JButton getButton() { return this.button; }
}
