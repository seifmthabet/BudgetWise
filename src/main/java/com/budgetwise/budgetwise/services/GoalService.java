package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.GoalDAO;
import com.budgetwise.budgetwise.models.Goal;
import com.budgetwise.budgetwise.utils.Validation;

import java.util.List;

public class GoalService {
    private GoalDAO goalDAO;
    private Validation validator;

    public GoalService (GoalDAO goalDAO, Validation validator){
        this.goalDAO = goalDAO;
        this.validator = validator;
    }

    public void createGoal(Goal goal){
        if (validator.validateGoal(goal)){
            goalDAO.save(goal);
        }
    }

    public void addContribution(int goalId, Double amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        Goal goal = goalDAO.findById(goalId);
        if (goal == null){
            throw new IllegalArgumentException("Invalid goal ID");
        }

        goal.addContribution(amount);

        goalDAO.update(goal);
    }

    public List<Goal> getGoals (int userId){
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return goalDAO.findAll(userId);
    }

    public void getProgressPercent (int goalId){
        if (goalId <= 0 || goalDAO.findById(goalId) == null) {
            throw new IllegalArgumentException("Invalid goal ID");
        }

        Goal goal = goalDAO.findById(goalId);

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
