package com.budgetwise.budgetwise.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class TransactionController {
    @FXML private TextField titleField;
    @FXML
    private TextField amountField;
    @FXML private ComboBox<String> typeCombo;
    @FXML private DatePicker datePicker;

    @FXML
    private void handleSaveTransaction() {
        String title = titleField.getText();
        String amount = amountField.getText();
        String type = typeCombo.getValue();
        LocalDate date = datePicker.getValue();

        System.out.println(title + " " + amount + " " + type + " " + date);
    }
}
