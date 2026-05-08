package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.UserDAO;
import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.utils.PasswordUtil;
import com.budgetwise.budgetwise.core.SessionManager;

/**
 * UserService component.
 */
public class UserService {

    private final UserDAO userDAO;

    /**
     * UserService operation.
     * @param userDAO parameter value
     */
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * register operation.
     * @param name parameter value
     * @param email parameter value
     * @param password parameter value
     * @return result value
     */
    public User register(String name, String email, String password) {

        if (userDAO.emailExists(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        String hashed = PasswordUtil.hashPassword(password);

        User user = new User(name, email, hashed,"USD");

        userDAO.save(user);

        return userDAO.findByEmail(email);
    }

    /**
     * login operation.
     * @param email parameter value
     * @param password parameter value
     * @return result value
     */
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
    /**
     * findUserById operation.
     * @param userId parameter value
     * @return result value
     */
    public User findUserById(int userId){
        User user = userDAO.findById(userId);
        if(user == null){
            throw new IllegalArgumentException("User Not Found");
        }
        return user;
    }
    /**
     * updateUserProfile operation.
     * @param user parameter value
     */
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
