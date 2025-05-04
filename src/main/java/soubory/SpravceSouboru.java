package soubory;

import databaze.IDatabaze;
import studenti.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SpravceSouboru implements IDatabaze {
    private final String soubor = "studenti.txt";

    @Override
    public List<Student> nactiStudenty() {
        List<Student> studenti = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(soubor))) {
            studenti = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nepodařilo se načíst soubor: " + e.getMessage());
        }
        return studenti;
    }

    @Override
    public void ulozStudenty(List<Student> studenti) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(soubor))) {
            oos.writeObject(studenti);
        } catch (IOException e) {
            System.out.println("Nepodařilo se uložit do souboru: " + e.getMessage());
        }
    }
}
