package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.CategoryDAO;
import com.budgetwise.budgetwise.models.Category;
import java.util.List;

public class CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

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

    public void deleteCategory(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        categoryDAO.delete(categoryId);
    }

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

    public Category findById(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
        return categoryDAO.findById(categoryId);
    }

    public List<Category> getCategories(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return categoryDAO.findByUser(userId);
    }
    public List<Category> getAllDefaults() {
        return categoryDAO.findDefault();
    }
}