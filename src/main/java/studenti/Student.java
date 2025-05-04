package studenti;

import java.util.ArrayList;
import java.util.List;

public abstract class Student {
    protected int id;
    protected String jmeno;
    protected String prijmeni;
    protected int rokNarozeni;
    protected List<Integer> znamky;

    public Student(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
        znamky = new ArrayList<Integer>();
    }

    public void pridejZnamku(int znamka) {
        if (znamka >= 1 && znamka <= 5) {
            znamky.add(znamka);
        } else {
            System.out.println("Neplatná známka. Validní rozmezí je od 1 do 5.");
        }
    }

    public double getStudijniPrumer() {
        if (znamky.isEmpty()) {
            return 0;
        }
        double soucet = 0;
        for (int z : znamky) {
            soucet += z;
        }
        return soucet / znamky.size();
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

    public List<Integer> getZnamky() {
        return new ArrayList<>(znamky);
    }

    public abstract void spustDovednost();

    @Override
    public String toString() {
        return String.format("ID: %d | Jméno: %s %s | Rok narození: %d | Průměr: %.2f", id, jmeno, prijmeni, rokNarozeni, getStudijniPrumer());
    }
}
