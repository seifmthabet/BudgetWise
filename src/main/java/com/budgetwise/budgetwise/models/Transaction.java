package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.models.enums.PaymentMethod;
import com.budgetwise.budgetwise.models.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction component.
 */
public class Transaction {
    private int transactionId;
    private int userId;
    private int categoryId;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private PaymentMethod paymentMethod;
    private LocalDateTime timestamp;

    /**
     * Transaction operation.
     * @param userId parameter value
     * @param categoryId parameter value
     * @param type parameter value
     * @param amount parameter value
     * @param description parameter value
     * @param paymentMethod parameter value
     * @param timestamp parameter value
     */
    public Transaction(int userId, int categoryId, TransactionType type, BigDecimal amount, String description, PaymentMethod paymentMethod, LocalDateTime timestamp) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
        if (description == null || description.trim().isEmpty()) throw new IllegalArgumentException("Description cannot be empty");
        if (paymentMethod == null) throw new IllegalArgumentException("Payment method cannot be null");
        if (type == null) throw new IllegalArgumentException("Transaction type cannot be null");
        if (userId <= 0) throw new IllegalArgumentException("User ID must be positive");
        if (categoryId <= 0) throw new IllegalArgumentException("Category ID must be positive");
        this.userId = userId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.timestamp = timestamp;
    }

    /**
     * Transaction operation.
     * @param transactionId parameter value
     * @param userId parameter value
     * @param categoryId parameter value
     * @param type parameter value
     * @param amount parameter value
     * @param description parameter value
     * @param paymentMethod parameter value
     * @param timestamp parameter value
     */
    public Transaction(int transactionId,int userId, int categoryId, TransactionType type, BigDecimal amount, String description, PaymentMethod paymentMethod, LocalDateTime timestamp) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
        if (description == null || description.trim().isEmpty()) throw new IllegalArgumentException("Description cannot be empty");
        if (paymentMethod == null) throw new IllegalArgumentException("Payment method cannot be null");
        if (type == null) throw new IllegalArgumentException("Transaction type cannot be null");
        if (userId <= 0) throw new IllegalArgumentException("User ID must be positive");
        if (categoryId <= 0) throw new IllegalArgumentException("Category ID must be positive");
        if (transactionId <= 0) throw new IllegalArgumentException("Transaction ID must be positive");
        if (timestamp == null) throw new IllegalArgumentException("Timestamp cannot be null");
        this.transactionId = transactionId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.timestamp = timestamp;
    }

    /**
     * getUserId operation.
     * @return result value
     */
    public int getUserId() {
        return userId;
    }

    /**
     * getTransactionId operation.
     * @return result value
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * getCategoryId operation.
     * @return result value
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * getDescription operation.
     * @return result value
     */
    public String getDescription() {
        return description;
    }

    /**
     * getPaymentMethod operation.
     * @return result value
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * getDate operation.
     * @return result value
     */
    public LocalDateTime getDate() {
        return timestamp;
    }

    /**
     * getType operation.
     * @return result value
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * getAmount operation.
     * @return result value
     */
    public BigDecimal getAmount() {
        return amount;
    }

}
