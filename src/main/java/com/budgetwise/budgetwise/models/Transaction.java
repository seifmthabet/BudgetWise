package com.budgetwise.budgetwise.models;

import com.budgetwise.budgetwise.models.enums.TransactionType;

import java.time.LocalDate;

public class Transaction {
    private int transactionId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private int userId;
    private int categoryId;
    private TransactionType type;
    private double amount;
    private String description;
    private String paymentMethod;
    private LocalDate date;

    public Transaction(int userId, int categoryId, TransactionType type,double amount, String description, String paymentMethod) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.date = LocalDate.now();
    }


}
