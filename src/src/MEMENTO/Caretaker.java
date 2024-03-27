package MEMENTO;

import java.util.ArrayList;

public class Caretaker {
    // Where all mementos are saved

    ArrayList<Memento> savedItems = new ArrayList<Memento>();

    // Adds memento to the ArrayList

    public void addMemento(Memento m) { savedItems.add(m); }

    // Gets the memento requested from the ArrayList

    public Memento getMemento(int index) { return savedItems.get(index); }

}
