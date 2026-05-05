package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.models.enums.BudgetStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Budget {
    private int budgetId;
    private int userId;
    private int categoryId;
    private double amount;
    private double spentAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int alertThreshold;

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
    public int getBudgetId() {
        return budgetId;
    }
    public int getUserId(){return userId;}
    public int getCategoryId(){return categoryId;}
    public double getAmount() {
        return amount;
    }
    public double getSpentAmount() {
        return spentAmount;
    }
    public LocalDateTime getStartDate(){return startDate;}
    public LocalDateTime getEndDate(){return endDate;}
    public int getAlertThreshold(){return alertThreshold;}

    public BudgetStatus getStatus() {
        if (spentAmount >= amount) {
            return BudgetStatus.EXCEEDED;
        } else if (spentAmount >= (alertThreshold / 100.0) * amount) {
            return BudgetStatus.NEAR_LIMIT;
        } else {
            return BudgetStatus.ON_TRACK;
        }
    }

    private double getRemainingAmount() {
        return amount - spentAmount;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }
    public void setSpentAmount(double amount){
        this.spentAmount = amount;
    }
    public void setEndDate(LocalDateTime date){
        this.endDate = date;
    }
    public void setBudgetId(int budgetId){
        this.budgetId = budgetId;
    }


}
