package controllers.student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import models.Question;
import models.Quiz;
import models.QuizResult;
import models.Student;
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

    //NO FXML Fields
    private Quiz quiz;
    private List<Question> questionList;
    private Question currentQuestion;
    int currentIndex = 0;
    private QuestionsObservable questionsObservable;
    private Map<Question, String> studentAnswers = new HashMap<>();
    private Integer numberOfRightAnswers = 0;
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }   

    //timer fields
    private long min, sec, hr, totalSec = 0;

    //METHODS AND CONSTRUCTOR
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.title.setText(this.quiz.getTitle());
        this.getData();
    }

    private String format(long value) {
        if (value < 10) {
            return 0 + "" + value;
        }
        return value + "";
    }

    public void convertTime() {
        min = TimeUnit.SECONDS.toMinutes(totalSec);
        sec = totalSec - (min * 60);
        hr = TimeUnit.MINUTES.toHours(min);
        min = min - (hr * 60);
        time.setText(format(hr) + ":" + format(min) + ":" + format(sec));

        totalSec--;
    }

    private void setTimer() {
        totalSec = this.questionList.size() * 30;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("After 1 sec...");
                        convertTime();
                        if (totalSec <= -1) {
                            timer.cancel();
                            time.setText("00:00:00");
                            //saving data to database
                            submit(null);
                            Notifications.create()
                                    .title("Erreur")
                                    .text("Temps écoulé...")
                                    .position(Pos.CENTER)
                                    .showError();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void getData() {
        if (quiz != null) {
            this.questionList = quiz.getQuestions();
            Collections.shuffle(this.questionList);
            renderProgress();
            setNextQuestion();
            setTimer();
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
                progressCircleFXMLController.setDefaultColor();
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

        this.option1.setSelected(true);
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
        boolean isRight = false;
        {
            // Checking answer
            RadioButton selectedButton = (RadioButton) options.getSelectedToggle();
            String userAnswer = selectedButton.getText();
            String rightAnswer = this.currentQuestion.getAnswer();
            if (userAnswer.trim().equalsIgnoreCase(rightAnswer.trim())) {
                isRight = true;
                this.numberOfRightAnswers++;
            }

            //saving Answer to hashMap
            studentAnswers.put(this.currentQuestion, userAnswer);
        }
        Node circleNode = this.progressPane.getChildren().get(currentIndex - 1);
        ProgressCircleFXMLController controller = (ProgressCircleFXMLController) circleNode.getUserData();

        if (isRight) {
            controller.setRightAnsweredColor();
        } else {
            controller.setWrongAnsweredColor();
        }
        this.setNextQuestion();
    }

    private void setNextQuestion() {
        if (!(currentIndex >= questionList.size())) {

            {
                //Changing the color
                Node circleNode = this.progressPane.getChildren().get(currentIndex);
                ProgressCircleFXMLController controller = (ProgressCircleFXMLController) circleNode.getUserData();
                controller.setCurrentQuestionColor();
            }

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
        
        
        System.out.println(this.studentAnswers);
        System.out.println(this.student);
//        Student student = new Student();
//        student.setId(1);
        QuizResult quizResult = new QuizResult(this.quiz, student, numberOfRightAnswers);
        boolean result = quizResult.save(this.studentAnswers);
        if (result) {
            Notifications.create()
                    .title("Message")
                    .text("Vous avez réussi le quiz...")
                    .position(Pos.CENTER)
                    .showInformation();
        } else {
            Notifications.create()
                    .title("Erreur")
                    .text("Quelque chose s'est mal passé...")
                    .position(Pos.CENTER)
                    .showError();
        }
    }

}
