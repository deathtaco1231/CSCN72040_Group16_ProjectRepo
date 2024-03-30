package ITEM;

import javax.swing.*;
import java.util.Scanner;

public class Item {
    protected int code;
    protected String name, img;
    protected double price;
    public Item() {
        this.code = 0;
        this.name = "UNNAMED";
        this.price = 0;
        this.img = "defaultitemimg.jpg"; // This will be used for when each item is displayed on the item panel, we will just handle the creation of icons there
    }
    public Item(int code, String name, double price, String img) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.img = img;
    }
    public String toString() { // Will be used when adding to the reciept, and for appending the text held by each memento
        return "CODE " + code + " NAME " + name + " PRICE $" + price;
    }
    public String getName() { return this.name; }
    public ImageIcon getImageIcon() { return new ImageIcon(img); }
}
