package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.GoalDAO;
import com.budgetwise.budgetwise.models.Goal;
import com.budgetwise.budgetwise.utils.Validation;

import java.util.List;

/**
 * GoalService component.
 */
public class GoalService {
    private GoalDAO goalDAO;
    private Validation validator;

    /**
     * GoalService operation.
     * @param goalDAO parameter value
     * @param validator parameter value
     */
    public GoalService (GoalDAO goalDAO, Validation validator){
        this.goalDAO = goalDAO;
        this.validator = validator;
    }

    /**
     * createGoal operation.
     * @param goal parameter value
     */
    public void createGoal(Goal goal){
        if (validator.validateGoal(goal)){
            goalDAO.save(goal);
        }
    }

    /**
     * addContribution operation.
     * @param goalId parameter value
     * @param amount parameter value
     */
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

    /**
     * getGoals operation.
     * @param userId parameter value
     * @return result value
     */
    public List<Goal> getGoals (int userId){
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return goalDAO.findAll(userId);
    }

    /**
     * getProgressPercent operation.
     * @param goalId parameter value
     */
    public void getProgressPercent (int goalId){
        if (goalId <= 0 || goalDAO.findById(goalId) == null) {
            throw new IllegalArgumentException("Invalid goal ID");
        }

        Goal goal = goalDAO.findById(goalId);

        goal.getProgressPercent();

    }

    /**
     * deleteGoal operation.
     * @param goalService parameter value
     * @param goalId parameter value
     * @return result value
     */
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
