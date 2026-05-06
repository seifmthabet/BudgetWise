package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.Goal;
import com.budgetwise.budgetwise.models.enums.GoalStatus;
import com.budgetwise.budgetwise.services.GoalService;
import com.budgetwise.budgetwise.utils.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class GoalController {

    public Button dashboardBtn;
    public Button transactionBtn;
    public Button budgetBtn;
    public Button goalBtn;
    public Button notificationBtn;
    public Button reportBtn;
    @FXML private BorderPane borderPane;
    @FXML private ListView<Goal> goalList;
    @FXML private AnchorPane formOverlay;
    @FXML private TextField goalNameField;
    @FXML private TextField targetField;
    @FXML private TextField savedField;
    @FXML private DatePicker deadlinePicker;

    private GoalService goalService;
    private int userId;

    @FXML
    public void initialize() {
        goalService = AppContext.getGoalService();
        userId = AppContext.getSession().getCurrentUser().getUserId();

        loadGoals();

        // Customizing the list view display
        goalList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Goal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String progress = String.format("%.1f%%", item.getProgressPercent());
                    setText(String.format("%s | Target: %.2f | Saved: %.2f | Progress: %s",
                            item.getName(), item.getTargetAmount(), item.getCurrentAmount(), progress));
                }
            }
        });
    }

    private void loadGoals() {
        ObservableList<Goal> goals = FXCollections.observableArrayList(goalService.getGoals(userId));
        goalList.setItems(goals);
    }

    @FXML
    public void showForm() {
        formOverlay.setVisible(true);
    }

    @FXML
    public void hideForm() {
        formOverlay.setVisible(false);
    }

    @FXML
    public void handleCreateGoal() {
        try {
            // Validation
            if (goalNameField.getText().isEmpty() || targetField.getText().isEmpty()) {
                showAlert("Input Error", "Please fill in all required fields (Name and Target Amount).");
                return;
            }

            String name = goalNameField.getText();
            double target = Double.parseDouble(targetField.getText());
            double saved = savedField.getText().isEmpty() ? 0.0 : Double.parseDouble(savedField.getText());

            LocalDateTime deadline = deadlinePicker.getValue() != null ?
                    deadlinePicker.getValue().atTime(LocalTime.MIDNIGHT) :
                    LocalDateTime.now();

            Goal newGoal = new Goal(userId, name, target, saved, deadline, GoalStatus.IN_PROGRESS);

            goalService.createGoal(newGoal);

            loadGoals();
            hideForm();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numeric values for Amount fields.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while creating the goal: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML public void goToDashboard() { NavigationUtil.goToPage(borderPane, "/fxml/DashboardView.fxml"); }
    @FXML public void goToTransaction() { NavigationUtil.goToPage(borderPane, "/fxml/TransactionView.fxml"); }
    @FXML public void goToBudget() { NavigationUtil.goToPage(borderPane, "/fxml/BudgetView.fxml"); }
    @FXML public void goToGoal() { NavigationUtil.goToPage(borderPane, "/fxml/GoalsView.fxml"); }
    @FXML public void goToNotification() { NavigationUtil.goToPage(borderPane, "/fxml/NotificationView.fxml"); }
    @FXML public void goToReport() { NavigationUtil.goToPage(borderPane, "/fxml/ReportView.fxml"); }
}