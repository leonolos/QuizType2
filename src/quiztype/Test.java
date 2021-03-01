package quiztype;

import java.util.regex.Pattern;
import static javafx.scene.input.KeyCode.A;
import static jdk.nashorn.tools.ShellFunctions.input;
import models.Student;

public class Test {

    public static void main(String[] args) {
        //Créer la table
//        Student.createTable();
        // Insérer des données dans la table
//new Student("Solo2","RAKOTO2","0767048418",'M',"solo@gmail.com","123456").save();
//new Student("Solo3","RAKOTO3","0767048418",'M',"solo@gmail.com","123456").save();
//        // Test Validation mail 
//        Pattern p = Pattern.compile("^(\\w+([_.]{1}\\w+)*@\\w+([_.]{1}\\w+)*\\.[A-Za-z]{2,3}[;]?)*$");
////        p.matcher("solonoel@gmail.com").matches();
//        System.out.println(p.matcher("solo.rakotomalala@yahoo.com").matches());
//        System.out.println(p.matcher("solo.rakotomalalayahoo.com").matches());
//        System.out.println(p.matcher("solo.rakotomalala@yahoo.com").matches());
//        System.out.println(p.matcher("solo.rakotomalala@yahoo.com").matches());
    boolean b=new Student("Solo2","RAKOTO2","0767048418",'M',"solo@gmail.com","123456").isExists();
    System.out.println(b);
    
    boolean d=new Student("LeSolo","LeRAKOTO","0767048418",'M',"Lesolo@gmail.com","123456").isExists();
    System.out.println(d);
    
    }

}
