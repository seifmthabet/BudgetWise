package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.models.enums.NotificationType;

import java.time.LocalDateTime;

/**
 * Notification component.
 */
public class Notification {
    private int notificationId;
    private int userId;
    private NotificationType type;
    private String message;
    private boolean isRead;
    private LocalDateTime timestamp;

    /**
     * Notification operation.
     * @param userId parameter value
     * @param type parameter value
     * @param message parameter value
     * @param isRead parameter value
     */
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

    /**
     * Notification operation.
     * @param notificationId parameter value
     * @param userId parameter value
     * @param type parameter value
     * @param message parameter value
     * @param isRead parameter value
     * @param timestamp parameter value
     */
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

    /**
     * getNotificationId operation.
     * @return result value
     */
    public int getNotificationId() {
        return notificationId;
    }
    /**
     * getType operation.
     * @return result value
     */
    public NotificationType getType() {
        return type;
    }

    /**
     * getUserId operation.
     * @return result value
     */
    public int getUserId() {
        return userId;
    }

    /**
     * getMessage operation.
     * @return result value
     */
    public String getMessage() {
        return message;
    }

    /**
     * getTimestamp operation.
     * @return result value
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * isRead operation.
     * @return result value
     */
    public boolean isRead() {
        return isRead;
    }
    /**
     * markAsRead operation.
     */
    public void markAsRead() {
        isRead = true;
    }
}
