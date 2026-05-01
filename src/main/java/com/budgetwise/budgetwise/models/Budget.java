package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.models.enums.BudgetStatus;

import java.time.LocalDate;

public class Budget {
    private int budgetId;
    private int userId;
    private int categoryId;
    private double amount;
    private double spentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private int alertThreshold;

    public Budget(int userId, int categoryId, double amount, double spentAmount, LocalDate startDate, LocalDate endDate, int alertThreshold) {
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
    public LocalDate getStartDate(){return startDate;}
    public LocalDate getEndDate(){return endDate;}
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
    public void setEndDate(LocalDate date){
        this.endDate = date;
    }
    public void setBudgetId(int budgetId){
        this.budgetId = budgetId;
    }


}
