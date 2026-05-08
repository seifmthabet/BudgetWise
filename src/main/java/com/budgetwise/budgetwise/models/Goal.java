package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.models.enums.GoalStatus;

import java.time.LocalDateTime;

/**
 * Goal component.
 */
public class Goal {
    private int goalId;
    private int userId;
    private String name;
    private double targetAmount;
    private double currentAmount;
    private LocalDateTime deadline;
    private GoalStatus status;

    /**
     * Goal operation.
     * @param goalId parameter value
     * @param userId parameter value
     * @param name parameter value
     * @param targetAmount parameter value
     * @param currentAmount parameter value
     * @param deadline parameter value
     * @param status parameter value
     */
    public Goal(int goalId, int userId, String name, double targetAmount, double currentAmount, LocalDateTime deadline, GoalStatus status) {
        this.goalId = goalId;
        this.userId = userId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.status = status;
    }
    /**
     * Goal operation.
     * @param userId parameter value
     * @param name parameter value
     * @param targetAmount parameter value
     * @param currentAmount parameter value
     * @param deadline parameter value
     * @param status parameter value
     */
    public Goal(int userId, String name, double targetAmount, double currentAmount, LocalDateTime deadline, GoalStatus status) {
        this.userId = userId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.status = status;
    }

    /**
     * getGoalId operation.
     * @return result value
     */
    public int getGoalId() {
        return goalId;
    }
    public int getUserId() {return userId;}
    public String getName() {return name;}
    public double getTargetAmount() {return targetAmount;}
    public double getCurrentAmount() {return currentAmount;}
    public LocalDateTime getDeadline() {return deadline;}
    public GoalStatus getStatus() {return status;}

    /**
     * getProgressPercent operation.
     * @return result value
     */
    public double getProgressPercent() {
        return (currentAmount / targetAmount) * 100;
    }

    /**
     * isCompleted operation.
     * @return result value
     */
    public boolean isCompleted() {
        return status == GoalStatus.COMPLETED;
    }

    /**
     * addContribution operation.
     * @param amount parameter value
     */
    public void addContribution(double amount) {
        currentAmount += amount;
    }
}
