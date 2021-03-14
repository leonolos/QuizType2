package controllers.student;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import models.Question;
import models.Quiz;

public class QuizResultFXMLController implements Initializable {

    @FXML
    private PieChart attempedChart;
    @FXML
    private PieChart scoreChart;
    @FXML
    private VBox questionsContainer;

    //Not FXML Variable
    private Map<Question, String> userAnswers;
    private Integer numberOfRightAnswers = 0;
    private Quiz quiz;
    private List<Question> questionList;
    private Integer notAttemped = 0;
    private Integer attemped = 0;

    public void setValues(Map<Question, String> userAnswers,
            Integer numberOfRightAnswers, Quiz quiz,
            List<Question> questionList) {
        this.userAnswers = userAnswers;
        this.numberOfRightAnswers = numberOfRightAnswers;
        this.quiz = quiz;
        this.questionList = questionList;

        this.attemped = this.userAnswers.keySet().size();
        this.notAttemped = this.questionList.size() - attemped;

        setValuesToChart();
    }

    private void setValuesToChart() {
        ObservableList<PieChart.Data> attempedData = this.attempedChart.getData();
        attempedData.add(new PieChart.Data(String.format("Attemped (%d)", this.attemped), this.attemped));
        attempedData.add(new PieChart.Data(String.format("Not Attemped (%d)", this.notAttemped), this.notAttemped));

        ObservableList<PieChart.Data> scoreChartDatata = this.scoreChart.getData();
        scoreChartDatata.add(new PieChart.Data(
                String.format("Right Answers (%d)", this.numberOfRightAnswers), this.numberOfRightAnswers));
        scoreChartDatata.add(new PieChart.Data(
                String.format("Wrong Answers (%d)", this.attemped - this.numberOfRightAnswers), this.attemped - this.numberOfRightAnswers));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        for (int i = 0; i < 6; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/student/QuizResultSingleQuestionFXML.fxml"));

            try {
                Node node = fxmlLoader.load();
                QuizResultSingleQuestionFXMLController controller = fxmlLoader.getController();
                questionsContainer.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
