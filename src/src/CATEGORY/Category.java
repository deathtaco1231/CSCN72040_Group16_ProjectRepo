package CATEGORY;
import ITEM.*;

import javax.swing.*;
import java.util.*;

public class Category {
    protected String name;
    protected ImageIcon symbol;
    protected ArrayList<Item> items = new ArrayList<Item>();
    public Category() {
        this.name = "UNNAMED";
        this.symbol = null;
    }
    public int itemCount() {
        return this.items.size();
    }
    public Item getItem(int index) {
        return this.items.get(index);
    }

}
