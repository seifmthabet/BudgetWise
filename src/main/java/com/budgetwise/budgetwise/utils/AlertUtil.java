package com.budgetwise.budgetwise.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

/**
 * AlertUtil component.
 */
public class AlertUtil {

    /**
     * showError operation.
     * @param message parameter value
     */
    public void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        style(alert, "Error", message, "#ef4444");
        alert.showAndWait();
    }

    /**
     * showSuccess operation.
     * @param message parameter value
     */
    public void showSuccess(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        style(alert, "Success", message, "#22c55e");
        alert.showAndWait();
    }

    /**
     * showWarning operation.
     * @param message parameter value
     */
    public void showWarning(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        style(alert, "Warning", message, "#f59e0b");
        alert.showAndWait();
    }

    private void style(Alert alert, String title, String message, String color) {

        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.getDialogPane().setStyle("""
            -fx-background-color: #111827;
            -fx-background-radius: 15;
            -fx-padding: 20;
        """);

        alert.getDialogPane().lookup(".header-panel").setStyle("""
            -fx-background-color: transparent;
        """);

        alert.getDialogPane().lookup(".content.label").setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 14px;
        """);

        alert.getDialogPane().lookupButton(ButtonType.OK).setStyle(
                "-fx-background-color: linear-gradient(to right,#ff7eb3,#ff758c);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 6 15;" +
                        "-fx-font-weight: bold;"
        );
    }
}
