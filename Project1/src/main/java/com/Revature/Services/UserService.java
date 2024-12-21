package com.Revature.Services;

import com.Revature.Aspects.AuthAspect;
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
import java.util.Optional;

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

    public void deleteUser(OutgoingUser user){
        Optional<User> valid_user = userDAO.findById(user.getUserId());
        if (valid_user.isEmpty()){
            // error should happen before this but keeping this here
            throw new InvalidAccountException("No such account");
        }
        userDAO.delete(valid_user.get());
    }



    public List<OutgoingUser> getAllUsers() {
        List<User> listUser = userDAO.findAll();
        List<OutgoingUser> listOut = new ArrayList<>();
        for(User user:listUser){
            listOut.add(new OutgoingUser(user));
        }
        return listOut;


    }

    public OutgoingUser retrieveUser(int userId){
        /*
        Session validation logic goes here
         */
        User valid_user = userDAO.getById(userId);
        if (valid_user == null){
            throw new UnauthorizedLogin("Unauthorized Session");
        }
        return new OutgoingUser(valid_user);
    }

    public User getUserProfile(int userId){
        Optional<User> optionalUser = userDAO.findById(userId);
        if (optionalUser.isEmpty()){
            throw new InvalidAccountException("No such user");
        }
        User user = optionalUser.get();
        System.out.println(AuthAspect.getSessionUsername());
        if ((user.getUserId() != AuthAspect.getSessionUserId()) && (!"Manager".equals(AuthAspect.getSessionUserRole()))){
            throw new InvalidAccountException("Unauthorized profile view");
        }
        return user;
    }

    public OutgoingUser updateUser(User user){
        //probably would have done other functions like this if I knew before

        System.out.println("------------------------------");
        System.out.println(AuthAspect.getSessionUserId());
        System.out.println(AuthAspect.getSessionUserRole());
        System.out.println(AuthAspect.getSessionUsername());
        System.out.println(user.getUserId());



        if ((AuthAspect.getSessionUserId()!=user.getUserId()) && (!"Manager".equals(AuthAspect.getSessionUserRole()))){
            throw new InvalidAccountException("Cannot update others account");
        }
        Optional<User> oldUserOptional = userDAO.findById(user.getUserId());
        if (oldUserOptional.isEmpty()){
            throw new IllegalArgumentException("No such account");
        }

        User oldUser = oldUserOptional.get();
        if ((!oldUser.getRole().equals(user.getRole())) &&  (!"Manager".equals(AuthAspect.getSessionUserRole()))){
            throw new InvalidAccountException("Only mangers can change a users role");
        }

        /*
        TODO: check if passwords match or blank or whatever

         */

        return new OutgoingUser(userDAO.save(user));
    }

}
