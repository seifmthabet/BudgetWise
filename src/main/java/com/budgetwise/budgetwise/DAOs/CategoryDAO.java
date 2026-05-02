package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Category;
import com.budgetwise.budgetwise.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements  GenericDAO<Category> {

    @Override
    public void save(Category entity) {
        String query = "INSERT INTO categories (user_id,name, isDefault) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, entity.getUserId());
            stmt.setString(2, entity.getName());
            stmt.setBoolean(3, entity.isDefault());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category findById(int id) {
        String query = "SELECT * FROM categories WHERE categoryId = ?";
        Category category =  null;
        try(Connection conn = DatabaseManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                category = new Category(
                        rs.getInt("categoryId"),
                        rs.getString("name"),
                        rs.getBoolean("isDefault")
                );
                return  category;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try(Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()) {
                Category category = new Category(
                        rs.getInt("categoryId"),
                        rs.getString("name"),
                        rs.getBoolean("isDefault")
                );
                categories.add(category);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public void update(Category entity) {
        String query = "UPDATE categories SET name = ?, isDefault = ? WHERE categoryId = ?";
        try(Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setString(1, entity.getName());
            stmt.setBoolean(2, entity.isDefault());
            stmt.setInt(3, entity.getCategoryId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM categories WHERE categoryId = ?";
        try(Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Category> findByUser(int userId) {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getBoolean("isDefault")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}