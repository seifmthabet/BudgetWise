package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.models.Budget;
import com.budgetwise.budgetwise.models.Category;
import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.utils.NavigationUtil;
import com.budgetwise.budgetwise.core.AppContext;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BudgetController {
//  ======================= UI Elements ===========================



    @FXML private AnchorPane formcreateOverlay;
    @FXML private BorderPane borderPane;
    @FXML private Button dashboardBtn;
    @FXML private Button transactionBtn;
    @FXML private Button budgetBtn;
    @FXML private Button goalBtn;
    @FXML private Button notificationBtn;
    @FXML private Button reportBtn;
    @FXML private TableView<Budget> budgetTable;

    @FXML private TableColumn<Budget, String> categoryColumn;
    @FXML private TableColumn<Budget, Double> amountColumn;
    @FXML private TableColumn<Budget, Double> spentColumn;
    @FXML private TableColumn<Budget, Double> remainingColumn;
    @FXML private TableColumn<Budget, LocalDate> startDateColumn;
    @FXML private TableColumn<Budget, LocalDate> endDateColumn;

    @FXML private ListView<Budget> budgetListView;
    @FXML private AnchorPane editOverlay;
    @FXML private TextField editAmountField;
    @FXML private DatePicker editStartDatePicker;
    @FXML private DatePicker editEndDatePicker;
    @FXML private TextField editAlertField;


    @FXML private ComboBox<Category> categoryBox;
    @FXML private TextField amountField;
    @FXML private TextField alertField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextField newCategoryField;

    private Budget editingBudget;
    private int userId;

//  ======================= Show Form -> Create Budget ===========================
    public void showForm(){
        formcreateOverlay.setVisible(true);
    }
    public void hideForm(){
        formcreateOverlay.setVisible(false);
    }
//  ======================= Save ===========================
public void handleSaveBudget() {
    try {
        BigDecimal Balance = AppContext.getTransactionService().getTotalBalance(userId);
        if(Balance.intValue() == 0){
            throw new RuntimeException("You Should Add Balance To Make A Budget");
        }
        Category category = categoryBox.getValue();
        double amount = Double.parseDouble(amountField.getText());
        int alert = alertField.getText().isEmpty()
                ? 80
                : Integer.parseInt(alertField.getText());

        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        LocalDateTime startDate = start.atStartOfDay();
        LocalDateTime endDate = end.atStartOfDay();

        AppContext.getValidator().validateBudget(category, amount, startDate, endDate);

        int userId = AppContext.getSession().getCurrentUser().getUserId();
        int categoryId = category.getCategoryId();

        Budget budget = new Budget(
                userId,
                categoryId,
                amount,
                0.0,
                startDate,
                endDate,
                alert
        );

        AppContext.getBudgetService().createBudget(budget);
        budgetTable.setItems(getData());

        hideForm();

    } catch (Exception e) {
        AppContext.getAlertUtil().showError(e.getMessage());
    }
}
//  ======================= Intialize ===========================
    public void initialize(){
        userId = AppContext.getSession().getCurrentUser().getUserId();
        //Expire Date
        AppContext.getBudgetService().removeExpiredBudgets();
        // 1. Setup Table Columns
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategoryName()));
        amountColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAmount()));
        spentColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getSpentAmount()));
        startDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getStartDate().toLocalDate()));
        endDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEndDate().toLocalDate()));

        // Set Remaining amount with logic for dynamic coloring
        remainingColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRemainingAmount()));
        remainingColumn.setCellFactory(column -> new TableCell<Budget, Double>() {
            @Override
            protected void updateItem(Double remaining, boolean empty) {
                super.updateItem(remaining, empty);
                if (empty || remaining == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText("$" + String.format("%.2f", remaining));
                    if (remaining < 0) {
                        setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;"); // Red if overdrawn
                    } else if (remaining < 50) { // Or compare against a percentage of the total
                        setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;"); // Orange if low
                    } else {
                        setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold;"); // Green if healthy
                    }
                }
            }
        });

        budgetTable.setItems(getData());

        // 2. Setup Category ComboBox
        ObservableList<Category> categories = FXCollections.observableArrayList(
                AppContext.getCategoryService().getAllDefaults()
        );
        categoryBox.setItems(categories);

        // 3. Setup Edit ListView with a custom cell for a cleaner UI
        budgetListView.setCellFactory(list -> new ListCell<Budget>() {
            @Override
            protected void updateItem(Budget item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item.getCategoryName() + "  -  $" + item.getAmount() + " (" + item.getStartDate().toLocalDate() + " to " + item.getEndDate().toLocalDate() + ")");
                    setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10; -fx-background-color: transparent; -fx-border-color: rgba(255,255,255,0.05); -fx-border-width: 0 0 1 0;");
                }
            }
        });

        // Edit List Selection Listener
        budgetListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                editAmountField.setText(String.valueOf(selected.getAmount()));
                editStartDatePicker.setValue(selected.getStartDate().toLocalDate());
                editEndDatePicker.setValue(selected.getEndDate().toLocalDate());
                editAlertField.setText(String.valueOf(selected.getAlertThreshold()));
                editingBudget = selected;
            }
        });
    }

    private ObservableList<Budget> getData() {
        return FXCollections.observableArrayList(AppContext.getBudgetService().filterById(userId));
    }
    //  ======================== Show and Edit  ===========================
    public void showEditForm() {
        editOverlay.setVisible(true);

        budgetListView.setItems(getData());
    }
    public void hideEditForm() {
        editOverlay.setVisible(false);
    }

    //  ======================== Update ===========================

    @FXML
    public void handleUpdateBudget() {
        if (editingBudget == null) {
            AppContext.getAlertUtil().showError("Select budget first");
            return;
        }

        try {
            double amount = Double.parseDouble(editAmountField.getText());

            LocalDateTime end = editEndDatePicker.getValue().atStartOfDay();
            LocalDateTime start = editStartDatePicker.getValue().atStartOfDay();

            if (end == null) {
                throw new RuntimeException("End date required");
            }
            AppContext.getValidator().validateUpdateBudget( amount, start, end);


            AppContext.getBudgetService().updateBudget(
                    editingBudget.getBudgetId(),
                    amount,
                    end
            );

            budgetTable.setItems(getData());
            budgetListView.setItems(getData());

        } catch (Exception e) {
            AppContext.getAlertUtil().showError(e.getMessage());
        }
    }
    //  ======================== Delete ===========================

    @FXML
    public void deleteSelectedBudget() {

        Budget selected = budgetListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            AppContext.getAlertUtil().showError("Select budget first");
            return;
        }

        AppContext.getBudgetService().deleteBudget(selected);

        budgetTable.setItems(getData());
        budgetListView.setItems(getData());
    }


}
