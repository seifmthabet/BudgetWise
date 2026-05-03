package com.budgetwise.budgetwise.utils;

import com.budgetwise.budgetwise.models.Transaction;

import java.math.BigDecimal;

public class Validation {
    private AlertUtil alertUtil = new AlertUtil();
    //Register Validation
    public Boolean  Rvalidation(String name , String email ,String password){
        if(name == null || name.trim().isEmpty()){
            alertUtil.showError("Name Is Required");
            return false;
        }
        if(email == null || email.trim().isEmpty()){
            alertUtil.showError("Email Is Required");
            return false;
        }
        String emailMatches = "^[A-Za-z0-9]+@(.+)$";
        if(!email.matches(emailMatches)){
            alertUtil.showError("Invalid Email");
            return false;
        }
        if(password == null || password.trim().isEmpty()){
            alertUtil.showError("Password Is Required");
            return false;
        }
        if(password.length()<6){
            alertUtil.showError("Password must be at least 6 characters");
            return false;
        }
        return true;
    }
    //Login Validation
    public Boolean Lvalidation(String email , String password){
        if(email == null || email.trim().isEmpty()){
            alertUtil.showError("Email Is Required");
            return false;
        }
        String emailMatches = "^[A-Za-z0-9]+@(.+)$";
        if(!email.matches(emailMatches)){
            alertUtil.showError("Invalid Email");
            return false;
        }
        if(password == null || password.trim().isEmpty()){
            alertUtil.showError("Password Is Required");
            return false;
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
