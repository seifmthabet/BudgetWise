package com.budgetwise.budgetwise.utils;

public class Validation {
    private AlertUtil alertUtil = new AlertUtil();
    public Boolean validation(String name , String email,String password){
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
}
