package controllers.student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

public class QuizListController implements Initializable {

    @FXML
    private FlowPane quizListContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        for (int i = 0; i < 7; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/student/QuizCardLayoutFXML.fxml"));

            try {
                Node node = fxmlLoader.load();
                quizListContainer.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
