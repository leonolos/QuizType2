package controllers.student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import models.Question;
import models.Quiz;
import org.controlsfx.control.Notifications;

public class QuestionsScreenFXMLController implements Initializable {
    
    private class QuestionsObservable {
        
        Property<String> question = new SimpleStringProperty();
        Property<String> option1 = new SimpleStringProperty();
        Property<String> option2 = new SimpleStringProperty();
        Property<String> option3 = new SimpleStringProperty();
        Property<String> option4 = new SimpleStringProperty();
        Property<String> answer = new SimpleStringProperty();
        
        public void setQuestion(Question question) {
            this.question.setValue(question.getQuestion());
            this.option1.setValue(question.getOption1());
            this.option2.setValue(question.getOption2());
            this.option3.setValue(question.getOption3());
            this.option4.setValue(question.getOption4());
            this.answer.setValue(question.getAnswer());
        }
    }
    
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
    @FXML
    private FlowPane progressPane;
    
    private Quiz quiz;
    private List<Question> questionList;
    private Question currentQuestion;
    int currentIndex = 0;
    private QuestionsObservable questionsObservable;
    
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
            renderProgress();
        }
    }
    
    private void renderProgress() {
        for (int i = 0; i < this.questionList.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass()
                            .getResource("/fxml/student/ProgressCircleFXML.fxml"));
            try {
                Node node = fxmlLoader.load();
                ProgressCircleFXMLController progressCircleFXMLController = fxmlLoader.getController();
                progressCircleFXMLController.setNumber(i + 1);
                progressPane.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.showNextQuestionButton();
        this.hideSubmitQuizButton();
        
        this.questionsObservable = new QuestionsObservable();
        bindFields();

//        for (int i = 0; i < 20; i++) {
//            FXMLLoader fxmlLoader = new FXMLLoader(
//                    getClass()
//                            .getResource("/fxml/student/ProgressCircleFXML.fxml"));
//            try {
//                Node node = fxmlLoader.load();
//                progressPane.getChildren().add(node);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
    
    private void bindFields() {
        this.question.textProperty().bind(this.questionsObservable.question);
        this.option4.textProperty().bind(this.questionsObservable.option4);
        this.option3.textProperty().bind(this.questionsObservable.option3);
        this.option2.textProperty().bind(this.questionsObservable.option2);
        this.option1.textProperty().bind(this.questionsObservable.option1);
        
    }
    
    @FXML
    private void nextQuestions(ActionEvent event) {
        this.setNextQuestion();
    }
    
    private void setNextQuestion() {
        if (!(currentIndex >= questionList.size())) {
            this.currentQuestion = this.questionList.get(currentIndex);
            List<String> options = new ArrayList<>();
            options.add(this.currentQuestion.getOption1());
            options.add(this.currentQuestion.getOption2());
            options.add(this.currentQuestion.getOption3());
            options.add(this.currentQuestion.getOption4());
            Collections.shuffle(options);
            
            this.currentQuestion.setOption1(options.get(0));
            this.currentQuestion.setOption2(options.get(1));
            this.currentQuestion.setOption3(options.get(2));
            this.currentQuestion.setOption4(options.get(3));

//            this.question.setText(this.currentQuestion.getQuestion());
//            this.option1.setText(options.get(0));
//            this.option2.setText(options.get(1));
//            this.option3.setText(options.get(2));
//            this.option4.setText(options.get(3));
            this.questionsObservable.setQuestion(this.currentQuestion);
            currentIndex++;
        } else {
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
