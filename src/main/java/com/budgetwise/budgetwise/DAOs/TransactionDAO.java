package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.models.enums.PaymentMethod;
import com.budgetwise.budgetwise.models.enums.TransactionType;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TransactionDAO component.
 */
public class TransactionDAO implements GenericDAO<Transaction>{

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getInt("transaction_id"),
                rs.getInt("user_id"),
                rs.getInt("category_id"),
                TransactionType.valueOf(rs.getString("type")),
                rs.getBigDecimal("amount"),
                rs.getString("description"),
                PaymentMethod.valueOf(rs.getString("payment_method")),
                rs.getTimestamp("date").toLocalDateTime()
        );
    }

    /**
     * save operation.
     * @param entity parameter value
     */
    public void save(Transaction entity) {
        String query = """
                INSERT INTO transactions
                (user_id, category_id, type, amount, date, description, payment_method)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entity.getUserId());
            stmt.setInt(2, entity.getCategoryId());
            stmt.setString(3, entity.getType().name());
            stmt.setBigDecimal(4, entity.getAmount());
            stmt.setTimestamp(5, Timestamp.valueOf(entity.getDate()));
            stmt.setString(6, entity.getDescription());
            stmt.setString(7, entity.getPaymentMethod().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save transaction", e);
        }
    }

    /**
     * findById operation.
     * @param id parameter value
     * @return result value
     */
    public Transaction findById(int id){
        String query = "SELECT * FROM transactions WHERE transaction_id = ?";
        Transaction entity;
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    entity = mapResultSetToTransaction(rs);
                    return entity;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find transaction by ID", e);
        }

        return null;
    }

    /**
     * findAll operation.
     * @return result value
     */
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions ORDER BY date DESC";

        try(Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch transactions:", e);
        }

        return transactions;
    }

    /**
     * update operation.
     * @param entity parameter value
     */
    public void update(Transaction entity) {
        String query = "UPDATE transactions SET amount = ?, description = ? WHERE transaction_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBigDecimal(1, entity.getAmount());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getTransactionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update transaction:", e);
        }
    }

    /**
     * delete operation.
     * @param id parameter value
     */
    public void delete(int id) {
        String query = "DELETE FROM transactions WHERE transaction_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete transaction:", e);
        }
    }

    /**
     * findByUserId operation.
     * @param userId parameter value
     * @return result value
     */
    public List<Transaction> findByUserId(int userId){
        String query = "SELECT * FROM transactions WHERE user_id = ? ORDER BY date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);

            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch transactions by user ID:", e);
        }

        return transactions;
    }

    /**
     * findByCategoryId operation.
     * @param userId parameter value
     * @param categoryId parameter value
     * @return result value
     */
    public List<Transaction> findByCategoryId(int userId, int categoryId){
        String query = "SELECT * FROM transactions WHERE user_id = ? AND category_id = ? ORDER BY date DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
           stmt.setInt(1, userId);
           stmt.setInt(2, categoryId);

           try(ResultSet rs = stmt.executeQuery()) {
               while (rs.next()) {
                   transactions.add(mapResultSetToTransaction(rs));
               }
           }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch transactions by category ID:", e);
        }

        return transactions;
    }

    /**
     * findByDateRange operation.
     * @param userId parameter value
     * @param startDate parameter value
     * @param endDate parameter value
     * @return result value
     */
    public List<Transaction> findByDateRange(int userId, LocalDateTime startDate, LocalDateTime endDate){
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE user_id = ? AND date BETWEEN ? AND ? ORDER BY date DESC";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setTimestamp(2, Timestamp.valueOf(startDate));
            stmt.setTimestamp(3, Timestamp.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch transactions by date range:", e);
        }

        return transactions;

    }
}
