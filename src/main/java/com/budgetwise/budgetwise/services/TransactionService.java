package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.TransactionDAO;
import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.models.enums.TransactionType;
import com.budgetwise.budgetwise.utils.Validation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final Validation validator;

    public TransactionService(TransactionDAO transactionDAO, Validation validator) {
        this.transactionDAO = transactionDAO;
        this.validator = validator;
    }

    public void addTransaction(Transaction tx) {
        if (validator.validateTransaction(tx))
            transactionDAO.save(tx);
    }

    public boolean deleteTransaction(int transactionId) {
        if (transactionId <= 0) {
            throw new IllegalArgumentException("Invalid transaction ID");
        }

        if (transactionDAO.findById(transactionId) == null) {
            return false;
        }

        transactionDAO.delete(transactionId);
        return true;
    }

    public List<Transaction> getTransactions(){
        return transactionDAO.findAll();
    }

    public List<Transaction> filterByUserId(int userId){
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return transactionDAO.findByUserId(userId);
    }

    public List<Transaction> filterByCategoryId(int userId, int categoryId){
        if (userId <= 0 || categoryId <= 0) {
            throw new IllegalArgumentException("Invalid user ID or category ID");
        }
        return transactionDAO.findByCategoryId(userId, categoryId);
    }

    public List<Transaction> filterByDateRange(int userId, LocalDateTime startDate, LocalDateTime endDate){
        if (userId <= 0 || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid user ID or date range");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        return transactionDAO.findByDateRange(userId, startDate, endDate);
    }

    public BigDecimal getTotalIncome(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return transactionDAO.findByUserId(userId).stream()
                .filter(tx -> tx.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalExpense(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return transactionDAO.findByUserId(userId).stream()
                .filter(tx -> tx.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
