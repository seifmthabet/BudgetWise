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

        User user = new User(name, email, hashed);

        userDAO.save(user);

        return user;
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
}