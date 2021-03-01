/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Question;
import models.Quiz;

/**
 *
 * @author RSoloN
 */
public class StartApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/quiztype/AdminLogin.fxml"));
        createTables();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/AdminHomeScreenFXML.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Quiz.createTable();
    }

    private void createTables() {
        Quiz.createTable();
        Question.createTable();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
