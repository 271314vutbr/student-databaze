package databaze;

import sql.SQLDatabaze;
import studenti.Student;

import java.util.*;

public class DatabazeStudentu {
    private Map<Integer, Student> studenti;
    private int dalsiID;
    private final SQLDatabaze sqlDatabaze;

    public DatabazeStudentu() {
        this.studenti = new HashMap<>();
        this.sqlDatabaze = new SQLDatabaze();
        this.dalsiID = 1;
    }

    public void pridejStudenta(Student s) {
        studenti.put(s.getID(), s);
        if (s.getID() >= dalsiID) {
            dalsiID++;
        }
    }

    public void odeberStudenta(int id) {
        studenti.remove(id);
    }

    public Student najdiStudenta(int id) {
        return studenti.get(id);
    }

    public Collection<Student> getVsechnyStudenty() {
        return studenti.values();
    }

    public int generujId() {
        return dalsiID++;
    }

    public List<Student> seradPodlePrijmeni() {
        return studenti.values().stream().sorted(Comparator.comparing(Student::getPrijmeni)).toList();
    }

    public double prumerOboru(Class<? extends Student> typ) {
        return studenti.values().stream().filter(typ::isInstance).mapToDouble(Student::getStudijniPrumer).average().orElse(0.0);
    }

    public long pocetStudentuOboru(Class<? extends Student> typ) {
        return studenti.values().stream().filter(typ::isInstance).count();
    }

    public void ulozDoDatabaze() {
        sqlDatabaze.ulozStudenty(this);
    }

    public void nactiZDatabaze() {
        List<Student> nacteni = sqlDatabaze.nactiStudenty();
        studenti.clear();
        for (Student s : nacteni) {
            pridejStudenta(s);
        }
    }
}
