package FILEHANDLING;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandling { // Simply handles creation of scanners, will be handling exceptions later also
    public static Scanner FileScanner(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) { //file with specified name does not exist
            System.out.println("File does not exist");
            return null;
        }
        if (!file.canRead()) { //cannot read from the specified file
            System.out.println("File cannot be read");
            return null;
        }
        return new Scanner(file);
    }

    public static void writeToFile(String fileName, String contents) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                System.out.println("File was not created");
                return;
            }
        }
        if (!file.canWrite()) {
            System.out.println("File cannot be written to");
            return;
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
            writer.write(contents);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            assert writer != null;
            writer.close();
        }
    }
}