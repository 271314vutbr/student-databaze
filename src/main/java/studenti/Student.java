package studenti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Student implements Serializable {
    protected int id;
    protected String jmeno;
    protected String prijmeni;
    protected int rokNarozeni;
    protected List<Integer> znamky = new ArrayList<>();

    public Student(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
    }

    public int getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getRokNarozeni() {
        return rokNarozeni;
    }

    public void pridejZnamku(int znamka) {
        znamky.add(znamka);
    }

    public double getStudijniPrumer() {
        if (znamky.isEmpty()) return 0.0;
        return znamky.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public List<Integer> getZnamky() {
        return znamky;
    }

    public abstract String vypisDovednost();
}
