package com.budgetwise.budgetwise.utils;

import com.budgetwise.budgetwise.models.Budget;

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
    private boolean validateBudget(Budget budget){
        if(budget.getAlertThreshold() < 0 || budget.getAlertThreshold() > 100){
            alertUtil.showError("Alert threshold must be between 0 and 100");
            return false;
        }
        return true;
    }
}
