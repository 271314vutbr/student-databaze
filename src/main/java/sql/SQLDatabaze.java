package sql;

import databaze.IDatabaze;
import studenti.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDatabaze implements IDatabaze {
    private final String URL = "jdbc:sqlite:studenti.db";

    public SQLDatabaze() {
        vytvorTabulku();
    }

    private void vytvorTabulku() {
        String sql = "CREATE TABLE IF NOT EXISTS studenti ("
                + "id INTEGER PRIMARY KEY,"
                + "jmeno TEXT,"
                + "prijmeni TEXT,"
                + "rokNarozeni INTEGER,"
                + "znamky TEXT,"
                + "typ TEXT"
                + ");";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Chyba při vytváření tabulky: " + e.getMessage());
        }
    }

    @Override
    public List<Student> nactiStudenty() {
        List<Student> studenti = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM studenti")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String jmeno = rs.getString("jmeno");
                String prijmeni = rs.getString("prijmeni");
                int rok = rs.getInt("rokNarozeni");
                String typ = rs.getString("typ");
                String znamkyText = rs.getString("znamky");
                List<Integer> znamky = new ArrayList<>();
                for (String s : znamkyText.split(",")) {
                    if (!s.isEmpty()) znamky.add(Integer.parseInt(s));
                }

                Student s;
                if ("kyber".equals(typ)) {
                    s = new studenti.StudentKyberbezpecnost(id, jmeno, prijmeni, rok);
                } else {
                    s = new studenti.StudentTelekomunikace(id, jmeno, prijmeni, rok);
                }
                for (int z : znamky) s.pridejZnamku(z);
                studenti.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Chyba při načítání studentů: " + e.getMessage());
        }
        return studenti;
    }

    @Override
    public void ulozStudenty(List<Student> studenti) {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM studenti");

            String sql = "INSERT INTO studenti (id, jmeno, prijmeni, rokNarozeni, znamky, typ) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Student s : studenti) {
                    pstmt.setInt(1, s.getId());
                    pstmt.setString(2, s.getJmeno());
                    pstmt.setString(3, s.getPrijmeni());
                    pstmt.setInt(4, s.getRokNarozeni());
                    pstmt.setString(5, s.getZnamky().toString().replaceAll("[\\[\\]\\s]", ""));
                    pstmt.setString(6, (s instanceof studenti.StudentKyberbezpecnost) ? "kyber" : "telekom");
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

        } catch (SQLException e) {
            System.out.println("Chyba při ukládání studentů: " + e.getMessage());
        }
    }
}
