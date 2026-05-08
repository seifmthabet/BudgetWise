package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.CategoryDAO;
import com.budgetwise.budgetwise.models.Category;
import java.util.List;

/**
 * CategoryService component.
 */
public class CategoryService {
    private final CategoryDAO categoryDAO;

    /**
     * CategoryService operation.
     * @param categoryDAO parameter value
     */
    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    /**
     * createCategory operation.
     * @param category parameter value
     */
    public void createCategory(Category category) {
        if (categoryDAO.existsDefaultCategoryByName(category.getName())) {
            throw new IllegalArgumentException("Cannot use default category name");
        }
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        if (!category.getName().matches("^[a-zA-Z0-9\\s&]+$"))
            throw new IllegalArgumentException("Category name contains invalid characters");
        if (category.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (category.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        categoryDAO.save(category);
    }

    /**
     * deleteCategory operation.
     * @param categoryId parameter value
     */
    public void deleteCategory(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        categoryDAO.delete(categoryId);
    }

    /**
     * updateCategory operation.
     * @param category parameter value
     */
    public void updateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (category.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        if (!category.getName().matches("^[a-zA-Z0-9\\s&]+$"))
            throw new IllegalArgumentException("Category name contains invalid characters");
        if (categoryDAO.existsDefaultCategoryByName(category.getName())) {
            throw new IllegalArgumentException("Cannot use default category name");
        }
        categoryDAO.update(category);
    }

    /**
     * findById operation.
     * @param categoryId parameter value
     * @return result value
     */
    public Category findById(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        return categoryDAO.findById(categoryId);
    }

    /**
     * getCategories operation.
     * @param userId parameter value
     * @return result value
     */
    public List<Category> getCategories(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return categoryDAO.findByUser(userId);
    }
    /**
     * getAllDefaults operation.
     * @return result value
     */
    public List<Category> getAllDefaults() {
        return categoryDAO.findDefault();
    }
}
