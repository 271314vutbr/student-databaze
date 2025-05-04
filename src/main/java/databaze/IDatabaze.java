package databaze;

import studenti.Student;
import java.util.List;

public interface IDatabaze {
    List<Student> nactiStudenty();
    void ulozStudenty(List<Student> studenti);
}
