package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.models.enums.GoalStatus;

import java.time.LocalDateTime;

public class Goal {
    private int goalId;
    private int userId;
    private String name;
    private double targetAmount;
    private double currentAmount;
    private LocalDateTime deadline;
    private GoalStatus status;

    public Goal(int goalId, int userId, String name, double targetAmount, double currentAmount, LocalDateTime deadline, GoalStatus status) {
        this.goalId = goalId;
        this.userId = userId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.status = status;
    }
    public Goal(int userId, String name, double targetAmount, double currentAmount, LocalDateTime deadline, GoalStatus status) {
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
    public int getUserId() {return userId;}
    public String getName() {return name;}
    public double getTargetAmount() {return targetAmount;}
    public double getCurrentAmount() {return currentAmount;}
    public LocalDateTime getDeadline() {return deadline;}
    public GoalStatus getStatus() {return status;}

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
