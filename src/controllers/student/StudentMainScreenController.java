/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author RSoloN
 */
public class StudentMainScreenController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private StackPane stackPanel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        addQuizListScreen();
    }

    private void addQuizListScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                getResource("/fxml/student/QuizList.fxml"));

        try {
            Node node = fxmlLoader.load();
            stackPanel.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
