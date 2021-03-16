
package controllers.student.smallLayout;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.Quiz;
import models.QuizResult;

public class AttempedQuizListItemFXML implements Initializable {

    @FXML
    private Label title;
    
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
    
}
