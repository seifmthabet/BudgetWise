package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.services.BudgetService;
import com.budgetwise.budgetwise.services.GoalService;
import com.budgetwise.budgetwise.services.TransactionService;
import com.budgetwise.budgetwise.utils.AlertUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;

/**
 * DashboardController component.
 */
public class DashboardController {

    // ===== Labels =====
    @FXML private Label balanceLabel;
    @FXML private Label incomeLabel;
    @FXML private Label expenseLabel;
    @FXML private Label transactionsLabel;
    @FXML private Label goalsLabel;
    @FXML private Label budgetsLabel;

    // ===== Table =====
    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, String> titleColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, BigDecimal> amountColumn;
    @FXML private TableColumn<Transaction, String> dateColumn;

    // ===== Services =====
    private TransactionService transactionService;
    private BudgetService budgetService;
    private GoalService goalService;
    private AlertUtil alertUtil;

    private int userId;

    // ================= INIT =================
    /**
     * initialize operation.
     */
    @FXML
    public void initialize() {

        transactionService = AppContext.getTransactionService();
        budgetService = AppContext.getBudgetService();
        goalService = AppContext.getGoalService();
        alertUtil = AppContext.getAlertUtil();

        userId = AppContext.getSession()
                .getCurrentUser()
                .getUserId();

        setupTableColumns();

        refreshDashboard();
    }

    // ================= SETUP TABLE =================
    private void setupTableColumns() {

        titleColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDescription())
        );

        typeColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getType()).asString()
        );

        dateColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDate().toString())
        );

        amountColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getAmount())
        );

        amountColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(BigDecimal amount, boolean empty) {
                super.updateItem(amount, empty);

                if (empty || amount == null) {
                    setText(null);
                    setStyle("");
                } else {

                    setText("$" + amount);

                    Transaction row = getTableView()
                            .getItems()
                            .get(getIndex());

                    if (row.getType().toString().equalsIgnoreCase("INCOME")) {
                        setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                    }
                }
            }
        });

        typeColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String type, boolean empty) {
                super.updateItem(type, empty);

                if (empty || type == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label badge = new Label(type);

                    String color = type.equalsIgnoreCase("INCOME")
                            ? "#22c55e"
                            : "#ef4444";

                    badge.setStyle(
                            "-fx-background-color: rgba(" +
                                    (type.equalsIgnoreCase("INCOME")
                                            ? "34, 197, 94"
                                            : "239, 68, 68") +
                                    ", 0.15);" +
                                    "-fx-text-fill: " + color + ";" +
                                    "-fx-padding: 2 8;" +
                                    "-fx-background-radius: 5;" +
                                    "-fx-font-size: 11;"
                    );

                    setGraphic(badge);
                }
            }
        });
    }

    // ================= REFRESH DASHBOARD =================
    private void refreshDashboard() {

        try {
            String currency = AppContext.getSession()
                    .getCurrentUser()
                    .getCurrency();

            double balance = transactionService.getTotalBalance(userId).doubleValue();
            double income = transactionService.getTotalIncome(userId).doubleValue();
            double expense = transactionService.getTotalExpense(userId).doubleValue();

            // لو عندك Converter
            balance = AppContext.getCurrencyConverter()
                    .convert(balance, "USD", currency);

            income = AppContext.getCurrencyConverter()
                    .convert(income, "USD", currency);

            expense = AppContext.getCurrencyConverter()
                    .convert(expense, "USD", currency);

            balanceLabel.setText(currency + " " + balance);
            incomeLabel.setText(currency + " " + income);
            expenseLabel.setText(currency + " " + expense);

            transactionsLabel.setText(
                    String.valueOf(transactionService.filterByUserId(userId).size())
            );

            budgetsLabel.setText(
                    String.valueOf(budgetService.getBudgets(userId).size())
            );

            goalsLabel.setText(
                    String.valueOf(goalService.getGoals(userId).size())
            );

            transactionsTable.setItems(getData());

        } catch (Exception e) {
            alertUtil.showError(e.getMessage());
        }
    }

    // ================= TABLE DATA =================
    private ObservableList<Transaction> getData() {

        try {
            return FXCollections.observableArrayList(
                    transactionService.filterByUserId(userId)
            );
        } catch (Exception e) {
            alertUtil.showError(e.getMessage());
        }

        return FXCollections.observableArrayList();
    }
}
