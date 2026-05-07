package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.UserDAO;
import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.utils.PasswordUtil;
import com.budgetwise.budgetwise.core.SessionManager;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User register(String name, String email, String password) {

        if (userDAO.emailExists(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        String hashed = PasswordUtil.hashPassword(password);

        User user = new User(name, email, hashed,"USD");

        userDAO.save(user);

        return userDAO.findByEmail(email);
    }

    public User login(String email, String password) {

        User user = userDAO.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!PasswordUtil.verify(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return user;
    }
    public User findUserById(int userId){
        User user = userDAO.findById(userId);
        if(user == null){
            throw new IllegalArgumentException("User Not Found");
        }
        return user;
    }
    public void updateUserProfile(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        User existingUser = userDAO.findById(user.getUserId());

        if (existingUser == null) {
            throw new IllegalArgumentException("User Not Found");
        }

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (!user.getEmail().matches("^[A-Za-z0-9]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (user.getCurrency() == null || user.getCurrency().trim().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be empty");
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setCurrency(user.getCurrency());

        userDAO.update(existingUser);

    }

}