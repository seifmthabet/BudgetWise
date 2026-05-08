package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Goal;
import com.budgetwise.budgetwise.models.enums.GoalStatus;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * GoalDAO component.
 */
public class GoalDAO {

    private Goal mapResultSetToGoal(ResultSet rs) throws SQLException {
        String deadlineStr = rs.getString("deadline");
        LocalDateTime deadline = LocalDateTime.parse(deadlineStr);
        return new Goal(
                rs.getInt("goal_id"),
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getDouble("target_amount"),
                rs.getDouble("current_amount"),
                deadline,
                GoalStatus.valueOf(rs.getString("status"))
        );
    }

    /**
     * save operation.
     * @param entity parameter value
     */
    public void save (Goal entity){
        String query = """
                INSERT INTO goals
                (user_id, name, target_amount, current_amount, deadline, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entity.getUserId());
            stmt.setString(2, entity.getName());
            stmt.setDouble(3, entity.getTargetAmount());
            stmt.setDouble(4, entity.getCurrentAmount());
            stmt.setString(5, entity.getDeadline().toString());
            stmt.setString(6, entity.getStatus().toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save goal", e);
        }
    }

    /**
     * findById operation.
     * @param goalId parameter value
     * @return result value
     */
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

    /**
     * findAll operation.
     * @param userId parameter value
     * @return result value
     */
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

    /**
     * update operation.
     * @param entity parameter value
     */
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

    /**
     * delete operation.
     * @param id parameter value
     */
    public void delete(int id) {
        String query = "DELETE FROM goals WHERE goal_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete goal:", e);
        }
    }

    /**
     * findByUserId operation.
     * @param id parameter value
     * @return result value
     */
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
