import databaze.DatabazeStudentu;
import databaze.IDatabaze;
import sql.SQLDatabaze;
import soubory.SpravceSouboru;
import ui.KonzoloveMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabazeStudentu databazeStudentu = new DatabazeStudentu();

        Scanner scanner = new Scanner(System.in);
        IDatabaze uloziste;

        System.out.println("Zvolte režim práce:");
        System.out.println("1 - Souborové uložiště");
        System.out.println("2 - SQL databáze");
        System.out.print("Vaše volba: ");
        String volba = scanner.nextLine();

        if (volba.equals("1")) {
            uloziste = new SpravceSouboru();
            System.out.println("Zvolen režim: souborové uložiště.");
        } else {
            uloziste = new SQLDatabaze();
            System.out.println("Zvolen režim: SQL databáze.");
        }

        databazeStudentu.nactiZDatabaze(uloziste);

        KonzoloveMenu menu = new KonzoloveMenu(databazeStudentu, uloziste);
        menu.spust();

        uloziste.ulozStudenty(databazeStudentu.vratVsechnyStudenty());
    }
}
