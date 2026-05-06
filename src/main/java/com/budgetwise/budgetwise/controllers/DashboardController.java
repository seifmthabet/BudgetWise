package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.services.BudgetService;
import com.budgetwise.budgetwise.services.GoalService;
import com.budgetwise.budgetwise.services.TransactionService;
import com.budgetwise.budgetwise.utils.AlertUtil;
import com.budgetwise.budgetwise.utils.NavigationUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.math.BigDecimal;

public class DashboardController {
    // Labels
    @FXML
    private Label balanceLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private Label expenseLabel;
    @FXML
    private Label transactionsLabel;
    @FXML
    private Label goalsLabel;
    @FXML
    private Label budgetsLabel;

    // Table
    @FXML
    private TableView<Transaction> transactionsTable;
    @FXML
    private TableColumn<Transaction, String> titleColumn;
    @FXML
    private TableColumn<Transaction, String> typeColumn;
    @FXML
    private TableColumn<Transaction, BigDecimal> amountColumn;
    @FXML
    private TableColumn<Transaction, String> dateColumn;

    private TransactionService transactionService;
    private BudgetService budgetService;
    private GoalService goalService;
    private AlertUtil alertUtil;
    private int userId;

    @FXML
    public void initialize() {
        transactionService = AppContext.getTransactionService();
        budgetService = AppContext.getBudgetService();
        goalService = AppContext.getGoalService();
        userId = AppContext.getSession().getCurrentUser().getUserId();
        alertUtil = AppContext.getAlertUtil();

        titleColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getDescription()
        ));
        typeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(
                data.getValue().getType()
        ).asString());
        amountColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(
                data.getValue().getAmount()
        ));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getDate().toString()
        ));
        amountColumn.setCellFactory(column -> new TableCell<Transaction, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText("$" + amount.toString());
                    Transaction rowData = getTableView().getItems().get(getIndex());
                    if (rowData.getType().toString().equalsIgnoreCase("INCOME")) {
                        setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                    }
                }
            }
        });
        typeColumn.setCellFactory(column -> new TableCell<Transaction, String>() {
            @Override
            protected void updateItem(String type, boolean empty) {
                super.updateItem(type, empty);
                if (empty || type == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label badge = new Label(type);
                    String color = type.equalsIgnoreCase("INCOME") ? "#22c55e" : "#ef4444";
                    badge.setStyle("-fx-background-color: rgba(" + (type.equalsIgnoreCase("INCOME") ? "34, 197, 94" : "239, 68, 68") + ", 0.15); " +
                            "-fx-text-fill: " + color + "; " +
                            "-fx-padding: 2 8; -fx-background-radius: 5; -fx-font-size: 11;");
                    setGraphic(badge);
                }
            }
        });

        balanceLabel.setText(transactionService.getTotalBalance(userId).toString());
        incomeLabel.setText(transactionService.getTotalIncome(userId).toString());
        expenseLabel.setText(transactionService.getTotalExpense(userId).toString());
        transactionsLabel.setText(transactionService.filterByUserId(userId).size() + "");
        budgetsLabel.setText(budgetService.getBudgets(userId).size() + "");
        goalsLabel.setText(goalService.getGoals(userId).size() + "");


        transactionsTable.setItems(getData());
    }

    private ObservableList<Transaction> getData() {
        try {
            return FXCollections.observableArrayList(transactionService.filterByUserId(userId));
        } catch (Exception e) {
             alertUtil.showError(e.getMessage());
        }
        return null;
    }
}
