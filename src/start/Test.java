package start;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import models.Quiz;
import models.QuizResult;
import models.Student;

public class Test {

    public static void main(String[] args) throws Exception {
//        Student student = new Student();
//        student.setId(2);
//        System.out.println(QuizResult.getQuizzes(student));
//        
//        student.setId(1);
//        System.out.println(QuizResult.getQuizzes(student));
        QuizResult quizResult = new QuizResult();
        quizResult.setId(5);
        System.out.println(quizResult.getNumberOfAttempedQuestions());
    }
}
