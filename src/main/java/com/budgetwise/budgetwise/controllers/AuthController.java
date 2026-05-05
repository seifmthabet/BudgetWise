package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.DAOs.UserDAO;
import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.services.UserService;
import com.budgetwise.budgetwise.utils.AlertUtil;
import com.budgetwise.budgetwise.utils.NavigationUtil;
import com.budgetwise.budgetwise.utils.Validation;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class AuthController {

    @FXML private TextField nameField;
    @FXML private TextField EmailField;
    @FXML private TextField PasswordField;

    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML
    private AnchorPane rootPane;
    @FXML private Circle bubble1;
    @FXML private Circle bubble2;
    @FXML private Circle bubble3;

    private UserService userService;
    private AlertUtil alertUtil;
    private Validation validation;
    private NavigationUtil navigationUtil;

    @FXML
    public void initialize() {
        userService = new UserService(new UserDAO());
        alertUtil = new AlertUtil();
        validation = new Validation();
        navigationUtil = new NavigationUtil();
        animateBubble(bubble1, 15);
        animateBubble(bubble2, 20);
        animateBubble(bubble3, 25);
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
    public void goToLogin(){
        navigationUtil.goToPage(rootPane,"/fxml/LoginView.fxml");
    }
    public void goToRegister(){
        navigationUtil.goToPage(rootPane, "/fxml/RegisterView.fxml");
    }



    private void animateBubble(Circle bubble, int duration) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(duration), bubble);
        tt.setFromY(0);
        tt.setToY(-50);
        tt.setAutoReverse(true);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.play();
    }

}