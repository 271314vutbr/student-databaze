package sql;

import studenti.Student;
import studenti.StudentKyberbezpecnost;
import studenti.StudentTelekomunikace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDatabaze {
    private static final String URL = "jdbc:sqlite:studenti.db";

    public void ulozStudenty(DatabazeStudentu databaze) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            vytvorTabulku(conn);

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM studenti");
            }

            String sql = "INSERT INTO studenti (id, jmeno, prijmeni, rok, prumer, obor) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (Student s : databaze.getVsechnyStudenty()) {
                    ps.setInt(1, s.getID());
                    ps.setString(2, s.getJmeno());
                    ps.setString(3, getPrijmeni());
                    ps.setInt(4, s.getRokNarozeni());
                    ps.setDouble(5, s.getStudijniPrumer());
                    ps.setString(6, s instanceof StudentTelekomunikace ? "telekomunikace" : "kyberbezpecnost");
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public List<Student> nactiStudenty() {
            List<Student> seznam = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(URL)) {
                vytvorTabulku(conn);
                String sql = "SELECT * FROM studenti";
                try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String jmeno = rs.getString("jmeno");
                        String prijmeni = rs.getString("prijmeni");
                        int rok = rs.getInt("rok"):
                        String obor = rs.getString("obor");

                        Student s;
                        if (obor.equals("telekomunikace")) {
                            s = new StudentTelekomunikace(id, jmeno, prijmeni, rok);
                        } else {
                            s = new StudentKyberbezpecnost(id, jmeno, prijmeni, rok);
                        }
                        seznam.add(s);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return seznam;
    }
    private void vytvorTabulku(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS studenti (
                id INTEGER PRIMARY KEY,
                jmeno TEXT,
                prijmeni TEXT,
                rok INTEGER,
                prumer DOUBLE,
                obor TEXT
                )""";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}
