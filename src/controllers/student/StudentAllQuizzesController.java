package controllers.student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import listeners.NewScreenListener;
import models.Student;

public class StudentAllQuizzesController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private StackPane stackPanel;

    private Student student;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
//        addQuizListScreen();
    }

    public void setStudent(Student student) {
        this.student = student;
        addQuizListScreen();
    }

    private void addScreenToStackPane(Node node) {
        this.stackPanel.getChildren().add(node);
    }

    private void addQuizListScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                getResource("/fxml/student/QuizList.fxml"));

        try {
            Node node = fxmlLoader.load();
            QuizListController quizListController = fxmlLoader.getController();
            quizListController.setStudent(this.student);
            quizListController.setScreenListener(new NewScreenListener() {
                @Override
                public void changeScreen(Node node) {
                    addScreenToStackPane(node);
                }

                @Override
                public void removeTopScreen() {
                    stackPanel.getChildren().remove(stackPanel.getChildren().size() - 1);
                }

                @Override
                public void handle(Event event) {

                }
            });
            quizListController.setCards();
            stackPanel.getChildren().add(node);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void back(ActionEvent event) {
        ObservableList<Node> nodes = this.stackPanel.getChildren();
        if (nodes.size() == 1) {
            return;
        }
        this.stackPanel.getChildren().remove(nodes.size() - 1);
    }
}
