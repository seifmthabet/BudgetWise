package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.UserDAO;
import com.budgetwise.budgetwise.models.User;
import com.budgetwise.budgetwise.utils.PasswordUtil;
import com.budgetwise.budgetwise.utils.Validation;

public class UserService {
    private final UserDAO ud = new UserDAO();
    private  User user = null;
    private Validation valid = new Validation();

    public User register(String name,String email,String password){
        user = new User(name,email,password);
        if(ud.existEmail(email)){
           return null;
        }
        ud.save(user);
        return user;
    }


}
