package controllers.student;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class QuizCardLayoutFXMLController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private Label noq;
    @FXML
    private Button startButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void startQuiz(ActionEvent event) {
    }
    
}
