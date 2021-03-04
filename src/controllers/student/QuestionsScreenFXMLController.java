package controllers.student;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void nextQuestion(ActionEvent event) {
    }

    @FXML
    private void submit(ActionEvent event) {
    }

}
