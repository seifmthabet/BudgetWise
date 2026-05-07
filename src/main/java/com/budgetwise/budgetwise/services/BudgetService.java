package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.BudgetDAO;
import com.budgetwise.budgetwise.models.Budget;
import com.budgetwise.budgetwise.models.enums.BudgetStatus;

import java.time.LocalDateTime;
import java.util.List;

public class BudgetService {
    private final BudgetDAO budgetDAO ;
    public BudgetService(BudgetDAO budgetDAO){
        this.budgetDAO = budgetDAO;
    }

    public void createBudget(Budget budget){
        Budget existing = budgetDAO.findByCategoryAndDate(budget.getUserId(),budget.getCategoryId(),budget.getStartDate().getMonthValue(),budget.getStartDate().getYear());

        if (existing != null) {
            throw new RuntimeException("Budget already exists for this category in this month");
        }
        budgetDAO.save(budget);
    }
    public Budget findByCategoryAndDate(int userId,int categoryId,LocalDateTime startDate){
        Budget existing = budgetDAO.findByCategoryAndDate(userId,categoryId,startDate.getMonthValue(),startDate.getYear());

        if (existing != null) {
            throw new RuntimeException("Budget already exists for this category in this month");
        }
        return existing;
    }

    public void deleteBudget(Budget budget){
        Budget budget1 = budgetDAO.findById(budget.getBudgetId());
        if(budget1 == null){
            throw new IllegalArgumentException("Budget Not Found");
        }
        budgetDAO.delete(budget.getBudgetId());
    }
    public List<Budget> getBudgets(int user_id){
        return budgetDAO.findByUserId(user_id);
    }

    public BudgetStatus checkBudgetAlert(int user_id , int category_id,int month,int year){
        Budget budget = budgetDAO.findByCategoryAndDate(user_id,category_id,month,year);

        if (budget == null) {
            throw new RuntimeException("Budget not found");
        }

        return budget.getStatus();
    }


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

    public List<Budget> filterById(int userId){
        if(userId <=0){
            throw new IllegalArgumentException("Invalid user ID");
        }
        return budgetDAO.findByUserId(userId);
    }
    public Budget AccessCategory(int userId,int categoryId){
        Budget budget = budgetDAO.findByCategory(userId,categoryId);
        if(budget == null){
            return null;
        }
        return budget;
    }
    public void removeExpiredBudgets() {

        List<Budget> budgets = budgetDAO.findAll();

        for (Budget budget : budgets) {

            if (budget.getEndDate().isBefore(LocalDateTime.now())) {

                budgetDAO.delete(budget.getBudgetId());
            }
        }
    }

}
