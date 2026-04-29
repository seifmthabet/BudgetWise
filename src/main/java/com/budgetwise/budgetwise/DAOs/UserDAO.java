package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public User findById(int id){
        String command = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(command) ){
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                User user = new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                return user;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        String command = "SELECT * FROM users";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command) ){

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                User user = new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                users.add(user);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    public void update(com.budgetwise.budgetwise.models.User entity){
        String command = "UPDATE users SET name = ?,email = ? , password = ? WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command) ){
            stmt.setString(1,entity.getName());
            stmt.setString(2,entity.getEmail());
            stmt.setString(3,entity.getPassword());
            stmt.setInt(4,entity.getUserId());

            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void delete(int id) {
        String command = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command) ){
            stmt.setInt(1,id);
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
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
    public User findByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                User user = new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
