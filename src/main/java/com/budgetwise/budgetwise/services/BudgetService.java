package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.BudgetDAO;
import com.budgetwise.budgetwise.models.Budget;
import com.budgetwise.budgetwise.models.enums.BudgetStatus;

import java.time.LocalDate;
import java.util.List;

public class BudgetService {
    private final BudgetDAO budgetDAO = new BudgetDAO();

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

        if(budget == null){
            return null;
        }

        return budget.getStatus();
    }
    public void updateSpentAmount(int budget_id,double amount){
        Budget budget = budgetDAO.findById(budget_id);
        budget.setSpentAmount(budget.getSpentAmount()+ amount);
        budgetDAO.update(budget);
    }
    public void updateAmount(int budget_id,double amount){
        Budget budget = budgetDAO.findById(budget_id);
        budget.setAmount(amount);
        budgetDAO.update(budget);
    }
    public void updateEndDate(int budget_id,String date){
        Budget budget = budgetDAO.findById(budget_id);
        budget.setEndDate(LocalDate.parse(date));
        budgetDAO.update(budget);
    }


}
