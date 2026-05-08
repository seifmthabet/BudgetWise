package com.budgetwise.budgetwise.models;

/**
 * Category component.
 */
public class Category {
    private int categoryId;
    private Integer userId;
    private String name;
    private boolean isDefault;

    /**
     * Category operation.
     * @param userId parameter value
     * @param name parameter value
     * @param isDefault parameter value
     */
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
    /**
     * Category operation.
     * @param categoryId parameter value
     * @param userId parameter value
     * @param name parameter value
     * @param isDefault parameter value
     */
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

    /**
     * getCategoryId operation.
     * @return result value
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * getName operation.
     * @return result value
     */
    public String getName() {
        return name;
    }
    /**
     * isDefault operation.
     * @return result value
     */
    public boolean isDefault() {
        return isDefault;
    }

    public int getUserId() {return userId;}
    /**
     * toString operation.
     * @return result value
     */
    @Override
    public String toString() {
        return name;
    }
}
