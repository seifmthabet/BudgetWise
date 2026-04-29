package com.budgetwise.budgetwise.controllers;

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
    @FXML
    private TextField nameField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField PasswordField;

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    private UserService userService = new UserService();
    private AlertUtil alertUtil = new AlertUtil();
    private Validation valid = new Validation();

    @FXML
    public void handleRegister(){

        String name = nameField.getText();
        String email = EmailField.getText();
        String password = PasswordField.getText();
        if(!valid.Rvalidation(name,email,password)){
            return;
        }
        User user = userService.register(name, email, password);

        if(user == null){
            alertUtil.showError("Email already exists");
        } else {
            alertUtil.showSuccess("Account created successfully");
        }
    }
    @FXML
    public void handleLogin(){
        String email = emailField.getText();
        String password = passwordField.getText();

        if(!valid.Lvalidation(email,password)){
            return;
        }
        User user = userService.Login(email, password);
        if(user == null){
            alertUtil.showError("User Is Not Found");
        }
        else {
            alertUtil.showSuccess("Welcome Mr/s "+ user.getName());
        }

    }

    public void goToLogin() {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource("/fxml/LoginView.fxml")
            );

            Scene scene = nameField.getScene();

            root.translateXProperty().set(scene.getWidth());

            ((AnchorPane) scene.getRoot()).getChildren().add(root);

            TranslateTransition tt =
                    new TranslateTransition(Duration.seconds(0.7), root);

            tt.setToX(0);
            tt.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
