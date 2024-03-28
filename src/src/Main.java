import TERMINALSYSTEM.TerminalSystem;
import USER_INTERFACE.*;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
         GUI gui = new GUI();
         TerminalSystem terminalsystem = new TerminalSystem(gui);
    }
}