package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.CategoryDAO;
import com.budgetwise.budgetwise.models.Category;
import java.util.List;

public class CategoryService {
    private final CategoryDAO categoryDAO = new CategoryDAO();
    public void createCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        categoryDAO.save(category);
    }
    public void deleteCategory(int id) {
        categoryDAO.delete(id);
    }
    public void updateCategory(Category category) {
        categoryDAO.update(category);
    }
    public List<Category> getCategories(int userId) {
        return categoryDAO.findByUser(userId);
    }
}
