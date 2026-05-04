package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.DAOs.UserDAO;
import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.services.UserService;
import com.budgetwise.budgetwise.utils.AlertUtil;
import com.budgetwise.budgetwise.utils.Validation;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AuthController {

    @FXML private TextField nameField;
    @FXML private TextField EmailField;
    @FXML private TextField PasswordField;

    @FXML private TextField emailField;
    @FXML private TextField passwordField;

    private UserService userService;
    private AlertUtil alertUtil;
    private Validation validation;

    @FXML
    public void initialize() {
        userService = new UserService(new UserDAO());
        alertUtil = new AlertUtil();
        validation = new Validation();
    }

    @FXML
    public void handleRegister() {

        try {
            String name = nameField.getText();
            String email = EmailField.getText();
            String password = PasswordField.getText();

            if (!validation.validateRigester(name, email, password)) return;

            userService.register(name, email, password);

            alertUtil.showSuccess("Account Created Successfully");

        } catch (Exception e) {
            alertUtil.showError(e.getMessage());
        }
    }

    @FXML
    public void handleLogin() {

        try {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (!validation.validateLogin(email, password)) return;

            User user = userService.login(email, password);

            alertUtil.showSuccess("Welcome " + user.getName());

        } catch (Exception e) {
            alertUtil.showError(e.getMessage());
        }
    }
    public void goToLogin() {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource("/fxml/LoginView.fxml")
            );

            Scene scene = nameField.getScene();

            root.translateXProperty().set(scene.getWidth());

            AnchorPane parent = (AnchorPane) scene.getRoot();
            parent.getChildren().add(root);

            TranslateTransition tt = new TranslateTransition(
                    Duration.seconds(0.7),
                    root
            );

            tt.setToX(0);
            tt.play();

        } catch (Exception e) {
            alertUtil.showError("Error");
        }
    }
}