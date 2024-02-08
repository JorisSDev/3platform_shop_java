package com.kursinis.kursinis_main.fxControllers;

import com.kursinis.kursinis_main.StartGui;
import com.kursinis.kursinis_main.hibernateControllers.UserHib;
import com.kursinis.kursinis_main.model.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;

    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;


    public void registerNewUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("registration.fxml"));
        Parent parent = fxmlLoader.load();

        RegistrationController registrationController = fxmlLoader.getController();
        registrationController.setData(entityManagerFactory, false);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("REGISTER");
        stage.setScene(scene);
        stage.setOnHidden(event -> {

        });
        stage.show();
    }

    public void validateAndConnect() throws IOException {
        userHib = new UserHib(entityManagerFactory);
        User user = userHib.getUserByCredentials(loginField.getText(), passwordField.getText());
        //Cia galim optimizuoti, kol kas paliksiu kaip pvz su userHib
        if (user != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("main-shop.fxml"));
            Parent parent = fxmlLoader.load();
            MainShopController mainShopController = fxmlLoader.getController();
            mainShopController.setData(entityManagerFactory, user);
            Scene scene = new Scene(parent);
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setTitle("Shop");
            stage.setScene(scene);
            stage.show();
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Login INFO", "Wrong data", "Please check credentials, no such user");
        }
    }

    public void loadNewlyRegistered() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    }
}
