package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.Category;
import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.models.enums.PaymentMethod;
import com.budgetwise.budgetwise.models.enums.TransactionType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionController {
    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, String> titleColumn, typeColumn, categoryColumn, dateColumn;
    @FXML private TableColumn<Transaction, BigDecimal> amountColumn;
    @FXML private Label totalCountLabel;

    @FXML private AnchorPane formOverlay;
    @FXML private TextField titleField, amountField;
    @FXML private ComboBox<TransactionType> typeCombo;
    @FXML private ComboBox<Category> categoryCombo;
    @FXML private ComboBox<PaymentMethod> paymentCombo;
    @FXML private DatePicker datePicker;

    @FXML private TextField searchField;
    @FXML private ComboBox<TransactionType> filterTypeCombo;
    @FXML private DatePicker filterDatePicker;

    private ObservableList<Transaction> masterData;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadData();

        // Initialize Dropdowns
        typeCombo.setItems(FXCollections.observableArrayList(TransactionType.values()));
        paymentCombo.setItems(FXCollections.observableArrayList(PaymentMethod.values()));
        // categoryCombo.setItems(...)
        ObservableList<Category> categories = FXCollections.observableArrayList(
                AppContext.getCategoryService().getAllDefaults()
        );
        categoryCombo.setItems(categories);
        filterTypeCombo.setItems(
                FXCollections.observableArrayList(TransactionType.values())
        );

        setupFilters();
    }

    private void setupTableColumns() {
        titleColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDescription()));
        dateColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDate().toString()));

        // Styled Amount Column
        amountColumn.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getAmount()));
        amountColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(BigDecimal amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText("$" + amount);
                    Transaction row = getTableView().getItems().get(getIndex());
                    if (row.getType() == TransactionType.INCOME) {
                        setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                    }
                }
            }
        });

        // Type Badge Column
        typeColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getType().toString()));
        categoryColumn.setCellValueFactory(d -> {
            int categoryId = d.getValue().getCategoryId();

            String categoryName = AppContext.getCategoryService()
                    .findById(categoryId)
                    .getName();

            return new SimpleStringProperty(categoryName);
        });
        typeColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String type, boolean empty) {
                super.updateItem(type, empty);
                if (empty || type == null) {
                    setGraphic(null);
                } else {
                    Label badge = new Label(type);
                    String color = type.equalsIgnoreCase("INCOME") ? "#22c55e" : "#ef4444";
                    badge.setStyle("-fx-background-color: rgba(" + (type.equalsIgnoreCase("INCOME") ? "34, 197, 94" : "239, 68, 68") + ", 0.15); " +
                            "-fx-text-fill: " + color + "; -fx-padding: 2 8; -fx-background-radius: 5; -fx-font-size: 11;");
                    setGraphic(badge);
                }
            }
        });
    }

    private void loadData() {
        int userId = AppContext.getSession().getCurrentUser().getUserId();

        var list = AppContext.getTransactionService()
                .filterByUserId(userId);

        masterData = FXCollections.observableArrayList(list);

        transactionsTable.setItems(masterData);

        totalCountLabel.setText(String.valueOf(list.size()));
    }

    @FXML public void showForm() { formOverlay.setVisible(true); }
    @FXML public void hideForm() { formOverlay.setVisible(false); }

    @FXML
    private void handleSaveTransaction() {
        try {
            // Logic to save through TransactionService...
            Category category = categoryCombo.getValue();
            TransactionType type = typeCombo.getValue();
            PaymentMethod payment = paymentCombo.getValue();
            LocalDateTime date = datePicker.getValue().atStartOfDay();
            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountField.getText()));
            String title = titleField.getText();
            int userId = AppContext.getSession().getCurrentUser().getUserId();

            Transaction tx = new Transaction(userId, category.getCategoryId(), type, amount, title, payment);
            AppContext.getValidator().validateTransaction(tx);
            AppContext.getTransactionService().addTransaction(tx);

            hideForm();
            loadData();
        } catch (Exception e) {
            AppContext.getAlertUtil().showError(e.getMessage());
        }
    }

    private void setupFilters() {

        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        filterTypeCombo.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        filterDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
    }

    private void applyFilters() {

        ObservableList<Transaction> filtered =
                FXCollections.observableArrayList();

        for (Transaction tx : masterData) {

            boolean matchesSearch = true;
            boolean matchesType = true;
            boolean matchesDate = true;

            // Search filter
            String search = searchField.getText();

            if (search != null && !search.isBlank()) {
                matchesSearch = tx.getDescription()
                        .toLowerCase()
                        .contains(search.toLowerCase());
            }

            // Type filter
            TransactionType selectedType = filterTypeCombo.getValue();

            if (selectedType != null) {
                matchesType = tx.getType() == selectedType;
            }

            // Date filter
            LocalDate selectedDate = filterDatePicker.getValue();

            if (selectedDate != null) {
                matchesDate = tx.getDate()
                        .toLocalDate()
                        .equals(selectedDate);
            }

            if (matchesSearch && matchesType && matchesDate) {
                filtered.add(tx);
            }
        }

        transactionsTable.setItems(filtered);

        totalCountLabel.setText(String.valueOf(filtered.size()));
    }

    @FXML
    private void handleResetFilters() {

        searchField.clear();

        filterTypeCombo.setValue(null);

        filterDatePicker.setValue(null);

        transactionsTable.setItems(masterData);

        totalCountLabel.setText(String.valueOf(masterData.size()));
    }
}