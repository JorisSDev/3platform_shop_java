package com.kursinis.kursinis_main.fxControllers;

import com.kursinis.kursinis_main.hibernateControllers.UserHib;
import com.kursinis.kursinis_main.model.Customer;
import com.kursinis.kursinis_main.model.Manager;
import com.kursinis.kursinis_main.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public RadioButton customerCheckbox;
    @FXML
    public ToggleGroup userType;
    @FXML
    public RadioButton managerCheckbox;
    @FXML
    public TextField addressField;
    @FXML
    public TextField cardNoField;
    @FXML
    public DatePicker birthDateField;
    @FXML
    public TextField employeeIdField;
    @FXML
    public TextField medCertificateField;
    @FXML
    public DatePicker employmentDateField;
    @FXML
    public CheckBox isAdminCheck;


    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;
    private Boolean isAdmin = false;

    public void setData(EntityManagerFactory entityManagerFactory, boolean isAdmin) {
        this.entityManagerFactory = entityManagerFactory;
        this.isAdmin = isAdmin;
        disableFields();
    }

    private void disableFields() {
        if (!isAdmin) {
            employeeIdField.setVisible(false);
            medCertificateField.setVisible(false);
            employmentDateField.setVisible(false);
            isAdminCheck.setVisible(false);
            managerCheckbox.setVisible(false);
        }
    }

    public void createUser() {
        userHib = new UserHib(entityManagerFactory);
        if (customerCheckbox.isSelected()) {
            Customer customer = new Customer(loginField.getText(), passwordField.getText(), birthDateField.getValue(), nameField.getText(), surnameField.getText(), addressField.getText(), cardNoField.getText());
            userHib.createUser(customer);
        } else if (managerCheckbox.isSelected()) {
            Manager manager = new Manager(loginField.getText(), passwordField.getText(), birthDateField.getValue(), nameField.getText(), surnameField.getText(), employeeIdField.getText(), medCertificateField.getText(), employmentDateField.getValue(), isAdminCheck.isSelected());
            userHib.createUser(manager);
        }
        closeForm();
    }

    public void closeForm() {
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.close();
    }

}
