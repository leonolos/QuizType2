
package controllers.student.smallLayout;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.Quiz;
import models.QuizResult;

public class AttempedQuizListItemFXML implements Initializable {

    @FXML
    private Label title;    
    @FXML
    private VBox item;
    
    private Quiz quiz;
    private QuizResult quizResult;    

    public void setData(Quiz quiz , QuizResult quizResult) {
        this.quiz = quiz;
        this.quizResult = quizResult;
        this.title.setText(this.quiz.getTitle());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loadData(MouseEvent event) {
        System.out.println("Item clicked...");
        Integer numberOfAttempedQuestions =quizResult.getNumberOfAttempedQuestions();
        Integer numberOfQuestions = quiz.getNumberOfQuestions();
        System.out.println(numberOfAttempedQuestions);
        System.out.println(numberOfQuestions);
    }
    
}
