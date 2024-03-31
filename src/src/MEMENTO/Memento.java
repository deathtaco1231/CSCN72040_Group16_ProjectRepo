package MEMENTO;

public class Memento {
    // The notes stored in memento Object

    private final String item; // This will be the ENTIRE receipt contents, so that we can roll back each time and just reprint an older version
                                // rather than figuring out what it was that was last added and removing it every time.

    // Save a new note String to the memento Object

    public Memento(String noteSave) {
        item = noteSave;
    }

    // Return the value stored in article

    public String getSavedItem() {
        return item;
    }

}
