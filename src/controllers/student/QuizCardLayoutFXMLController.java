package controllers.student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import listeners.NewScreenListener;
import models.Quiz;

public class QuizCardLayoutFXMLController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private Label noq;
    @FXML
    private Button startButton;

    private Quiz quiz;

    private NewScreenListener screenListener;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.title.setText(this.quiz.getTitle());
    }

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setNoq(String value) {
        this.noq.setText(value);
    }

    @FXML
    private void startQuiz(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                getResource("/fxml/student/QuestionsScreenFXML.fxml"));

        try {
            Node node = fxmlLoader.load();
            QuestionsScreenFXMLController questionsScreenFXMLController = fxmlLoader.getController();
            questionsScreenFXMLController.setQuiz(this.quiz);
            this.screenListener.changeScreen(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
