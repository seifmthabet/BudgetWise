package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.Notification;
import com.budgetwise.budgetwise.models.enums.NotificationType;
import com.budgetwise.budgetwise.utils.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NotificationController {
    @FXML
    private ListView<Notification> notificationList;

    @FXML
    public void initialize() {
        notificationList.setCellFactory(list -> new ListCell<>(){
            private final HBox root = new HBox(15);
            private final VBox textBox = new VBox(5);
            private final Label messageLabel = new Label();
            private final Button readBtn = new Button("✔");
            private final Button deleteBtn = new Button("🗑");

            {
                root.setAlignment(Pos.CENTER_LEFT);
                messageLabel.setStyle("-fx-text-fill: white; -fx-font-size:14;");

                String glassyBtnStyle = "-fx-min-width: 32px; -fx-min-height: 32px; -fx-max-width: 32px; -fx-max-height: 32px; -fx-background-radius: 50em; -fx-border-radius: 50em; -fx-cursor: hand; ";

                readBtn.setStyle(glassyBtnStyle + "-fx-background-color: rgba(34, 197, 94, 0.15); -fx-border-color: rgba(34, 197, 94, 0.4); -fx-text-fill: #22c55e;");

                deleteBtn.setStyle(glassyBtnStyle + "-fx-background-color: rgba(239, 68, 68, 0.15); -fx-border-color: rgba(239, 68, 68, 0.4); -fx-text-fill: #ef4444;");

                textBox.getChildren().add(messageLabel);

                root.getChildren().addAll(readBtn, deleteBtn, textBox);
            }

            @Override
            protected void updateItem(Notification item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                messageLabel.setText(item.getMessage());

                String baseRootStyle = "-fx-padding:12 15; -fx-background-color:#1e293b; -fx-background-radius:8; ";

                if (item.isRead()) {
                    root.setStyle(baseRootStyle + "-fx-border-color:#ef4444; -fx-border-width:0 0 0 4; -fx-border-radius: 8;");
                } else {
                    root.setStyle(baseRootStyle + "-fx-border-color:#22c55e; -fx-border-width:0 0 0 4; -fx-border-radius: 8;");
                }

                readBtn.setOnAction(e -> {
                    item.markAsRead();
                    AppContext.getNotificationService().markAsRead(item.getNotificationId());
                    getListView().refresh();
                });

                deleteBtn.setOnAction(e -> {
                    AppContext.getNotificationService().deleteNotification(item.getNotificationId());
                    getListView().getItems().remove(item);
                });

                setGraphic(root);
            }

        });

        notificationList.setItems(FXCollections.observableArrayList(
                AppContext.getNotificationService().getUnreadNotifications(
                        AppContext.getSession().getCurrentUser().getUserId()
                )
        ));
    }

    @FXML
    public void handleClearAll(){
        notificationList.getItems().clear();
    }
}
