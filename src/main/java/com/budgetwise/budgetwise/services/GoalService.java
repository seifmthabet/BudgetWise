package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.GoalDAO;
import com.budgetwise.budgetwise.models.Goal;
import com.budgetwise.budgetwise.utils.Validation;

public class GoalService {
    private Goal goal;
    private GoalDAO goalDAO;
    private Validation validator;

    public GoalService (Goal goal, GoalDAO goalDAO, Validation validator){
        this.goal = goal;
        this.goalDAO = goalDAO;
        this.validator = validator;
    }

    public void createGoal(GoalService goalService){
        if (goalService.validator.validateGoal(goalService.goal)){
            goalService.goalDAO.save(goalService.goal);
        }
    }

    public void addContribution(int goalId, Double amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (goalDAO.findById(goalId) == null){
            throw new IllegalArgumentException("Invalid goal ID");
        }
        goalDAO.findById(goalId).addContribution(amount);
    }

    public void getGoals (int userId){
        if (userId <= 0 || goalDAO.findById(userId) == null) {
            throw new IllegalArgumentException("Invalid goal ID");
        }
        goalDAO.findAll(userId);
    }

    public void getProgressPercent (int goalId){
        if (goalId <= 0 || goalDAO.findById(goalId) == null) {
            throw new IllegalArgumentException("Invalid goal ID");
        }
        goal.getProgressPercent();

    }

    public boolean deleteGoal(GoalService goalService, int goalId){
        if (goalId <= 0) {
            throw new IllegalArgumentException("Invalid goal ID");
        }

        if (goalDAO.findById(goalId) == null) {
            return false;
        }

        goalDAO.delete(goalId);
        return true;
    }

}
