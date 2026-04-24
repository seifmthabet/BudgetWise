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
    public double getAmount() {
        return amount;
    }
    public double getSpentAmount() {
        return spentAmount;
    }
    public BudgetStatus getStatus() {
        if (spentAmount >= amount) {
            return BudgetStatus.EXCEEDED;
        } else if (spentAmount >= (0.9 * amount)) {
            return BudgetStatus.NEAR_LIMIT;
        } else {
            return BudgetStatus.ON_TRACK;
        }
    }

    private double getRemainingAmount() {
        return amount - spentAmount;
    }
}
