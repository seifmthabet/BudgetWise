package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.enums.NotificationType;
import com.budgetwise.budgetwise.services.ReportService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.Map;

public class ReportController {

    private ReportService reportService;

    @FXML private PieChart expensePieChart;
    @FXML private BarChart<String, Number> incomeExpenseChart;
    @FXML private Label insightLabel, emptyLabel;
    @FXML private ComboBox<String> rangeBox;

    @FXML private AnchorPane exportModalOverlay;
    @FXML private ComboBox<String> exportFormatBox;
    @FXML private DatePicker exportStartDate;
    @FXML private DatePicker exportEndDate;
    @FXML private CheckBox checkTransactions;
    @FXML private CheckBox checkBudgets;
    @FXML private CheckBox checkGoals;

    @FXML
    public void initialize() {
        rangeBox.setItems(javafx.collections.FXCollections.observableArrayList(
                "This Month", "Last Month", "Last 3 Months", "This Year"
        ));
        rangeBox.setValue("This Month");

        exportFormatBox.setItems(javafx.collections.FXCollections.observableArrayList(
                "CSV", "PDF", "Excel"
        ));
        // Chart initialization logic here...
    }

    @FXML
    private void handleRangeChange() {
        // Update charts based on rangeBox.getValue()
    }

    @FXML public void showExportModal() {
        exportModalOverlay.setVisible(true);
    }

    @FXML public void hideExportModal() {
        exportModalOverlay.setVisible(false);
    }

    @FXML
    private void handleGenerateAndDownload() {
        try {
            String format = exportFormatBox.getValue();
            LocalDate start = exportStartDate.getValue();
            LocalDate end = exportEndDate.getValue();

            boolean includeTransactions = checkTransactions.isSelected();
            boolean includeBudgets = checkBudgets.isSelected();
            boolean includeGoals = checkGoals.isSelected();

            int userId = AppContext.getSession().getCurrentUser().getUserId();

            AppContext.getDataExportService().generateAndDownloadReport(
                    userId, format, start, end, includeTransactions, includeBudgets, includeGoals
            );

            hideExportModal();
            AppContext.getAlertUtil().showSuccess("Success" + "Your file has been generated and downloaded.");
            AppContext.getNotificationService().sendNotification(userId, NotificationType.SYSTEM_ALERT, "Report Generated");
        } catch (Exception e) {
            AppContext.getAlertUtil().showError("Export Failed" + e.getMessage());
        }
    }

}