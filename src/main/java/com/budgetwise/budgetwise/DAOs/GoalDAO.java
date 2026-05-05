package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Goal;
import com.budgetwise.budgetwise.models.enums.GoalStatus;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO {

    private Goal mapResultSetToGoal(ResultSet rs) throws SQLException {
        return new Goal(
                rs.getInt("goal_id"),
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getDouble("target_amount"),
                rs.getDouble("current_amount"),
                rs.getString("deal_line"),
                GoalStatus.valueOf(rs.getString("status"))
        );
    }

    public void save (Goal entity){
        String query = """
                INSERT INTO goals
                (goal_id, user_id, name, target_amount, current_amount, deadline, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entity.getGoalId());
            stmt.setInt(2, entity.getUserId());
            stmt.setString(3, entity.getName());
            stmt.setDouble(4, entity.getTargetAmount());
            stmt.setDouble(5, entity.getCurrentAmount());
            stmt.setTimestamp(6, Timestamp.valueOf(entity.getDeadline()));
            stmt.setString(7, entity.getStatus().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save goal", e);
        }
    }

    public Goal findById(int goalId){
        String query = "SELECT * FROM goals WHERE goal_id = ?";
        Goal entity;
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, goalId);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    entity = mapResultSetToGoal(rs);
                    return entity;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find goal by ID", e);
        }

        return null;
    }

    public List<Goal> findAll(int userId) {
        List<Goal> goals = new ArrayList<>();
        String query = "SELECT * FROM goals WHERE user_id = ?";

        try(Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);

            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    goals.add(mapResultSetToGoal(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch goals:", e);
        }

        return goals;
    }

    public void update(Goal entity) {
        String query = "UPDATE goals SET target_amount = ?, deadline = ? WHERE goal_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, entity.getTargetAmount());
            stmt.setString(2, entity.getDeadline().toString());
            stmt.setInt(3, entity.getGoalId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update goal:", e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM goals WHERE goal_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete goal:", e);
        }
    }

    public Goal findByUserId(int id){
        String query = "SELECT * FROM goals WHERE user_id = ?";
        Goal entity;
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    entity = mapResultSetToGoal(rs);
                    return entity;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find goal by User ID", e);
        }

        return null;
    }
}
