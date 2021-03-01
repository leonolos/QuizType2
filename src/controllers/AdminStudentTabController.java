package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.controlsfx.control.Notifications;

public class AdminStudentTabController implements Initializable {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField mobilNumber;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private Button saveButton;
    @FXML
    private TableView<?> studentTable;
    @FXML
    private TableColumn<?, ?> studentIdColumn;
    @FXML
    private TableColumn<?, ?> firstNameColumn;
    @FXML
    private TableColumn<?, ?> lastNameColumn;
    @FXML
    private TableColumn<?, ?> mobilNumberColumn;
    @FXML
    private TableColumn<?, ?> genderColumn;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

//No FXML Variables
    private ToggleGroup toggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initAll();
        radioButtonSetup();
    }

    private void radioButtonSetup() {
        this.male.setSelected(true);
        this.male.setToggleGroup(toggleGroup);
        this.female.setToggleGroup(toggleGroup);
    }

    private void initAll() {
        toggleGroup = new ToggleGroup();
    }

    @FXML
    private void saveStudent(ActionEvent event) {
        System.out.println("Button Clicked...");

        String firstName = this.firstName.getText().trim();
        String lastName = this.lastName.getText().trim();
        String mobile = this.mobilNumber.getText().trim();
        String email = this.email.getText().trim();
        String password = this.password.getText().trim();
        String stringGender = "Male";
        RadioButton gender = (RadioButton) toggleGroup.getSelectedToggle();
        if (gender != null) {
            if (gender == female) {
                stringGender = "Female";
            }
        }

        String message = null;
          Pattern p = Pattern.compile("^(\\w+([_.]{1}\\w+)*@\\w+([_.]{1}\\w+)*\\.[A-Za-z]{2,3}[;]?)*$");
       
        if (firstName.length() < 4) {
            message = "Le prénom doit avoir plus de 4 caractères";
        } else if (lastName.length() < 2) {
            message = "Le nom doit avoir plus de 4 caractères";
        } else if (!p.matcher("email").matches()) {
            message = "Entrez un Email valide s'il vous plaît";
        } else if (password.length() <= 6) {
            message = "Le mot de passe doit être plus de 6 caractères";
        } else if (mobile.length() < 10) {
            message = "Entrez un numéro de contact valide";
        }

        if (message != null) {
            Notifications.create()
                    .title("S'il vous plaît, remplir correctement le formulaire")
                    .text(message)
                    .showError();
            return;
        }

        //save code will be here
        System.out.println(firstName + "\n"
                + lastName + "\n"
                + mobile + "\n"
                + stringGender);
    }

}
