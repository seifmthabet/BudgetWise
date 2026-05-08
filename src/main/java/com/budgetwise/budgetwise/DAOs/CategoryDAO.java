package com.budgetwise.budgetwise.DAOs;

import com.budgetwise.budgetwise.models.Category;
import com.budgetwise.budgetwise.utils.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CategoryDAO component.
 */
public class CategoryDAO implements GenericDAO<Category> {

    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("category_id"),
                 rs.getObject("user_id") != null
                ? rs.getInt("user_id")
                : null,
                rs.getString("name"),
                rs.getBoolean("is_default")
        );
    }

    /**
     * save operation.
     * @param entity parameter value
     */
    @Override
    public void save(Category entity) {
        String query = "INSERT INTO categories (user_id, name, is_default) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, entity.getUserId());
            stmt.setString(2, entity.getName());
            stmt.setBoolean(3, entity.isDefault());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save category", e);
        }
    }

    /**
     * findById operation.
     * @param id parameter value
     * @return result value
     */
    @Override
    public Category findById(int id) {
        String query = "SELECT * FROM categories WHERE category_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find category by ID", e);
        }
        return null;
    }

    /**
     * findAll operation.
     * @return result value
     */
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all categories", e);
        }
        return categories;
    }

    /**
     * findDefault operation.
     * @return result value
     */
    public List<Category> findDefault() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE is_default = 1 ";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all categories", e);
        }
        return categories;
    }

    /**
     * update operation.
     * @param entity parameter value
     */
    @Override
    public void update(Category entity) {
        String query = "UPDATE categories SET name = ?, is_default = ? WHERE category_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, entity.getName());
            stmt.setBoolean(2, entity.isDefault());
            stmt.setInt(3, entity.getCategoryId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update category", e);
        }
    }

    /**
     * delete operation.
     * @param id parameter value
     */
    @Override
    public void delete(int id) {
        String query = "DELETE FROM categories WHERE category_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete category", e);
        }
    }

    /**
     * findByUser operation.
     * @param userId parameter value
     * @return result value
     */
    public List<Category> findByUser(int userId) {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch categories by user", e);
        }
        return categories;
    }
    /**
     * existsDefaultCategoryByName operation.
     * @param name parameter value
     * @return result value
     */
    public boolean existsDefaultCategoryByName(String name) {
        String query = "SELECT 1 FROM categories WHERE name = ? AND is_default = 1 LIMIT 1";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch default category", e);
        }
    }
}
