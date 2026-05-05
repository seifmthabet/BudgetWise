package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.services.BudgetService;
import com.budgetwise.budgetwise.services.GoalService;
import com.budgetwise.budgetwise.services.TransactionService;
import com.budgetwise.budgetwise.utils.NavigationUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.math.BigDecimal;

public class DashboardController {
    @FXML
    private BorderPane borderPane;

    // Buttons
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button transactionBtn;
    @FXML
    private Button budgetBtn;
    @FXML
    private Button goalBtn;
    @FXML
    private Button notificationBtn;
    @FXML
    private Button reportBtn;

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
    private int userId;

    @FXML
    public void initialize() {
        transactionService = AppContext.getTransactionService();
        budgetService = AppContext.getBudgetService();
        goalService = AppContext.getGoalService();
        userId = AppContext.getSession().getCurrentUser().getUserId();

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

        balanceLabel.setText(transactionService.getTotalBalance(userId).toString());
        incomeLabel.setText(transactionService.getTotalIncome(userId).toString());
        expenseLabel.setText(transactionService.getTotalExpense(userId).toString());
        transactionsLabel.setText(transactionService.filterByUserId(userId).size() + "");
        budgetsLabel.setText(budgetService.getBudgets(userId).size() + "");
        goalsLabel.setText(goalService.getGoals(userId).size() + "");


        transactionsTable.setItems(getData());
    }

    private ObservableList<Transaction> getData() {
        return FXCollections.observableArrayList(transactionService.filterByUserId(userId));
    }

    public void goToDashboard() {
        NavigationUtil.goToPage(borderPane, "/fxml/DashboardView.fxml");
    }
    public void goToTransaction() {
        NavigationUtil.goToPage(borderPane, "/fxml/TransactionView.fxml");
    }
    public void goToBudget() {
        NavigationUtil.goToPage(borderPane, "/fxml/BudgetView.fxml");
    }
    public void goToGoal() {
        NavigationUtil.goToPage(borderPane, "/fxml/GoalsView.fxml");
    }
    public void goToNotification() {
        NavigationUtil.goToPage(borderPane, "/fxml/NotificationView.fxml");
    }
    public void goToReport() {
        NavigationUtil.goToPage(borderPane, "/fxml/ReportView.fxml");
    }



}
