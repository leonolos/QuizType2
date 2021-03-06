package controllers.student;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import models.Question;
import models.Quiz;
import org.controlsfx.control.Notifications;

public class QuestionsScreenFXMLController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private Label time;
    @FXML
    private Label question;
    @FXML
    private RadioButton option1;
    @FXML
    private RadioButton option2;
    @FXML
    private RadioButton option3;
    @FXML
    private RadioButton option4;
    @FXML
    private Button next;
    @FXML
    private Button submit;
    @FXML
    private ToggleGroup options;

    private Quiz quiz;
    private List<Question> questionList;
    private Question currentQuestion;
    int currentIndex = 0;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.title.setText(this.quiz.getTitle());
        this.getData();
    }

    private void getData() {
        if (quiz != null) {
            this.questionList = quiz.getQuestions();
            Collections.shuffle(this.questionList);
            setNextQuestion();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.showNextQuestionButton();
        this.hideSubmitQuizButton();
    }

    @FXML
    private void nextQuestions(ActionEvent event) {
        this.setNextQuestion();
    }

    private void setNextQuestion() {
        if (!(currentIndex >= questionList.size())) {
            this.currentQuestion = this.questionList.get(currentIndex);
            
            List<String> options=new ArrayList<>();
            options.add(this.currentQuestion.getOption1());
            options.add(this.currentQuestion.getOption2());
            options.add(this.currentQuestion.getOption3());
            options.add(this.currentQuestion.getOption4());
            Collections.shuffle(options);
                    
            this.question.setText(this.currentQuestion.getQuestion());
            this.option1.setText(options.get(0));
            this.option1.setText(options.get(1));
            this.option1.setText(options.get(2));
            this.option1.setText(options.get(3));
            
//            this.option1.setText(this.currentQuestion.getOption1());
//            this.option2.setText(this.currentQuestion.getOption2());
//            this.option3.setText(this.currentQuestion.getOption3());
//            this.option4.setText(this.currentQuestion.getOption4());
            currentIndex++;
        } else {
//            Notifications.create()
//                    .title("Pas de question")
//                    .text("Il n'y a plus de question")
//                    .showInformation();
            hideNextQuestionButton();
            showSubmitQuizButton();
        }
    }

    private void hideNextQuestionButton() {
        this.next.setVisible(false);
    }

    private void showNextQuestionButton() {
        this.next.setVisible(true);
    }

    private void hideSubmitQuizButton() {
        this.submit.setVisible(false);
    }

    private void showSubmitQuizButton() {
        this.submit.setVisible(true);
    }

    @FXML
    private void submit(ActionEvent event) {
    }

}
