package com.budgetwise.budgetwise.core;

import com.budgetwise.budgetwise.models.User;

/**
 * SessionManager component.
 */
public class SessionManager {
    private User currentUser;

    /**
     * setCurrentUser operation.
     * @param user parameter value
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * getCurrentUser operation.
     * @return result value
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * clear operation.
     */
    public void clear() {
        currentUser = null;
    }

    /**
     * isLoggedIn operation.
     * @return result value
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
