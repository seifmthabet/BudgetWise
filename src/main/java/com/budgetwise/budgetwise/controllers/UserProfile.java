package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * UserProfile component.
 */
public class UserProfile {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label transactionsCount;
    @FXML private Label budgetsCount;

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField currencyField;

    @FXML private CheckBox notificationCheck;

    // ===== Current User =====
    private User currentUser;

    /**
     * initialize operation.
     */
    @FXML
    public void initialize() {
        loadUser();
    }

    // ===== Load user from session =====
    private void loadUser() {
        try {


            int userId = AppContext.getSession().getCurrentUser().getUserId();

            currentUser = AppContext.getUserService().findUserById(userId);

            if (currentUser == null) {
                AppContext.getAlertUtil()
                        .showError("User not found");
                return;
            }

            // Fill UI
            nameLabel.setText(currentUser.getName());
            emailLabel.setText(currentUser.getEmail());

            nameField.setText(currentUser.getName());
            emailField.setText(currentUser.getEmail());
            currencyField.setText(currentUser.getCurrency());

            notificationCheck.setSelected(true);


            transactionsCount.setText(String.valueOf(
                    AppContext.getTransactionService().getTransactions().size()
            ));
            budgetsCount.setText(String.valueOf(
                    AppContext.getBudgetService().getBudgets(userId).size()
            ));
        }catch (Exception e){
            AppContext.getAlertUtil().showError(e.getMessage());
        }
    }

    // ===== SAVE =====
    @FXML
    private void handleSaveProfile() {

        if (currentUser == null) return;

        currentUser.setName(nameField.getText());
        currentUser.setEmail(emailField.getText());
        currentUser.setCurrency(currencyField.getText());

        try {
            AppContext.getUserService().updateUserProfile(currentUser);

            AppContext.getSession().setCurrentUser(currentUser);

            nameLabel.setText(currentUser.getName());
            emailLabel.setText(currentUser.getEmail());

            AppContext.getAlertUtil()
                    .showSuccess("Profile updated successfully");

        } catch (Exception e) {
            AppContext.getAlertUtil()
                    .showError("Update failed: " + e.getMessage());
        }
    }
}
