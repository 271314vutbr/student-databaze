package studenti;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StudentKyberbezpecnost extends Student {
    public StudentKyberbezpecnost(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }


    @Override
    public String vypisDovednost() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String input = jmeno + prijmeni;
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "Chyba při hashování";
        }
    }
}
