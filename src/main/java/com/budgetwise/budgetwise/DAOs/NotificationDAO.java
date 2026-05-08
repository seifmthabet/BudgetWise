package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Notification;
import com.budgetwise.budgetwise.models.enums.NotificationType;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NotificationDAO component.
 */
public class NotificationDAO implements GenericDAO<Notification> {

    private Notification mapResultSetToNotification(ResultSet rs) throws SQLException {
        return new Notification(
                rs.getInt("notification_id"),
                rs.getInt("user_id"),
                NotificationType.valueOf(rs.getString("type")),
                rs.getString("message"),
                rs.getBoolean("is_read"),
                rs.getTimestamp("timestamp").toLocalDateTime());
    }

    /**
     * save operation.
     * @param entity parameter value
     */
    public void save(Notification entity){
        String query = "INSERT INTO notifications (user_id,type,message,is_read,timestamp) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, entity.getUserId());
            stmt.setString(2, entity.getType().name());
            stmt.setString(3, entity.getMessage());
            stmt.setBoolean(4, entity.isRead());
            stmt.setTimestamp(5, Timestamp.valueOf(entity.getTimestamp()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save notification", e);
        }
    }

    /**
     * findById operation.
     * @param id parameter value
     * @return result value
     */
    public Notification findById(int id){
        String query = "Select * FROM notifications WHERE notification_id = ?";
        Notification entity = null;

        try(Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    entity = mapResultSetToNotification(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find notification by ID", e);
        }

        return entity;
    }

    /**
     * findAll operation.
     * @return result value
     */
    public List<Notification> findAll(){
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifications ORDER BY timestamp DESC";

        try(Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notifications.add(mapResultSetToNotification(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch notifications:", e);
        }

        return notifications;
    }

    /**
     * update operation.
     * @param entity parameter value
     */
    public void update(Notification entity) {
        String query = "UPDATE notifications SET is_read = ? WHERE notification_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, true);
            stmt.setInt(2, entity.getNotificationId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update notification:", e);
        }
    }

    /**
     * delete operation.
     * @param id parameter value
     */
    public void delete(int id) {
        String query = "DELETE FROM notifications WHERE notification_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete notification:", e);
        }
    }

    /**
     * findAllByUserId operation.
     * @param id parameter value
     * @return result value
     */
    public List<Notification> findAllByUserId(int id) {
        String query = "SELECT * FROM notifications WHERE user_id = ? ORDER BY timestamp DESC";
        List<Notification> notifications = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notifications.add(mapResultSetToNotification(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch notifications by user ID:", e);
        }

        return notifications;
    }

    /**
     * findUnreadByUserId operation.
     * @param id parameter value
     * @return result value
     */
    public List<Notification> findUnreadByUserId(int id) {
        String query = "SELECT * FROM notifications WHERE user_id = ? AND is_read = false ORDER BY timestamp DESC";
        List<Notification> notifications = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notifications.add(mapResultSetToNotification(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch notifications by user ID:", e);
        }


        return notifications;
    }
}
