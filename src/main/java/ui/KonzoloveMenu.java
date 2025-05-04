package ui;

import databaze.DatabazeStudentu;
import databaze.IDatabaze;
import studenti.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class KonzoloveMenu {
    private final DatabazeStudentu databaze;
    private final IDatabaze uloziste;
    private final Scanner scanner;

    public KonzoloveMenu(DatabazeStudentu databaze, IDatabaze uloziste) {
        this.databaze = databaze;
        this.uloziste = uloziste;
        this.scanner = new Scanner(System.in);
    }

    public void spust() {
        int volba;
        do {
            vypisMenu();
            volba = nactiCislo();
            switch (volba) {
                case 1 -> pridatStudenta();
                case 2 -> zadatZnamku();
                case 3 -> hledatPodlePrijmeni();
                case 4 -> vypisVsechny();
                case 5 -> vypisPrumery();
                case 6 -> vypisPocty();
                case 7 -> smazatStudenta();
                case 8 -> ulozitManualne();
                case 9 -> nacistZeSouboru();
                case 10 -> dovednostStudenta();
                case 0 -> System.out.println("Program ukončen.");
                default -> System.out.println("Neplatná volba.");
            }
        } while (volba != 0);
    }

    private void dovednostStudenta() {
        System.out.print("Zadejte ID studenta k spusteni dovednosti: ");
        int id = nactiCislo();
        Student s = databaze.najdiStudenta(id);
        if (s == null) {
            System.out.println("Student s ID " + id + " nebyl nalezen.");
        } else {
            System.out.println("  Dovednost: " + s.vypisDovednost());
        }
    }

    private void vypisMenu() {
        System.out.println("\n===== STUDENTSKÁ DATABÁZE =====");
        System.out.println("1. Přidat nového studenta");
        System.out.println("2. Zadání známky studentovi");
        System.out.println("3. Najít studenty podle příjmení");
        System.out.println("4. Vypsat všechny studenty");
        System.out.println("5. Vypsat průměry dle oborů");
        System.out.println("6. Vypsat počty studentů v oborech");
        System.out.println("7. Smazat studenta podle ID");
        System.out.println("8. Uložit studenty manuálně");
        System.out.println("9. Načíst studenty ze souboru/databáze");
        System.out.println("10. Spustit dovednost studenta");
        System.out.println("0. Ukončit program");
        System.out.print("Zadejte volbu: ");
    }

    private int nactiCislo() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void pridatStudenta() {
        System.out.print("Zadejte jméno: ");
        String jmeno = scanner.nextLine();
        System.out.print("Zadejte příjmení: ");
        String prijmeni = scanner.nextLine();
        System.out.print("Zadejte rok narození: ");
        int rok = nactiCislo();

        System.out.println("Zadejte obor: 1 - Kyberbezpečnost, 2 - Telekomunikace");
        int obor = nactiCislo();

        int id = generujId();
        Student s;
        if (obor == 1) {
            s = new StudentKyberbezpecnost(id, jmeno, prijmeni, rok);
        } else {
            s = new StudentTelekomunikace(id, jmeno, prijmeni, rok);
        }

        databaze.pridejStudenta(s);
        System.out.println("Student přidán s ID: " + s.getId());
    }

    private int generujId() {
        List<Student> vsichni = databaze.vratVsechnyStudenty();
        return vsichni.stream().mapToInt(Student::getId).max().orElse(0) + 1;
    }

    private void zadatZnamku() {
        System.out.print("Zadejte ID studenta: ");
        int id = nactiCislo();
        Student s = databaze.najdiStudenta(id);
        if (s == null) {
            System.out.println("Student nenalezen.");
            return;
        }
        System.out.print("Zadejte známku (1-5): ");
        int znamka = nactiCislo();
        if (znamka < 1 || znamka > 5) {
            System.out.println("Neplatná známka.");
            return;
        }
        s.pridejZnamku(znamka);
        System.out.println("Známka přidána.");
    }

    private void hledatPodlePrijmeni() {
        System.out.print("Zadejte příjmení: ");
        String prijmeni = scanner.nextLine();
        List<Student> nalezeni = databaze.najdiPodlePrijmeni(prijmeni);
        if (nalezeni.isEmpty()) {
            System.out.println("Žádní studenti s tímto příjmením.");
        } else {
            nalezeni.forEach(this::vypisStudenta);
        }
    }

    private void vypisVsechny() {
        List<Student> vsichni = databaze.seradPodlePrijmeni();
        vsichni.forEach(this::vypisStudenta);
    }

    private void vypisStudenta(Student s) {
        System.out.println("ID: " + s.getId() + ", Jméno: " + s.getJmeno() + " " + s.getPrijmeni()
                + ", Rok narození: " + s.getRokNarozeni() + ", Průměr: " + String.format("%.2f", s.getStudijniPrumer()));
        System.out.println("  Dovednost: " + s.vypisDovednost());
    }

    private void vypisPrumery() {
        double p1 = databaze.prumerOboru(StudentKyberbezpecnost.class);
        double p2 = databaze.prumerOboru(StudentTelekomunikace.class);
        System.out.println("Průměr Kyberbezpečnost: " + String.format("%.2f", p1));
        System.out.println("Průměr Telekomunikace: " + String.format("%.2f", p2));
    }

    private void vypisPocty() {
        long p1 = databaze.pocetStudentuOboru(StudentKyberbezpecnost.class);
        long p2 = databaze.pocetStudentuOboru(StudentTelekomunikace.class);
        System.out.println("Počet Kyberbezpečnost: " + p1);
        System.out.println("Počet Telekomunikace: " + p2);
    }

    private void smazatStudenta() {
        System.out.print("Zadejte ID studenta k odstranění: ");
        int id = nactiCislo();
        Student s = databaze.najdiStudenta(id);
        if (s == null) {
            System.out.println("Student s ID " + id + " nebyl nalezen.");
        } else {
            databaze.odeberStudenta(id);
            System.out.println("Student byl úspěšně odstraněn.");
        }
    }

    private void ulozitManualne() {
        uloziste.ulozStudenty(databaze.vratVsechnyStudenty());
        System.out.println("Data byla uložena.");
    }

    private void nacistZeSouboru() {
        databaze.nactiZDatabaze(uloziste);
        System.out.println("Data byla načtena.");
    }
}