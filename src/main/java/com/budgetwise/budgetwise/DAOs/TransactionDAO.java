package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.models.enums.TransactionType;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO implements GenericDAO<Transaction>{
    public void save(Transaction entity) {
        String query = "INSERT INTO transactions (userId, categoryId, amount, date, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, entity.getUserId());
            stmt.setInt(2, entity.getCategoryId());
            stmt.setDouble(3, entity.getAmount());
            stmt.setString(4, entity.getDate().toString());
            stmt.setString(5, entity.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Transaction findById(int id){
        String query = "SELECT * FROM transactions WHERE transactionId = ?";
        Transaction entity = null;
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entity = new Transaction(
                     rs.getInt("user_id")  ,
                     rs.getInt("category_id"),
                     TransactionType.valueOf(rs.getString("type")),
                     rs.getDouble("amount"),
                     rs.getString("description"),
                        rs.getString("payment_method")
                );
                return entity;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Transaction> findAll() {
        List<Transaction> entity = new ArrayList<>();
        String query = "SELECT * FROM transactions";

        try(Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
           ResultSet rs = stmt.executeQuery();

           while (rs.next()) {
               entity.add(new Transaction(
                       rs.getInt("user_id")  ,
                       rs.getInt("category_id"),
                       TransactionType.valueOf(rs.getString("type")),
                       rs.getDouble("amount"),
                       rs.getString("description"),
                       rs.getString("payment_method")
               ));
           }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public void update(Transaction entity) {
        String query = "UPDATE transactions SET amount = ?, description = ? WHERE transactionId = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, entity.getAmount());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getTransactionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM transactions WHERE transactionId = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> findByUserId(int userId){
        List<Transaction> transactions = findAll();

        transactions.stream().filter(t -> t.getUserId() == userId);

        return transactions;
    }

    public List<Transaction> findByCategoryId(int userId, int categoryId){
        List<Transaction> transactions = findAll();

        transactions.stream().filter(t -> t.getUserId() == userId && t.getCategoryId() == categoryId);

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
                transactions.add(new Transaction(
                        rs.getInt("user_id")  ,
                        rs.getInt("category_id"),
                        TransactionType.valueOf(rs.getString("type")),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("payment_method")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;

    }
}
