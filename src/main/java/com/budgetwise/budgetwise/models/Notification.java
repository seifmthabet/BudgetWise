package com.budgetwise.budgetwise.models;

import java.time.LocalDate;

public class Notification {
    private int notificationId;
    private int userId;
    private String type;
    private String message;
    private boolean isRead;
    private LocalDate timestamp;

    public Notification(int userId, String type, String message, boolean isRead, LocalDate timestamp) {
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.isRead = isRead;
        this.timestamp = timestamp;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public boolean isRead() {
        return isRead;
    }
    public void markAsRead() {
        isRead = true;
    }
}
