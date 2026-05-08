package com.budgetwise.budgetwise.models;


import java.time.LocalDateTime;

/**
 * User component.
 */
public class User {
    /**
     * getEmail operation.
     * @return result value
     */
    public String getEmail() {
        return email;
    }

    /**
     * setEmail operation.
     * @param email parameter value
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getUserId operation.
     * @return result value
     */
    public int getUserId() {
        return userId;
    }

    /**
     * getName operation.
     * @return result value
     */
    public String getName() {
        return name;
    }

    /**
     * setName operation.
     * @param name parameter value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getCreatedAt operation.
     * @return result value
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * setCreatedAt operation.
     * @param createdAt parameter value
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * getLanguage operation.
     * @return result value
     */
    public String getLanguage() {
        return language;
    }

    /**
     * setLanguage operation.
     * @param language parameter value
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * getCurrency operation.
     * @return result value
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * setCurrency operation.
     * @param currency parameter value
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * getPassword operation.
     * @return result value
     */
    public String getPassword() {
        return password;
    }

    /**
     * setPassword operation.
     * @param password parameter value
     */
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

    /**
     * User operation.
     * @param name parameter value
     * @param email parameter value
     * @param password parameter value
     * @param currency parameter value
     */
    public User(String name, String email, String password, String currency)
    {
        if (name == null || name.trim().isEmpty() ) throw new IllegalArgumentException("Name cannot be empty");
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email cannot be empty");
        if (!email.matches("^[A-Za-z0-9]+@(.+)$")) throw new IllegalArgumentException("Email Is Invalid ");
        if (password == null|| password.trim().isEmpty()) throw new IllegalArgumentException("Password cannot be empty");
        if(password.length()<8){throw new IllegalArgumentException("Password must be at least 6 characters");}
        this.name = name;
        this.email = email;
        this.password = password;
        this.currency = currency;
        this.language = "en";
        this.createdAt = LocalDateTime.now();
    }
    /**
     * User operation.
     * @param userId parameter value
     * @param name parameter value
     * @param email parameter value
     * @param password parameter value
     * @param currency parameter value
     * @param language parameter value
     * @param createdAt parameter value
     */
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
