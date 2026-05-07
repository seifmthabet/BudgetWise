package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.Main;
import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.utils.NavigationUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML private BorderPane mainRoot;
    @FXML private Button dashboardBtn, transactionBtn, budgetBtn, goalBtn, notificationBtn, reportBtn,ProfiletBtn,logoutBtn;

    private Button currentActiveBtn;

    @FXML
    public void initialize() throws IOException {
        currentActiveBtn = dashboardBtn;

        Parent view = FXMLLoader.load(
                Objects.requireNonNull(Main.class.getResource("/fxml/DashboardView.fxml"))
        );
        mainRoot.setCenter(view);
    }

    private void setActive(Button activeBtn) {
        if (currentActiveBtn != null) {
            currentActiveBtn.getStyleClass().remove("nav-button-active");
        }

        activeBtn.getStyleClass().add("nav-button-active");
        currentActiveBtn = activeBtn;
    }

    public void goToDashboard() {
        NavigationUtil.goToPage(mainRoot, "/fxml/DashboardView.fxml");
        setActive(dashboardBtn);
    }
    public void goToTransaction() {
        NavigationUtil.goToPage(mainRoot, "/fxml/TransactionView.fxml");
        setActive(transactionBtn);
    }
    public void goToBudget() {
        NavigationUtil.goToPage(mainRoot, "/fxml/BudgetView.fxml");
        setActive(budgetBtn);
    }
    public void goToGoal() {
        NavigationUtil.goToPage(mainRoot, "/fxml/GoalsView.fxml");
        setActive(goalBtn);
    }
    public void goToNotification() {
        NavigationUtil.goToPage(mainRoot, "/fxml/NotificationView.fxml");
        setActive(notificationBtn);
    }
    public void goToReport() {
        NavigationUtil.goToPage(mainRoot, "/fxml/ReportView.fxml");
        setActive(reportBtn);
    }
    public void goToProfile() {
        NavigationUtil.goToPage(mainRoot, "/fxml/ProfileView.fxml");
        setActive(ProfiletBtn);
    }
    public void handleLogout() {

        AppContext.getSession().setCurrentUser(null);

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/LoginView.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) mainRoot.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Error LogOut");
        }
    }
}
