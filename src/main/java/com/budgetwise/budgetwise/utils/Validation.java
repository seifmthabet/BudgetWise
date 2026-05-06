package com.budgetwise.budgetwise.utils;

import com.budgetwise.budgetwise.models.Category;
import com.budgetwise.budgetwise.models.Goal;
import com.budgetwise.budgetwise.models.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Validation {
    //  ======================= Rigester Validation ===========================
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
    //  ======================= Login Validation ===========================
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
    //  ======================= Transaction Validation ===========================
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
    //  ======================= Goal Validation ===========================
    public boolean validateGoal(Goal goal){
        if (goal == null) {
            throw new IllegalArgumentException("Goal cannot be null");
        }

        if (goal.getUserId() <= 0) {
            throw new IllegalArgumentException("User id must be positive");
        }

        if (goal.getGoalId() < 0) {
            throw new IllegalArgumentException("Goal id must be positive");
        }

        if (goal.getName() == null) {
            throw new IllegalArgumentException("Name is required");
        }

        if (goal.getTargetAmount() <= goal.getCurrentAmount()) {
            throw new IllegalArgumentException("Target amount must be greater than the current amount");
        }

        if (goal.getCurrentAmount() < 0) {
            throw new IllegalArgumentException("Current amount cannot be negative");
        }

        if (!goal.getDeadline().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("The deadline date is required and must be a future date");
        }

        return true;
    }
    public Boolean validateBudget(Category category , Double amount , LocalDateTime startTime, LocalDateTime endTime){

        if(category == null){
            throw new IllegalArgumentException("Category cannot be null");
        }

        if(amount == null || amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if(startTime == null || endTime == null){
            throw new IllegalArgumentException("Start and End date are required");
        }

        LocalDateTime now = LocalDateTime.now();

        if (startTime.getYear() < now.getYear() ||
                (startTime.getYear() == now.getYear() && startTime.getMonthValue() < now.getMonthValue()) && startTime.getDayOfMonth() < now.getDayOfMonth()) {

            throw new IllegalArgumentException("Cannot create budget for past months");
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        return true;
    }
     public Boolean validateUpdateBudget( Double amount ,LocalDateTime startTime, LocalDateTime endTime){

        if(amount == null || amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        LocalDateTime now = LocalDateTime.now();

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        return true;
    }

}
