package MEMENTO;

public class Memento {
    // The notes stored in memento Object

    private String item;

    // Save a new note String to the memento Object

    public Memento(String noteSave) {
        item = noteSave;
    }

    // Return the value stored in article

    public String getSavedItem() {
        return item;
    }

}
