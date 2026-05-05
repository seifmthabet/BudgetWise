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
        budgetDAO.save(budget);
    }

    public void updateBudget(Budget budget){
        budgetDAO.update(budget);
    }

    public void deleteBudget(Budget budget){
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
            throw new RuntimeException("Budget not found");
        }

        budget.setSpentAmount(budget.getSpentAmount() + amount);
        budgetDAO.update(budget);
    }

    public void updateAmount(int budget_id,double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Budget budget = budgetDAO.findById(budget_id);

        if (budget == null) {
            throw new RuntimeException("Budget not found");
        }

        budget.setAmount(amount);
        budgetDAO.update(budget);
    }

    public void updateEndDate(int budget_id,String date){
        Budget budget = budgetDAO.findById(budget_id);

        if (budget == null) {
            throw new RuntimeException("Budget not found");
        }

        budget.setEndDate(LocalDateTime.parse(date));
        budgetDAO.update(budget);
    }
}
