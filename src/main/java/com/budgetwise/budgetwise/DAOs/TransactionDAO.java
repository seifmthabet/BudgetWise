package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.models.enums.PaymentMethod;
import com.budgetwise.budgetwise.models.enums.TransactionType;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                LocalDateTime.parse(rs.getString("date"))
        );
    }

    public void save(Transaction entity) {
        String query = "INSERT INTO transactions (user_id, category_id, amount, date, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, entity.getUserId());
            stmt.setInt(2, entity.getCategoryId());
            stmt.setBigDecimal(3, entity.getAmount());
            stmt.setString(4, entity.getDate().toString());
            stmt.setString(5, entity.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Transaction findById(int id){
        String query = "SELECT * FROM transactions WHERE transaction_id = ?";
        Transaction entity;
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entity = mapResultSetToTransaction(rs);
                return entity;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions";

        try(Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
           ResultSet rs = stmt.executeQuery();

           while (rs.next()) {
               transactions.add(mapResultSetToTransaction(rs));
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    public void update(Transaction entity) {
        String query = "UPDATE transactions SET amount = ?, description = ? WHERE transaction_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBigDecimal(1, entity.getAmount());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getTransactionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM transactions WHERE transaction_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> findByUserId(int userId){
        String query = "SELECT * FROM transactions WHERE user_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
           ResultSet rs = stmt.executeQuery();

           while(rs.next()) {
               transactions.add(mapResultSetToTransaction(rs));
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    public List<Transaction> findByCategoryId(int userId, int categoryId){
        String query = "SELECT * FROM transactions WHERE user_id = ? AND category_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
           stmt.setInt(1, userId);
           stmt.setInt(2, categoryId);
           ResultSet rs = stmt.executeQuery();

           while(rs.next()) {
               transactions.add(mapResultSetToTransaction(rs));
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    public List<Transaction> findByDateRange(int userId, String startDate, String endDate){
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE user_id = ? AND date BETWEEN ? AND ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return transactions;

    }
}
