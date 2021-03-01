package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;
import models.Question;
import models.Quiz;
import org.controlsfx.control.Notifications;

public class AddQuizFXMLController implements Initializable {

    @FXML
    private TextField quizTitle;
    @FXML
    private TextArea question;
    @FXML
    private TextField option1;
    @FXML
    private TextField option2;
    @FXML
    private TextField option3;
    @FXML
    private TextField option4;
    @FXML
    private Button addNextQuestion;
    @FXML
    private Button submitQuiz;
    @FXML
    private RadioButton option1radio;
    @FXML
    private RadioButton option2radio;
    @FXML
    private RadioButton option3radio;
    @FXML
    private RadioButton option4radio;

    private ToggleGroup radioGroup;
    @FXML
    private Button setQuizTitleButton;

    //my variable
    private Quiz quiz = null;
    private ArrayList<Question> questions = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        radioButtonSetup();
    }

    private void radioButtonSetup() {
        radioGroup = new ToggleGroup();
        option1radio.setToggleGroup(radioGroup);
        option2radio.setToggleGroup(radioGroup);
        option3radio.setToggleGroup(radioGroup);
        option4radio.setToggleGroup(radioGroup);
    }

    @FXML
    private void setQuizTitle(ActionEvent event) {
        System.out.println("On a cliqué sur le bouton Enregistrer le titre");
        String title = quizTitle.getText();
        if (title.trim().isEmpty()) {
            Notifications.create()
                    .darkStyle()
                    .position(Pos.CENTER)
                    .title("Titre du Quiz")
                    .text("Ecrire le titre du Quiz s'il vous plaît")
                    .hideAfter(Duration.millis(3000))
                    .showError();
//            System.out.println("Ecrire un titre");
        } else {
            quizTitle.setEditable(false);
            System.out.println("Save Title...");
            this.quiz = new Quiz(title);
        }
    }

    private boolean validateFields() {
        if (quiz == null) {
            Notifications.create()
                    .darkStyle()
                    .title("Quiz")
                    .text("Entrez le titre de la Quiz s'il vous plaît")
                    .position(Pos.CENTER)
                    .hideAfter(Duration.millis(3000))
                    .showError();
            return false;
        }

        String qu = this.question.getText();
        String op1 = this.option1.getText();
        String op2 = this.option2.getText();
        String op3 = this.option3.getText();
        String op4 = this.option4.getText();

        Toggle selectedRadio = radioGroup.getSelectedToggle();

        System.out.println(selectedRadio);

        if (qu.trim().isEmpty()
                || op1.trim().isEmpty()
                || op2.trim().isEmpty()
                || op3.trim().isEmpty()
                || op4.trim().isEmpty()) {
            Notifications.create()
                    .darkStyle()
                    .text("S'il vous plaît, remplir les champs de..."
                            + "\nQuestion"
                            + "\nOption1"
                            + "\nOption2"
                            + "\nOption3"
                            + "\nOption4")
                    .title("Question et option")
                    .position(Pos.CENTER)
                    .hideAfter(Duration.millis(3000))
                    .showError();
            return false;
        } else {
            if (selectedRadio == null) {
                Notifications.create()
                        .darkStyle()
                        .text("S'il vous plaît, sélectionnez une réponse")
                        .title("Question et option")
                        .position(Pos.CENTER)
                        .hideAfter(Duration.millis(3000))
                        .showError();
                return false;
            } else {
                return true;//save Question and add next              
            }
        }
    }

    @FXML
    private void addNextQuestion(ActionEvent event) {
        addQuestions();
    }

    private boolean addQuestions() {
        boolean valid = validateFields();
        Question question = new Question();
        if (valid) {
            //Save
            question.setOption1(option1.getText().trim());
            question.setOption2(option2.getText().trim());
            question.setOption3(option3.getText().trim());
            question.setOption4(option4.getText().trim());
            Toggle selected = radioGroup.getSelectedToggle();
            String ans = null;
            if (selected == option1radio) {
                ans = option1.getText().trim();
            } else if (selected == option2radio) {
                ans = option2.getText().trim();
            } else if (selected == option3radio) {
                ans = option3.getText().trim();
            } else if (selected == option4radio) {
                ans = option4.getText().trim();
            }
            question.setAnswer(ans);
            question.setQuestion(this.question.getText().trim());

            this.question.clear();
            option1.clear();
            option2.clear();
            option3.clear();
            option4.clear();
            questions.add(question);
            question.setQuiz(quiz);
            System.out.println("Save question...");
            System.out.println(questions);
            System.out.println(quiz);
        }
        return valid;
    }

    @FXML
    private void submitQuiz(ActionEvent event) {
        boolean flag = addQuestions();
        if (flag) {
            flag = quiz.save(questions);
            if (flag) {
                //success
                Notifications.create()
                        .darkStyle()
                        .title("Success")
                        .text("Quiz Successfully Saved...")
                        .position(Pos.CENTER)
                        .hideAfter(Duration.millis(3000))
                        .showInformation();
            } else {
                //error
                Notifications.create()
                        .darkStyle()
                        .title("Fail...")
                        .text("Cant Save Quiz... Try Again...")
                        .position(Pos.CENTER)
                        .hideAfter(Duration.millis(3000))
                        .showError();
            }
        }
    }

}
