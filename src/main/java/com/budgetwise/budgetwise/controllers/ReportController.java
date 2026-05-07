package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.services.ReportService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.util.Map;

public class ReportController {

    private ReportService reportService;

    @FXML private PieChart expensePieChart;
    @FXML private BarChart<String, Number> incomeExpenseChart;
    @FXML private Label insightLabel, emptyLabel;
    @FXML private ComboBox<String> rangeBox;

    @FXML
    public void initialize() {
        rangeBox.setItems(javafx.collections.FXCollections.observableArrayList(
                "This Month", "Last Month", "Last 3 Months", "This Year"
        ));
        // Chart initialization logic here...
    }

    @FXML
    private void handleExportCSV() {
        // Implementation: Use a FileChooser to save CSV
        System.out.println("Exporting CSV...");
    }

    @FXML
    private void handleExportPDF() {
        // Implementation: Call PDF generation logic
        System.out.println("Generating PDF Report...");
    }

    @FXML
    private void handleExportExcel() {
        System.out.println("Generating Excel Report...");
    }

    @FXML
    private void handleRangeChange() {
        // Update charts based on rangeBox.getValue()
    }

    @FXML
    private void handleGenerateReport() {
        LocalDate from = LocalDate.now().withDayOfMonth(1);
        LocalDate to = LocalDate.now();

        Thread thread = new Thread(() -> {
            try {
                Map<String, Double> categories = reportService.getExpenseByCategory(1, from, to);

                Platform.runLater(() -> {
                    if (categories.isEmpty()) {
                        emptyLabel.setVisible(true);
                        expensePieChart.setVisible(false);
                        incomeExpenseChart.setVisible(false);
                    } else {
                        emptyLabel.setVisible(false);
                        expensePieChart.setVisible(true);
                        incomeExpenseChart.setVisible(true);
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    throw  new RuntimeException(e);
                });
            }
        });
    }
}