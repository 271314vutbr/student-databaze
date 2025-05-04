import databaze.DatabazeStudentu;
import databaze.IDatabaze;
import soubory.SpravceSouboru;
// import sql.SQLDatabaze; // odkomentuj pro SQL režim
import ui.KonzoloveMenu;

public class Main {
    public static void main(String[] args) {
        DatabazeStudentu databazeStudentu = new DatabazeStudentu();

        // Přepínej zde mezi úložišti:
        IDatabaze uloziste = new SpravceSouboru();
        // IDatabaze uloziste = new SQLDatabaze();

        databazeStudentu.nactiZDatabaze(uloziste);

        KonzoloveMenu menu = new KonzoloveMenu(databazeStudentu, uloziste);
        menu.spust();

        uloziste.ulozStudenty(databazeStudentu.vratVsechnyStudenty());
    }
}
