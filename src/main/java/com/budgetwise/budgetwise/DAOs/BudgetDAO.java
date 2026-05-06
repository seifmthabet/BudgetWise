package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Budget;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO implements GenericDAO<Budget> {
    private Budget mapResultSetToBudget(ResultSet rs) throws SQLException {

        return new Budget(
                rs.getInt("budget_id"),
                rs.getInt("user_id"),
                rs.getInt("category_id"),
                rs.getDouble("amount"),
                rs.getDouble("spent_amount"),
                rs.getTimestamp("start_date").toLocalDateTime(),
                rs.getTimestamp("end_date").toLocalDateTime(),
                rs.getInt("alert_threshold")
        );
    }

    public void save(Budget entity) {
        String command = "INSERT INTO budgets (user_id,category_id,amount,start_date,end_date,alert_threshold)" +
                "VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            stmt.setInt(1, entity.getUserId());
            stmt.setInt(2, entity.getCategoryId());
            stmt.setDouble(3, entity.getAmount());
            stmt.setTimestamp(4, Timestamp.valueOf(entity.getStartDate()));
            stmt.setTimestamp(5, Timestamp.valueOf(entity.getEndDate()));
            stmt.setInt(6, entity.getAlertThreshold());


            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save Budget", e);
        }
    }

    public Budget findById(int budgetId) {
        String command = "SELECT * FROM budgets WHERE budget_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            stmt.setInt(1, budgetId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBudget(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find Budget by ID",e);
        }

        return null;
    }

    public List<Budget> findAll() {
        List<Budget> budgets = new ArrayList<>();
        String command = "SELECT * FROM budgets";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                budgets.add(mapResultSetToBudget(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch Budgets:", e);
        }
        return budgets;
    }

    public void update(Budget entity) {
        String command = "UPDATE budgets SET amount = ?, end_date = ? WHERE budget_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            stmt.setDouble(1, entity.getAmount());
            stmt.setTimestamp(2, Timestamp.valueOf(entity.getEndDate()));
            stmt.setInt(3, entity.getBudgetId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update Budgets:", e);
        }

    }
    public void updateSpentAmount(int budgetId, double spentAmount) {
        String sql = "UPDATE budgets SET spent_amount = ? WHERE budget_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, spentAmount);
            stmt.setInt(2, budgetId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update spent amount", e);
        }
    }

    public void delete(int id) {
        String command = "DELETE FROM budgets WHERE budget_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete Budgets:", e);
        }
    }

    public List<Budget> findByUserId(int id) {
        List<Budget> budgets = new ArrayList<>();

        String sql = "SELECT * FROM budgets WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                budgets.add(mapResultSetToBudget(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to Find User's Budgets:", e);
        }
        return budgets;

    }
    
    public Budget findByCategoryAndDate(int user_id, int category_id, int month, int year) {

        String command ="""
        SELECT * FROM budgets WHERE user_id = ? AND category_id = ? AND start_date >= ? AND start_date < ?""";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command)) {

            LocalDate start = LocalDate.of(year, month, 1);
            LocalDate end = start.plusMonths(1);

            stmt.setInt(1, user_id);
            stmt.setInt(2, category_id);
            stmt.setDate(3, java.sql.Date.valueOf(start));
            stmt.setDate(4, java.sql.Date.valueOf(end));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToBudget(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to Find User's Budgets:", e);
        }

        return null;
    }
}

