package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Budget;
import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO implements GenericDAO<Budget> {
    public void save(Budget entity){
        String command = "INSERT INTO budgets (user_id,category_id,amount,spent_amount,start_date,end_date,alert_threshold)" +
                "VALUES (?,?,?,?,?,?,?)";
        try(Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(command);){
            stmt.setInt(1,entity.getUserId());
            stmt.setInt(2,entity.getCategoryId());
            stmt.setDouble(3,entity.getAmount());
            stmt.setDouble(4,entity.getSpentAmount());
            stmt.setString(5, entity.getStartDate().toString());
            stmt.setString(6, entity.getEndDate().toString());
            stmt.setInt(7,entity.getAlertThreshold());


            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Budget findById(int id){
        String command = "SELECT * FROM budgets WHERE budget_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command) ){
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Budget budget = new Budget(
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getDouble("spent_amount"),
                        LocalDate.parse(rs.getString("start_date")),
                        LocalDate.parse(rs.getString("end_date")),
                        rs.getInt("alert_threshold")
                );
                return budget;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public List<Budget> findAll(){
        List<Budget> budgets = new ArrayList<>();
        String command = "SELECT * FROM budgets";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command) ){

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Budget budget = new Budget(
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getDouble("spent_amount"),
                        LocalDate.parse(rs.getString("start_date")),
                        LocalDate.parse(rs.getString("end_date")),
                        rs.getInt("alert_threshold")
                );
                budgets.add(budget);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return budgets;
    }

    public void update(Budget entity){
        String command = "UPDATE budgets SET amount = ?,spent_amount = ? , end_date = ? WHERE budget_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command) ){
            stmt.setDouble(1,entity.getAmount());
            stmt.setDouble(2,entity.getSpentAmount());
            stmt.setString(3,entity.getEndDate().toString());
            stmt.setInt(4,entity.getBudgetId());

            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void delete(int id) {
        String command = "DELETE FROM budgets WHERE budget_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command) ){
            stmt.setInt(1,id);
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Budget> findByUserId(int id) {
        List<Budget> budgets = new ArrayList<>();

        String sql = "SELECT * FROM budgets WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

                Budget budget = new Budget(
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getDouble("spent_amount"),
                        LocalDate.parse(rs.getString("start_date")),
                        LocalDate.parse(rs.getString("end_date")),
                        rs.getInt("alert_threshold")
                );

                budgets.add(budget);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgets;

    }
}
