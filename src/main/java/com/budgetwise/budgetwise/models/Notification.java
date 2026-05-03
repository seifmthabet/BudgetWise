package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.models.enums.NotificationType;

import java.time.LocalDateTime;

public class Notification {
    private int notificationId;
    private int userId;
    private NotificationType type;
    private String message;
    private boolean isRead;
    private LocalDateTime timestamp;

    public Notification(int userId, NotificationType type, String message, boolean isRead) {
        if (message.isEmpty() || message.trim().isEmpty()) throw new IllegalArgumentException("Message cannot be empty");
        if (type == null) throw new IllegalArgumentException("Type cannot be null");
        if (userId <= 0) throw new IllegalArgumentException("User ID must be positive");
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.isRead = isRead;
        this.timestamp = LocalDateTime.now();
    }

    public Notification(int notificationId, int userId, NotificationType type, String message, boolean isRead, LocalDateTime timestamp) {
        if (message.isEmpty() || message.trim().isEmpty()) throw new IllegalArgumentException("Message cannot be empty");
        if (type == null) throw new IllegalArgumentException("Type cannot be null");
        if (userId <= 0) throw new IllegalArgumentException("User ID must be positive");
        if (notificationId <= 0) throw new IllegalArgumentException("Notification ID must be positive");
        if (timestamp == null) throw new IllegalArgumentException("Timestamp cannot be null");
        this.notificationId = notificationId;
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.isRead = isRead;
        this.timestamp = timestamp;
    }

    public int getNotificationId() {
        return notificationId;
    }
    public NotificationType getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isRead() {
        return isRead;
    }
    public void markAsRead() {
        isRead = true;
    }
}
