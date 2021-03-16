package controllers.student;

import controllers.student.smallLayout.AttempedQuizListItemFXML;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.Quiz;
import models.QuizResult;
import models.Student;

public class AttempedQuizListScreenFXMLController implements Initializable {

    @FXML
    private VBox list;    
    @FXML
    private Label total;
    @FXML
    private Label Aq;
    @FXML
    private Label ra;
    @FXML
    private Label wa;
    @FXML
    private PieChart attempedChart;
    @FXML
    private PieChart rightWrongChart;

    private Student student;
    
    public void setStudent(Student student) {
        this.student = student;
        
        setList();
    }

    private void setList() {
        Map<QuizResult, Quiz> map = QuizResult.getQuizzes(student);
        Set<QuizResult> quizzesData = map.keySet();

        for (QuizResult quizResult : quizzesData) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/student/smallLayout/AttempedQuizListItemFXML.fxml"));
            try {
                Parent root = fxmlLoader.load();
                AttempedQuizListItemFXML attempedQuizListItemFXML = fxmlLoader.getController();
                attempedQuizListItemFXML.setData(map.get(quizResult) , quizResult);
                this.list.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

}
