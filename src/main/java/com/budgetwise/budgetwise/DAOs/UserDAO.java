package com.budgetwise.budgetwise.DAOs;
import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO component.
 */
public class UserDAO implements GenericDAO<User> {

    private User mapResultSetToUser(ResultSet rs) throws SQLException {

        return new User(
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("currency"),
                rs.getString("language"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }


    /**
     * save operation.
     * @param entity parameter value
     */
    public void save(User entity) {
        String sql = """
        INSERT INTO users(name, email, password, currency, language, created_at)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getEmail());
            stmt.setString(3, entity.getPassword());
            stmt.setString(4, entity.getCurrency());
            stmt.setString(5, entity.getLanguage());
            stmt.setTimestamp(6, Timestamp.valueOf(entity.getCreatedAt()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    /**
     * findById operation.
     * @param id parameter value
     * @return result value
     */
    public User findById(int id){
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query) ){
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return mapResultSetToUser(rs);
            }

        }catch (SQLException e){
            throw new RuntimeException("Failed to find User by ID",e);        }

        return null;
    }

    /**
     * findAll operation.
     * @return result value
     */
    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query) ){

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                users.add(mapResultSetToUser(rs));
            }

        }catch (SQLException e){
            throw new RuntimeException("Failed to fetch Users:", e);
        }
        return users;
    }

    /**
     * update operation.
     * @param entity parameter value
     */
    public void update(User entity){
        String command = """
        UPDATE users 
        SET name = ?, email = ?, password = ?, currency = ?, language = ?
        WHERE user_id = ?
    """;

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command)) {

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getEmail());
            stmt.setString(3, entity.getPassword());
            stmt.setString(4, entity.getCurrency());
            stmt.setString(5, entity.getLanguage());
            stmt.setInt(6, entity.getUserId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update Users:", e);
        }
    }

    /**
     * delete operation.
     * @param id parameter value
     */
    public void delete(int id) {
        String command = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(command) ){
            stmt.setInt(1,id);
            stmt.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException("Failed to delete Users:", e);
        }
    }
    /**
     * emailExists operation.
     * @param email parameter value
     * @return result value
     */
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Error while checking if email exists", e);
        }
    }
    /**
     * findByEmail operation.
     * @param email parameter value
     * @return result value
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try(ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error to Save User",e);
        }

        return null;
    }
}
