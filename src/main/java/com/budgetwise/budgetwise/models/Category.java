package com.budgetwise.budgetwise.models;

public class Category {
    private int categoryId;
    private Integer userId;
    private String name;
    private boolean isDefault;

    public Category(Integer userId, String name, boolean isDefault) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Category name cannot be empty");
        if (!name.matches("^[a-zA-Z0-9\\s&]+$"))
            throw new IllegalArgumentException("Category name contains invalid characters");
        if (userId != null && userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }


        this.userId = userId;
        this.name = name;
        this.isDefault = isDefault;
    }
    public Category(int categoryId, Integer userId, String name, boolean isDefault) {

        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Category name cannot be empty");
        if (!name.matches("^[a-zA-Z0-9\\s&]+$"))
            throw new IllegalArgumentException("Category name contains invalid characters");
        if (categoryId <= 0)
            throw new IllegalArgumentException("Category ID must be positive");
        if (userId != null && userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }


        this.categoryId = categoryId;
        this.userId = userId;
        this.name = name;
        this.isDefault = isDefault;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
    public boolean isDefault() {
        return isDefault;
    }

    public int getUserId() {return userId;}
    @Override
    public String toString() {
        return name;
    }
}