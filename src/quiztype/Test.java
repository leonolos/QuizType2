package quiztype;

import models.Quiz;

public class Test {

    public static void main(String[] args) throws Exception {
        Quiz quiz=new Quiz();
        quiz.setQuizId(2);
        System.out.println(quiz.getQuestions());
    }

}
