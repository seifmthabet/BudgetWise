package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.enums.BudgetStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Budget component.
 */
public class Budget {
    private int budgetId;
    private int userId;
    private int categoryId;
    private double amount;
    private double spentAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int alertThreshold;

    /**
     * Budget operation.
     * @param userId parameter value
     * @param categoryId parameter value
     * @param amount parameter value
     * @param spentAmount parameter value
     * @param startDate parameter value
     * @param endDate parameter value
     * @param alertThreshold parameter value
     */
    public Budget(int userId, int categoryId, double amount, double spentAmount, LocalDateTime startDate, LocalDateTime endDate, int alertThreshold) {
        if (userId <= 0) throw new IllegalArgumentException("User ID must be positive");
        if (categoryId <= 0) throw new IllegalArgumentException("Category ID must be positive");
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (spentAmount < 0) throw new IllegalArgumentException("SpentAmount must be positive");
        if(startDate == null){throw new IllegalArgumentException("StartTime cannot be empty");}
        if(endDate == null){throw new IllegalArgumentException("EndTime cannot be empty");}
        if (alertThreshold <= 0) throw new IllegalArgumentException("alertThreshold must be positive");
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.spentAmount = spentAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.alertThreshold = alertThreshold;
    }
    /**
     * Budget operation.
     * @param budgetId parameter value
     * @param userId parameter value
     * @param categoryId parameter value
     * @param amount parameter value
     * @param spentAmount parameter value
     * @param startDate parameter value
     * @param endDate parameter value
     * @param alertThreshold parameter value
     */
    public Budget(int budgetId , int userId, int categoryId, double amount, double spentAmount, LocalDateTime startDate, LocalDateTime endDate, int alertThreshold) {
        if (budgetId <= 0) throw new IllegalArgumentException("Budget ID must be positive");
        if (userId <= 0) throw new IllegalArgumentException("User ID must be positive");
        if (categoryId <= 0) throw new IllegalArgumentException("Category must be positive");
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (spentAmount < 0) throw new IllegalArgumentException("SpentAmount must be positive");
        if(startDate == null){throw new IllegalArgumentException("StartTime cannot be empty");}
        if(endDate == null){throw new IllegalArgumentException("EndTime cannot be empty");}
        if (alertThreshold <= 0) throw new IllegalArgumentException("alertThreshold must be positive");
       this.budgetId = budgetId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.spentAmount = spentAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.alertThreshold = alertThreshold;
    }
    /**
     * getBudgetId operation.
     * @return result value
     */
    public int getBudgetId() {
        return budgetId;
    }
    public int getUserId(){return userId;}
    public int getCategoryId(){return categoryId;}
    /**
     * getCategoryName operation.
     * @return result value
     */
    public String getCategoryName(){
        Category category = AppContext.getCategoryDAO().findById(getCategoryId());
        return category.getName();
    }
    /**
     * getAmount operation.
     * @return result value
     */
    public double getAmount() {
        return amount;
    }
    /**
     * getSpentAmount operation.
     * @return result value
     */
    public double getSpentAmount() {
        return spentAmount;
    }
    public LocalDateTime getStartDate(){return startDate;}
    public LocalDateTime getEndDate(){return endDate;}
    public int getAlertThreshold(){return alertThreshold;}

    /**
     * getStatus operation.
     * @return result value
     */
    public BudgetStatus getStatus() {
        if (spentAmount >= amount) {
            return BudgetStatus.EXCEEDED;
        } else if (spentAmount >= (alertThreshold / 100.0) * amount) {
            return BudgetStatus.NEAR_LIMIT;
        } else {
            return BudgetStatus.ON_TRACK;
        }
    }

    /**
     * getRemainingAmount operation.
     * @return result value
     */
    public Double getRemainingAmount() {
        return amount - spentAmount;
    }
    /**
     * setAmount operation.
     * @param amount parameter value
     */
    public void setAmount(double amount){
        this.amount = amount;
    }
    /**
     * setSpentAmount operation.
     * @param amount parameter value
     */
    public void setSpentAmount(double amount){
        this.spentAmount = amount;
    }
    /**
     * setEndDate operation.
     * @param date parameter value
     */
    public void setEndDate(LocalDateTime date){
        this.endDate = date;
    }
    /**
     * toString operation.
     * @return result value
     */
    @Override
    public String toString() {
        return getCategoryName() + " - " + amount;
    }

}
