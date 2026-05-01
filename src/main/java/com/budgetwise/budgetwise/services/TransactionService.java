package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.TransactionDAO;
import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.models.enums.TransactionType;

import java.util.List;

public class TransactionService {
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final BudgetService budgetService = new BudgetService();

    public void addTransaction(Transaction tx) {
        transactionDAO.save(tx);
    }

    public void deleteTransaction(int transactionId) {
        transactionDAO.delete(transactionId);
    }

    public List<Transaction> getTransactions(){
        return transactionDAO.findAll();
    }

    public List<Transaction> filterByUserId(int userId){
        return transactionDAO.findByUserId(userId);
    }

    public List<Transaction> filterByCategoryId(int userId, int categoryId){
        return transactionDAO.findByCategoryId(userId, categoryId);
    }

    public List<Transaction> filterByDateRange(int userId, String startDate, String endDate){
        return transactionDAO.findByDateRange(userId, startDate, endDate);
    }

    public double getTotalIncome() {
        final double[] income = {0};

        transactionDAO.findAll().forEach(tx -> {
            if (tx.getType() == TransactionType.INCOME) {
                income[0] += tx.getAmount();
            }
        });

        return income[0];
    }

    public double getTotalExpense() {
        final double[] expense = {0};
        transactionDAO.findAll().forEach(tx -> {
            if (tx.getType() == TransactionType.EXPENSE) {
                expense[0] += tx.getAmount();
            }
        });

        return expense[0];
    }


}
