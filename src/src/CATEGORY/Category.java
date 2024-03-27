package CATEGORY;
import ITEM.*;
import java.util.*;

public class Category {
    protected ArrayList<Item> items = new ArrayList<Item>();
    public Category() {

    }
    public int itemCount() {
        return this.items.size();
    }
    public Item getItem(int index) {
        return this.items.get(index);
    }

}
