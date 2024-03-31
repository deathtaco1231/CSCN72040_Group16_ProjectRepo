package MEMENTO;

public class Originator {
    private String item;

    // Sets the value for the article

    public void set(String newNote) {
        System.out.println("From Originator: Current Version of Note\n"+newNote+ "\n");
        this.item = newNote;
    }

    // Creates a new Memento with a new note

    public Memento storeInMemento() {
        System.out.println("From Originator: Saving to Memento");
        return new Memento(item);
    }

    // Gets the article currently stored in memento

    public String restoreFromMemento(Memento memento) {

        item = memento.getSavedItem();

        System.out.println("From Originator: Previous Note Saved in Memento\n"+item + "\n");

        return item;

    }
}