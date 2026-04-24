package com.budgetwise.budgetwise.models;

import java.time.LocalDate;

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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
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
    private LocalDate createdAt;

    public User(String name, String email, String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.currency = "USD";
        this.language = "en";
        this.createdAt = LocalDate.now();
    }


}
