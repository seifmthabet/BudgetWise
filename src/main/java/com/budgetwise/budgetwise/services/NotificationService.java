package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.NotificationDAO;
import com.budgetwise.budgetwise.models.Notification;
import com.budgetwise.budgetwise.models.enums.NotificationType;

import java.util.List;

/**
 * NotificationService component.
 */
public class NotificationService {
    private final NotificationDAO notificationDAO;

    /**
     * NotificationService operation.
     * @param notificationDAO parameter value
     */
    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    /**
     * sendNotification operation.
     * @param userId parameter value
     * @param type parameter value
     * @param message parameter value
     */
    public void sendNotification(int userId, NotificationType type, String message) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (type == null || message.isEmpty()) {
            throw new IllegalArgumentException("Invalid notification type or message");
        }
        Notification notification = new Notification(userId, type, message, false);
        notificationDAO.save(notification);
    }

    /**
     * getUnreadNotifications operation.
     * @param userId parameter value
     * @return result value
     */
    public List<Notification> getUnreadNotifications(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return notificationDAO.findUnreadByUserId(userId);
    }

    /**
     * getNotificationById operation.
     * @param notificationId parameter value
     * @return result value
     */
    public Notification getNotificationById(int notificationId) {
        if (notificationId <= 0) {
            throw new IllegalArgumentException("Invalid notification ID");
        }
        return notificationDAO.findById(notificationId);
    }

    /**
     * markAsRead operation.
     * @param notificationId parameter value
     * @return result value
     */
    public boolean markAsRead(int notificationId) {
        if (notificationId <= 0) {
            throw new IllegalArgumentException("Invalid notification ID");
        }

        Notification notification = notificationDAO.findById(notificationId);

        if (notification == null) {
            throw new IllegalArgumentException("Notification not found");
        }
        notification.markAsRead();
        notificationDAO.update(notification);
        return true;
    }

    /**
     * deleteNotification operation.
     * @param notificationId parameter value
     * @return result value
     */
    public boolean deleteNotification(int notificationId) {
        if (notificationId <= 0) {
            throw new IllegalArgumentException("Invalid notification ID");
        }
        if (notificationDAO.findById(notificationId) == null) {
            throw new IllegalArgumentException("Notification not found");
        }
        notificationDAO.delete(notificationId);
        return true;
    }
}
