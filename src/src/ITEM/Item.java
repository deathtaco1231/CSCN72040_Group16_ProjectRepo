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
        this.img = "defaultitemimg.jpg";
    }
    public Item(int code, String name, double price, String img) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.img = img;
    }
}
