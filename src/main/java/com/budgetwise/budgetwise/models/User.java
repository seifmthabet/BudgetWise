package com.budgetwise.budgetwise.models;


import java.time.LocalDateTime;

public class User {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int userId;
    private String name;
    private String email;
    private String password;
    private String currency;
    private String language;
    private LocalDateTime createdAt;

    public User(String name, String email, String password)
    {
        if (name == null || name.trim().isEmpty() ) throw new IllegalArgumentException("Name cannot be empty");
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email cannot be empty");
        if (!email.matches("^[A-Za-z0-9]+@(.+)$")) throw new IllegalArgumentException("Email Is Invalid ");
        if (password == null|| password.trim().isEmpty()) throw new IllegalArgumentException("Password cannot be empty");
        if(password.length()<8){throw new IllegalArgumentException("Password must be at least 6 characters");}
        this.name = name;
        this.email = email;
        this.password = password;
        this.currency = "USD";
        this.language = "en";
        this.createdAt = LocalDateTime.now();
    }
    public User(int userId,String name, String email, String password,String currency,String language,LocalDateTime createdAt)
    {
        if (userId <= 0) throw new IllegalArgumentException("User ID must be positive");
        if (name == null || name.trim().isEmpty() ) throw new IllegalArgumentException("Name cannot be empty");
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email cannot be empty");
        if (!email.matches("^[A-Za-z0-9]+@(.+)$")) throw new IllegalArgumentException("Email Is Invalid ");
        if (password == null|| password.trim().isEmpty()) throw new IllegalArgumentException("Password cannot be empty");
        if(password.length()<8){throw new IllegalArgumentException("Password must be at least 6 characters");}
        if(currency == null){throw new IllegalArgumentException("Currency cannot be empty");}
        if(language == null){throw new IllegalArgumentException("Language cannot be empty");}
        if(createdAt == null){throw new IllegalArgumentException("Time cannot be empty");}
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.currency = currency;
        this.language = language;
        this.createdAt = createdAt;
    }



}
