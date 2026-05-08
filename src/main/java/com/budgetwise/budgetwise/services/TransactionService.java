package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.TransactionDAO;
import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.models.enums.TransactionType;
import com.budgetwise.budgetwise.utils.Validation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TransactionService component.
 */
public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final Validation validator;

    /**
     * TransactionService operation.
     * @param transactionDAO parameter value
     * @param validator parameter value
     */
    public TransactionService(TransactionDAO transactionDAO, Validation validator) {
        this.transactionDAO = transactionDAO;
        this.validator = validator;
    }

    /**
     * addTransaction operation.
     * @param tx parameter value
     */
    public void addTransaction(Transaction tx) {
        if (validator.validateTransaction(tx))
            transactionDAO.save(tx);
    }

    /**
     * deleteTransaction operation.
     * @param transactionId parameter value
     * @return result value
     */
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

    /**
     * getTransactions operation.
     * @return result value
     */
    public List<Transaction> getTransactions(){
        return transactionDAO.findAll();
    }

    /**
     * filterByUserId operation.
     * @param userId parameter value
     * @return result value
     */
    public List<Transaction> filterByUserId(int userId){
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return transactionDAO.findByUserId(userId);
    }

    /**
     * filterByCategoryId operation.
     * @param userId parameter value
     * @param categoryId parameter value
     * @return result value
     */
    public List<Transaction> filterByCategoryId(int userId, int categoryId){
        if (userId <= 0 || categoryId <= 0) {
            throw new IllegalArgumentException("Invalid user ID or category ID");
        }
        return transactionDAO.findByCategoryId(userId, categoryId);
    }

    /**
     * filterByDateRange operation.
     * @param userId parameter value
     * @param startDate parameter value
     * @param endDate parameter value
     * @return result value
     */
    public List<Transaction> filterByDateRange(int userId, LocalDateTime startDate, LocalDateTime endDate){
        if (userId <= 0 || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid user ID or date range");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        return transactionDAO.findByDateRange(userId, startDate, endDate);
    }

    /**
     * getTotalIncome operation.
     * @param userId parameter value
     * @return result value
     */
    public BigDecimal getTotalIncome(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return transactionDAO.findByUserId(userId).stream()
                .filter(tx -> tx.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * getTotalExpense operation.
     * @param userId parameter value
     * @return result value
     */
    public BigDecimal getTotalExpense(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return transactionDAO.findByUserId(userId).stream()
                .filter(tx -> tx.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * getTotalBalance operation.
     * @param userId parameter value
     * @return result value
     */
    public BigDecimal getTotalBalance(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return getTotalIncome(userId).subtract(getTotalExpense(userId));
    }

}
