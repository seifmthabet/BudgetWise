package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.models.enums.GoalStatus;

import java.time.LocalDate;

public class Goal {
    private int goalId;
    private int userId;
    private String name;
    private double targetAmount;
    private double currentAmount;
    private LocalDate deadline;
    private GoalStatus status;

    public Goal(int userId, String name, double targetAmount, double currentAmount, LocalDate deadline, GoalStatus status) {
        this.userId = userId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.status = status;
    }

    public int getGoalId() {
        return goalId;
    }

    public double getProgressPercent() {
        return (currentAmount / targetAmount) * 100;
    }

    public boolean isCompleted() {
        return status == GoalStatus.COMPLETED;
    }

    public void addContribution(double amount) {
        currentAmount += amount;
    }
}
