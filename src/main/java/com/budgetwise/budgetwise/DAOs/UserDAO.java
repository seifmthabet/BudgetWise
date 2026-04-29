package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements GenericDAO<User> {
    public void save(User entity){
        String command = "INSERT INTO users(name,email,password,currency,language,created_at)" +
                "VALUES (?,?,?,?,?,?)";
        try(Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(command);){
            stmt.setString(1,entity.getName());
            stmt.setString(2,entity.getEmail());
            stmt.setString(3,entity.getPassword());
            stmt.setString(4,entity.getCurrency());
            stmt.setString(5,entity.getLanguage());
            stmt.setString(6, entity.getCreatedAt().toString());
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public com.budgetwise.budgetwise.models.User findById(int id){
        com.budgetwise.budgetwise.models.User entity = null;

        return entity;
    }

    public List<com.budgetwise.budgetwise.models.User> findAll(){
        List<com.budgetwise.budgetwise.models.User> entity = null;
        return entity;
    }

    public void update(com.budgetwise.budgetwise.models.User entity){

    }

    public void delete(int id) {

    }
    public Boolean existEmail(String email){
        String command = "SELECT 1 FROM users WHERE email = ?";
        try(Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(command)) {
            stmt.setString(1,email);

            ResultSet re = stmt.executeQuery();
            return re.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
