package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Student;
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
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> studentIdColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, String> mobilNumberColumn;
    @FXML
    private TableColumn<Student, Character> genderColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, String> passwordColumn;
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
        renderTable();
    }

    private void renderTable() {
        List<Student> students = Student.getAll();
        studentTable.getItems().clear();
        
        this.studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.mobilNumberColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        
        studentTable.getItems().addAll(students);
    }

    private void radioButtonSetup() {
        this.male.setSelected(true);
        this.male.setToggleGroup(toggleGroup);
        this.female.setToggleGroup(toggleGroup);
    }

    private void initAll() {
        toggleGroup = new ToggleGroup();
    }

    private void resetForm() {
        this.password.clear();
        this.email.clear();
        this.firstName.clear();
        this.lastName.clear();
        this.mobilNumber.clear();
    }

    private String validate(Student student) {
        String message = null;
//        Pattern p = Pattern.compile("^(\\w+([_.]{1}\\w+)*@\\w+([_.]{1}\\w+)*\\.[A-Za-z]{2,3}[;]?)*$");

        if (student.getFirstName().length() < 4) {
            message = "Le prénom doit avoir plus de 4 caractères";
        } else if (student.getLastName().length() < 2) {
            message = "Le nom doit avoir plus de 4 caractères";
//        } else if (!p.matcher("email").matches()) {
//            message = "Entrez un Email valide s'il vous plaît";
        } else if (student.getPassword().length() <= 6) {
            message = "Le mot de passe doit être plus de 6 caractères";
        } else if (student.getMobile().length() < 10) {
            message = "Entrez un numéro de contact valide";
        }
        return message;
    }

    @FXML
    public void saveStudent(ActionEvent event) {
        System.out.println("Button Clicked...");

        String firstName = this.firstName.getText().trim();
        String lastName = this.lastName.getText().trim();
        String mobile = this.mobilNumber.getText().trim();
        String email = this.email.getText().trim();
        String password = this.password.getText().trim();
        Character gen = 'M';
        RadioButton gender = (RadioButton) toggleGroup.getSelectedToggle();
        if (gender != null) {
            if (gender == female) {
                gen = 'F';
            }
        }

        String message = null;

        Student s = new Student(
                firstName,
                lastName,
                mobile,
                gen,
                email,
                password);
        message = this.validate(s);

        if (message != null) {
            Notifications.create()
                    .title("S'il vous plaît, remplir correctement le formulaire")
                    .text(message)
                    .position(Pos.CENTER)
                    .showWarning();
            return;
        }

        System.out.println(s);

        if (s.isExists()) {
            Notifications.create()
                    .title("Enregistrement échoué")
                    .text("Cet étudiant existe déjà!")
                    .position(Pos.CENTER)
                    .showError();
            return;
        }
        s = s.save();

        if (s != null) {
            Notifications.create()
                    .text("Etudiant inscrit...")
                    .title("Succès")
                    .position(Pos.CENTER)
                    .showInformation();
            this.resetForm();
            
            studentTable.getItems().add(0, s);
        } else {
            Notifications.create()
                    .text("Inscription échoué...")
                    .title("Echoué")
                    .position(Pos.CENTER)
                    .showError();
        }

    }

}
