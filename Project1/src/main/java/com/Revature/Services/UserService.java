package com.Revature.Services;

import com.Revature.DAOs.UserDAO;
import com.Revature.Exception.DuplicateUsernameException;
import com.Revature.Exception.InvalidAccountException;
import com.Revature.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    private boolean validateUser(User user){
        if (user.getusername().isBlank()) {
            throw new InvalidAccountException("Username cannot be blank");

        }
        if (userDAO.findUserByUsername(user.getusername()) != null){
            throw new DuplicateUsernameException("Account with username " + user.getusername() + " already exists!");
        }
        if (user.getusername().length() <8){
            throw new InvalidAccountException("Username must be at least 8 characters");
        }
        if (user.getPassword().isBlank()){
            throw new InvalidAccountException("Password cannot be blank");
        }
        if (user.getPassword().length()<8){
            throw new InvalidAccountException("Password must be at least 8 characters");
        }

        return true;
    }
    public User register(User user){
        validateUser(user);
        User saved_user = userDAO.save(user);
        return saved_user;
    }

    public User login(User user){
        User valid_user = userDAO.findUserByUsername(user.getusername());
        if (valid_user == null){
            throw new InvalidAccountException("No user exists for that username");
        }
        if (!user.getPassword().equals(valid_user.getPassword())){

            throw new InvalidAccountException("Incorrect Password");
        }
        return valid_user;
    }

}
