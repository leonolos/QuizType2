/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.util.ArrayList;
import models.Question;
import models.Quiz;

/**
 *
 * @author RSoloN
 */
public class Test {
    public static void main(String[] args) {
        Quiz quiz = new Quiz("JAVA Quiz");
        Question question = new Question(quiz, "1+3=?", "4", "5", "10", "45", "4");
        
        ArrayList<Question> arr = new ArrayList<>();
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        question.setQuiz(quiz);
        quiz.save(arr);
    }
}
