package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Notification;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO implements GenericDAO<Notification> {
    public void save(Notification entity){
        String query = "INSERT INTO notifications (user_id,type,message,is_read,timestamp) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, entity.getUserId());
            stmt.setString(2, entity.getType());
            stmt.setString(3, entity.getMessage());
            stmt.setBoolean(4, entity.isRead());
            stmt.setString(5, entity.getTimestamp().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Notification findById(int id){
        String query = "Select * FROM notifications WHERE notification_id = ?";
        Notification entity = null;

        try(Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entity = new Notification(
                        rs.getInt("notification_id"),
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("message"),
                        rs.getBoolean("is_read"),
                        LocalDate.parse(rs.getString("timestamp"))
                );

                return entity;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Notification> findAll(){
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifications";

        try(Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                notifications.add(new Notification(
                        rs.getInt("notification_id"),
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getString("message"),
                        rs.getBoolean("is_read"),
                        LocalDate.parse(rs.getString("timestamp"))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    public void update(Notification entity) {
        String query = "UPDATE notifications SET is_read = ? WHERE notification_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, true);
            stmt.setInt(2, entity.getNotificationId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM notifications WHERE notification_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Notification> findAllByUserId(int id) {
        List<Notification> notifications = findAll();

        notifications.stream().filter(n -> n.getUserId() == id);

        return notifications;
    }

    public List<Notification> findUnreadByUserId(int id) {
        List<Notification> notifications = findAllByUserId(id);

        notifications.stream().filter(n -> !n.isRead());

        return notifications;
    }
}
