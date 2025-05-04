package studenti;

public class StudentTelekomunikace extends Student {

    public StudentTelekomunikace(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public String vypisDovednost() {
        String vstup = jmeno + " " + prijmeni;
        StringBuilder morse = new StringBuilder();
        for (char c : vstup.toUpperCase().toCharArray()) {
            morse.append(morseKod(c)).append(" ");
        }
        return morse.toString();
    }

    private String morseKod(char c) {
        switch (c) {
            case 'A': return ".-";
            case 'B': return "-...";
            case 'C': return "-.-.";
            case 'D': return "-..";
            case 'E': return ".";
            case 'F': return "..-.";
            case 'G': return "--.";
            case 'H': return "....";
            case 'I': return "..";
            case 'J': return ".---";
            case 'K': return "-.-";
            case 'L': return ".-..";
            case 'M': return "--";
            case 'N': return "-.";
            case 'O': return "---";
            case 'P': return ".--.";
            case 'Q': return "--.-";
            case 'R': return ".-.";
            case 'S': return "...";
            case 'T': return "-";
            case 'U': return "..-";
            case 'V': return "...-";
            case 'W': return ".--";
            case 'X': return "-..-";
            case 'Y': return "-.--";
            case 'Z': return "--..";
            case ' ': return "/";
            default: return "?";
        }
    }
}
