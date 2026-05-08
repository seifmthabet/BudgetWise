package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.BudgetDAO;
import com.budgetwise.budgetwise.models.Budget;
import com.budgetwise.budgetwise.models.enums.BudgetStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * BudgetService component.
 */
public class BudgetService {
    private final BudgetDAO budgetDAO ;
    /**
     * BudgetService operation.
     * @param budgetDAO parameter value
     */
    public BudgetService(BudgetDAO budgetDAO){
        this.budgetDAO = budgetDAO;
    }

    /**
     * createBudget operation.
     * @param budget parameter value
     */
    public void createBudget(Budget budget){
        Budget existing = budgetDAO.findByCategoryAndDate(budget.getUserId(),budget.getCategoryId(),budget.getStartDate().getMonthValue(),budget.getStartDate().getYear());

        if (existing != null) {
            throw new RuntimeException("Budget already exists for this category in this month");
        }
        budgetDAO.save(budget);
    }
    /**
     * findByCategoryAndDate operation.
     * @param userId parameter value
     * @param categoryId parameter value
     * @param startDate parameter value
     * @return result value
     */
    public Budget findByCategoryAndDate(int userId,int categoryId,LocalDateTime startDate){
        Budget existing = budgetDAO.findByCategoryAndDate(userId,categoryId,startDate.getMonthValue(),startDate.getYear());

        if (existing != null) {
            throw new RuntimeException("Budget already exists for this category in this month");
        }
        return existing;
    }

    /**
     * deleteBudget operation.
     * @param budget parameter value
     */
    public void deleteBudget(Budget budget){
        Budget budget1 = budgetDAO.findById(budget.getBudgetId());
        if(budget1 == null){
            throw new IllegalArgumentException("Budget Not Found");
        }
        budgetDAO.delete(budget.getBudgetId());
    }
    /**
     * getBudgets operation.
     * @param user_id parameter value
     * @return result value
     */
    public List<Budget> getBudgets(int user_id){
        return budgetDAO.findByUserId(user_id);
    }

    /**
     * checkBudgetAlert operation.
     * @param user_id parameter value
     * @param category_id parameter value
     * @param month parameter value
     * @param year parameter value
     * @return result value
     */
    public BudgetStatus checkBudgetAlert(int user_id , int category_id,int month,int year){
        Budget budget = budgetDAO.findByCategoryAndDate(user_id,category_id,month,year);

        if (budget == null) {
            throw new RuntimeException("Budget not found");
        }

        return budget.getStatus();
    }


    /**
     * updateSpentAmount operation.
     * @param budget_id parameter value
     * @param amount parameter value
     */
    public void updateSpentAmount(int budget_id,double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Budget budget = budgetDAO.findById(budget_id);

        if (budget == null) {
            throw new RuntimeException("Can't Add Amount");
        }
        budget.setSpentAmount(budget.getSpentAmount() + amount);
        Double newSpent =  budget.getSpentAmount();
        budgetDAO.updateSpentAmount(budget_id,newSpent);
    }

    /**
     * updateBudget operation.
     * @param budget_id parameter value
     * @param amount parameter value
     * @param endDate parameter value
     */
    public void updateBudget(int budget_id, double amount,LocalDateTime endDate){

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Budget budget = budgetDAO.findById(budget_id);

        if (budget == null) {
            throw new RuntimeException("Budget not found");
        }

        if (amount < budget.getSpentAmount()) {
            throw new RuntimeException("New amount cannot be less than already spent");
        }
        budget.setEndDate(budget.getEndDate());
        budget.setAmount(amount);
        budget.setEndDate(endDate);
        budgetDAO.update(budget);
    }

    /**
     * filterById operation.
     * @param userId parameter value
     * @return result value
     */
    public List<Budget> filterById(int userId){
        if(userId <=0){
            throw new IllegalArgumentException("Invalid user ID");
        }
        return budgetDAO.findByUserId(userId);
    }
    /**
     * AccessCategory operation.
     * @param userId parameter value
     * @param categoryId parameter value
     * @return result value
     */
    public Budget AccessCategory(int userId,int categoryId){
        Budget budget = budgetDAO.findByCategory(userId,categoryId);
        if(budget == null){
            return null;
        }
        return budget;
    }
    /**
     * removeExpiredBudgets operation.
     */
    public void removeExpiredBudgets() {

        List<Budget> budgets = budgetDAO.findAll();

        for (Budget budget : budgets) {

            if (budget.getEndDate().isBefore(LocalDateTime.now())) {

                budgetDAO.delete(budget.getBudgetId());
            }
        }
    }

}
