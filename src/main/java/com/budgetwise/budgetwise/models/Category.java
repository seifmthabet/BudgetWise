package com.budgetwise.budgetwise.models;

public class Category {
    private int categoryId;
    private int userId;
    private String name;
    private boolean isDefault;

    public Category(int userId, String name, boolean isDefault) {
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
}
