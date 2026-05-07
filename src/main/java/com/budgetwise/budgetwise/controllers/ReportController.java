package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.DAOs.TransactionDAO;
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

    private final ReportService reportService =
            new ReportService(new TransactionDAO());

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
        rangeBox.getSelectionModel().selectFirst();

        handleGenerateReport();
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
    private void handleRangeChange() {
        handleGenerateReport();
        // Update charts based on rangeBox.getValue()
    }


    @FXML
    public void handleGenerateReport() {
        String selected = rangeBox.getValue();
        LocalDate to = LocalDate.now();
        LocalDate from = calculateFromDate(selected);

        Thread thread = new Thread(() -> {
            try {
                Map<String, Double> categories = reportService.getExpenseByCategory(1, from, to);
                double[] stats = reportService.getIncomeVsExpense(1, from, to);
                String insight = generateInsightText(categories);

                Platform.runLater(() -> {
                    if (categories.isEmpty()) {
                        emptyLabel.setVisible(true);
                        expensePieChart.setVisible(false);
                        incomeExpenseChart.setVisible(false);
                        insightLabel.setText("");
                    } else {
                        emptyLabel.setVisible(false);
                        expensePieChart.setVisible(true);
                        incomeExpenseChart.setVisible(true);

                        updateChartsData(categories, stats);
                        insightLabel.setText(insight);
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> insightLabel.setText("Error: " + e.getMessage()));
            }
        });
        thread.setDaemon(true);
        thread.start();
    }


    private LocalDate calculateFromDate(String range) {
        LocalDate now = LocalDate.now();
        if (range == null || range.isEmpty()){
            return now.withDayOfMonth(1);
        }
        return switch (range) {
            case "Last Month" -> now.minusMonths(1).withDayOfMonth(1);
            case "Last 3 Months" -> now.minusMonths(3);
            case "This Year" -> now.withDayOfYear(1);
            default -> now.withDayOfMonth(1);
        };
    }

    private void updateChartsData(Map<String, Double> categories, double[] stats) {
        expensePieChart.getData().clear();

        categories.forEach((name, value) ->
                expensePieChart.getData().add(new PieChart.Data(name, value))
        );

        incomeExpenseChart.getData().clear();

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Expense");

        String label = rangeBox.getValue();

        incomeSeries.getData().add(new XYChart.Data<>(label, stats[0]));
        expenseSeries.getData().add(new XYChart.Data<>(label, stats[1]));

        incomeExpenseChart.getData().addAll(incomeSeries, expenseSeries);
    }


    private String generateInsightText(Map<String, Double> categories) {

        if (categories == null || categories.isEmpty()) {
            return "No spending data available.";
        }

        Map.Entry<String, Double> maxEntry =
                categories.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null);

        if (maxEntry == null) {
            return "No spending data available.";
        }

        return "Top spending category: " + maxEntry.getKey()
                + " (" + String.format("%.2f", maxEntry.getValue()) + ")";
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