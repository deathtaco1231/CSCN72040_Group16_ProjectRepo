package ITEM;

import javax.swing.*;

public class Item {
    protected int code;
    protected String name, img;
    protected double price;
    public Item(int code, String name, double price, String img) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.img = img;
    }
    public String toString() { // Will be used when adding to the receipt, and for appending the text held by each memento
        return "CODE " + code + " NAME " + name + " PRICE $" + price + "\n";
    }
    public String getName() { return this.name; }
    public int getCode() {return this.code;}
    public ImageIcon getImageIcon() { return new ImageIcon(img); }
    public double getPrice() { return this.price; }
}
