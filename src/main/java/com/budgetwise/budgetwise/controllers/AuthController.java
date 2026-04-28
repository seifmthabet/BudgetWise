package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.services.UserService;
import com.budgetwise.budgetwise.utils.AlertUtil;
import com.budgetwise.budgetwise.utils.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class AuthController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField PasswordField;

    private UserService userService = new UserService();
    private AlertUtil alertUtil = new AlertUtil();
    private Validation valid = new Validation();

    @FXML
    public void handleRegister(){

        String name = nameField.getText();
        String email = EmailField.getText();
        String password = PasswordField.getText();
        if(!valid.validation(name,email,password)){
            return;
        }
        User user = userService.register(name, email, password);

        if(user == null){
            alertUtil.showError("Email already exists");
        } else {
            alertUtil.showSuccess("Account created successfully");
        }
    }
}
