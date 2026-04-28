package com.budgetwise.budgetwise.utils;

import javafx.scene.control.Alert;

public class AlertUtil {
    public void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(message);
        alert.show();
    }
    public void showSuccess(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setContentText(message);
        alert.show();
    }
}
