package com.Revature.Services;

import com.Revature.DAOs.UserDAO;
import com.Revature.Exception.DuplicateUsernameException;
import com.Revature.Exception.InvalidAccountException;
import com.Revature.Exception.UnauthorizedLogin;
import com.Revature.Models.DTOs.OutgoingUser;
import com.Revature.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    private boolean validateNewUser(User user){
        if (user.getUsername().isBlank()) {
            throw new InvalidAccountException("Username cannot be blank");

        }
        if (userDAO.findUserByUsername(user.getUsername()) != null){
            throw new DuplicateUsernameException("Account with username " + user.getUsername() + " already exists!");
        }
        if (user.getUsername().length() <8){
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
    public OutgoingUser register(User user){
        validateNewUser(user);
        User saved_user = userDAO.save(user);
        return new OutgoingUser(saved_user);
    }

    public OutgoingUser login(User user){
        User valid_user = userDAO.findUserByUsername(user.getUsername());
        if (valid_user == null){
            throw new InvalidAccountException("No user exists for that username");
        }
        if (user.getPassword()== null){
            throw new InvalidAccountException("Incorrect Password");
        }
        if (!user.getPassword().equals(valid_user.getPassword())){

            throw new InvalidAccountException("Incorrect Password");
        }
        return new OutgoingUser(valid_user);
    }

    public void delete_user(User user){
        userDAO.delete(user);
    }



    public List<OutgoingUser> getAllUsers() {
        List<User> listUser = userDAO.findAll();
        List<OutgoingUser> listOut = new ArrayList<>();
        for(User user:listUser){
            listOut.add(new OutgoingUser(user));
        }
        return listOut;


    }

    public OutgoingUser validate_session(int userId){
        /*
        Session validation logic goes here
         */
        User valid_user = userDAO.getById(userId);
        if (valid_user == null){
            throw new UnauthorizedLogin("Unauthorized Session");
        }
        return new OutgoingUser(valid_user);
    }

}
