package com.budgetwise.budgetwise.utils;

import com.budgetwise.budgetwise.models.Transaction;

import java.math.BigDecimal;

public class Validation {
    public Boolean  validateRigester(String name , String email ,String password){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("name cannot be null");

        }
        if(email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("Email cannot be null");

        }
        String emailMatches = "^[A-Za-z0-9]+@(.+)$";
        if(!email.matches(emailMatches)){
            throw new IllegalArgumentException("The Email must have a traditional look");

        }
        if(password == null || password.trim().isEmpty()){
            throw new IllegalArgumentException("Password cannot be null");

        }
        if(password.length()<8){
            throw new IllegalArgumentException("Password must be at least 8 characters");

        }
        return true;
    }
    //Login Validation
    public Boolean validateLogin(String email , String password){
        if(email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("Email cannot be null");
        }
        String emailMatches = "^[A-Za-z0-9]+@(.+)$";
        if(!email.matches(emailMatches)){
            throw new IllegalArgumentException("The Email must have a traditional look");
        }
        if(password == null || password.trim().isEmpty()){
            throw new IllegalArgumentException("Password cannot be null");
        }
        return true;
    }

    public boolean validateTransaction(Transaction tx){
        if (tx == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if (tx.getUserId() <= 0) {
            throw new IllegalArgumentException("User id must be positive");
        }

        if (tx.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Category id must be positive");
        }

        if (tx.getType() == null) {
            throw new IllegalArgumentException("Transaction type is required");
        }

        if (tx.getAmount() == null || tx.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (tx.getPaymentMethod() == null) {
            throw new IllegalArgumentException("Payment method is required");
        }

        if (tx.getDate() == null) {
            throw new IllegalArgumentException("Transaction date is required");
        }

        return true;
    }
}
