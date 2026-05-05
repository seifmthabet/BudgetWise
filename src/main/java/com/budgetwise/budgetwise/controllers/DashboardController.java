package com.budgetwise.budgetwise.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class DashboardController {
    @FXML
    private PieChart financeChart;

    public void initialize() {
        PieChart.Data income = new PieChart.Data("Income", 800);
        PieChart.Data expense = new PieChart.Data("Expense", 400);

        financeChart.getData().addAll(income, expense);
    }
}
