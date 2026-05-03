package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.NotificationDAO;
import com.budgetwise.budgetwise.models.Notification;
import com.budgetwise.budgetwise.models.enums.NotificationType;

import java.util.List;

public class NotificationService {
    private final NotificationDAO notificationDAO;

    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

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

    public List<Notification> getUnreadNotifications(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return notificationDAO.findUnreadByUserId(userId);
    }

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

    public boolean deleteNotification(int notificationId) {
        if (notificationId <= 0) {
            throw new IllegalArgumentException("Invalid notification ID");
        }
        if (notificationDAO.findById(notificationId) != null) {
            throw new IllegalArgumentException("Notification not found");
        }
        notificationDAO.delete(notificationId);
        return true;
    }
}
