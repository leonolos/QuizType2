package quiztype;

import java.util.regex.Pattern;
import static javafx.scene.input.KeyCode.A;
import static jdk.nashorn.tools.ShellFunctions.input;
import models.Student;

public class Test {

    public static void main(String[] args) {
        System.out.println(Student.getAll());
        System.out.println(Student.getAll().size());
    
    }

}
