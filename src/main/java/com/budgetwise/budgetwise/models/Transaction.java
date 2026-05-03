package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.models.enums.PaymentMethod;
import com.budgetwise.budgetwise.models.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private int userId;
    private int categoryId;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private PaymentMethod paymentMethod;
    private LocalDateTime timestmap;

    public Transaction(int userId, int categoryId, TransactionType type, BigDecimal amount, String description, PaymentMethod paymentMethod) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
        if(description.isEmpty()) throw new IllegalArgumentException("Description cannot be empty");
        if(paymentMethod == null) throw new IllegalArgumentException("Payment method cannot be null");
        if(type == null) throw new IllegalArgumentException("Transaction type cannot be null");
        if(userId <= 0) throw new IllegalArgumentException("User ID must be positive");
        if(categoryId <= 0) throw new IllegalArgumentException("Category ID must be positive");
        this.userId = userId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.timestmap = LocalDateTime.now();
    }

    public Transaction(int transactionId,int userId, int categoryId, TransactionType type, BigDecimal amount, String description, PaymentMethod paymentMethod, LocalDateTime timestamp) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
        if(description.isEmpty()) throw new IllegalArgumentException("Description cannot be empty");
        if(paymentMethod == null) throw new IllegalArgumentException("Payment method cannot be empty");
        if(type == null) throw new IllegalArgumentException("Transaction type cannot be null");
        if(userId <= 0) throw new IllegalArgumentException("User ID must be positive");
        if(categoryId <= 0) throw new IllegalArgumentException("Category ID must be positive");
        if(transactionId <= 0) throw new IllegalArgumentException("Transaction ID must be positive");
        if(timestamp == null) throw new IllegalArgumentException("Timestamp cannot be null");
        this.transactionId = transactionId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.timestmap = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getDate() {
        return timestmap;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
