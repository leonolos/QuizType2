package controllers.student;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class ProgressCircleFXMLController implements Initializable {
    
    @FXML
    private Circle circle;
    @FXML
    private Label number;
    
    public void setNumber(Integer number) {
        this.number.setText(number.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
