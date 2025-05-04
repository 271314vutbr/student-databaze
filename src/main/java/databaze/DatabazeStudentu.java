package databaze;

import studenti.Student;

import java.util.*;
import java.util.stream.Collectors;

public class DatabazeStudentu {
    private Map<Integer, Student> studenti = new HashMap<>();
    private int dalsiID = 1;

    public void pridejStudenta(Student s) {
        studenti.put(s.getId(), s);
        if (s.getId() >= dalsiID) {
            dalsiID = s.getId() + 1;
        }
    }
    public Student najdiStudenta(int id) {
        return studenti.get(id);
    }

    public void odeberStudenta(int id) {
        studenti.remove(id);
    }

    public List<Student> vratVsechnyStudenty() {
        return new ArrayList<>(studenti.values());
    }

    public List<Student> najdiPodlePrijmeni(String prijmeni) {
        return studenti.values().stream()
                .filter(s -> s.getPrijmeni().equalsIgnoreCase(prijmeni))
                .collect(Collectors.toList());
    }

    public List<Student> seradPodlePrijmeni() {
        return studenti.values().stream()
                .sorted(Comparator.comparing(Student::getPrijmeni))
                .collect(Collectors.toList());
    }

    public double prumerOboru(Class<? extends Student> typ) {
        return studenti.values().stream()
                .filter(typ::isInstance)
                .mapToDouble(Student::getStudijniPrumer)
                .average()
                .orElse(0.0);
    }

    public long pocetStudentuOboru(Class<? extends Student> typ) {
        return studenti.values().stream()
                .filter(typ::isInstance)
                .count();
    }


    public void nactiZDatabaze(IDatabaze sqlDatabaze) {
        List<Student> nacteni = sqlDatabaze.nactiStudenty();
        studenti.clear();
        for (Student s : nacteni) {
            pridejStudenta(s);
        }
    }
}
