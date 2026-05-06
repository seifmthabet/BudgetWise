package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.DAOs.UserDAO;
import com.budgetwise.budgetwise.core.AppContext;
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
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField confirmPasswordField;
    @FXML
    private AnchorPane rootPane;
    @FXML private Circle bubble1;
    @FXML private Circle bubble2;
    @FXML private Circle bubble3;

    private UserService userService;
    private AlertUtil alertUtil;
    private Validation validation;

    @FXML
    public void initialize() {
        userService = AppContext.getUserService();
        alertUtil = AppContext.getAlertUtil();
        validation = AppContext.getValidator();
        animateBubble(bubble1, 15);
        animateBubble(bubble2, 20);
        animateBubble(bubble3, 25);
    }

    @FXML
    public void handleRegister() {

        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (!validation.validateRigester(name, email, password)) return;
            if (!password.equals(confirmPassword)) {
                alertUtil.showWarning("Passwords do not match");
                return;
            }
            User user = userService.register(name, email, password);

            alertUtil.showSuccess("Account Created Successfully");
            AppContext.getSession().setCurrentUser(user);
            goToDashboard();

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
            AppContext.getSession().setCurrentUser(user);
            goToDashboard();

        } catch (Exception e) {
            alertUtil.showError(e.getMessage());
        }
    }
    public void goToLogin(){
        NavigationUtil.goToPage(rootPane,"/fxml/LoginView.fxml");
    }
    public void goToRegister(){
        NavigationUtil.goToPage(rootPane, "/fxml/RegisterView.fxml");
    }
    public void goToDashboard(){
        NavigationUtil.goToPage(rootPane, "/fxml/DashboardView.fxml");
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