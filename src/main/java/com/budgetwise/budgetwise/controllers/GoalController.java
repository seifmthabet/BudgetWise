package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.Goal;
import com.budgetwise.budgetwise.models.enums.GoalStatus;
import com.budgetwise.budgetwise.services.GoalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class GoalController {
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

        goalList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Goal item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    HBox card = new HBox(20);
                    card.setAlignment(Pos.CENTER_LEFT);
                    card.setStyle("-fx-background-color: rgba(31, 41, 55, 0.6); -fx-background-radius: 12; -fx-border-color: rgba(255, 255, 255, 0.05); -fx-border-radius: 12; -fx-padding: 20;");

                    VBox infoBox = new VBox(5);
                    Label nameLabel = new Label(item.getName());
                    nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

                    Label dateLabel = new Label("Deadline: " + item.getDeadline().toLocalDate().toString());
                    dateLabel.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 13px;");

                    infoBox.getChildren().addAll(nameLabel, dateLabel);

                    Pane spacer = new Pane();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    VBox progressBox = new VBox(8);
                    progressBox.setAlignment(Pos.CENTER_RIGHT);
                    progressBox.setPrefWidth(250);

                    HBox numbersBox = new HBox();
                    numbersBox.setAlignment(Pos.BASELINE_CENTER);
                    Label savedLabel = new Label("$" + String.format("%.2f", item.getCurrentAmount()));
                    savedLabel.setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold; -fx-font-size: 14px;");
                    Label targetLabel = new Label("of $" + String.format("%.2f", item.getTargetAmount()));
                    targetLabel.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 14px;");

                    numbersBox.getChildren().addAll(savedLabel, targetLabel);

                    ProgressBar progressBar = new ProgressBar(item.getProgressPercent() / 100.0);
                    progressBar.setMaxWidth(Double.MAX_VALUE); // Let it stretch

                    progressBox.getChildren().addAll(numbersBox, progressBar);

                    card.getChildren().addAll(infoBox, spacer, progressBox);

                    setGraphic(card);
                    setText(null);
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
        // Reset fields when opening form
        goalNameField.clear();
        targetField.clear();
        savedField.clear();
        deadlinePicker.setValue(null);
        formOverlay.setVisible(true);
    }

    @FXML
    public void hideForm() {
        formOverlay.setVisible(false);
    }

    @FXML
    public void handleCreateGoal() {
        try {
            if (goalNameField.getText().isEmpty() || targetField.getText().isEmpty()) {
                AppContext.getAlertUtil().showError("Please fill in the Name and Target Amount.");
                return;
            }

            String name = goalNameField.getText();
            double target = Double.parseDouble(targetField.getText());
            double saved = savedField.getText().isEmpty() ? 0.0 : Double.parseDouble(savedField.getText());

            LocalDateTime deadline = deadlinePicker.getValue() != null ?
                    deadlinePicker.getValue().atTime(LocalTime.MIDNIGHT) :
                    LocalDateTime.now().plusMonths(1);

            Goal newGoal = new Goal(userId, name, target, saved, deadline, GoalStatus.IN_PROGRESS);

            goalService.createGoal(newGoal);

            loadGoals();
            hideForm();

        } catch (NumberFormatException e) {
            AppContext.getAlertUtil().showError("Please enter valid numeric values for Amounts.");
        } catch (Exception e) {
            AppContext.getAlertUtil().showError("An error occurred: " + e.getMessage());
        }
    }
}