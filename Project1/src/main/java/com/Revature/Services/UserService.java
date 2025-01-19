package com.Revature.Services;

import com.Revature.Aspects.AuthAspect;
import com.Revature.DAOs.UserDAO;
import com.Revature.Exception.DuplicateUsernameException;
import com.Revature.Exception.InvalidAccountException;
import com.Revature.Exception.UnauthorizedLogin;
import com.Revature.Models.DTOs.OutgoingUser;
import com.Revature.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private Random random = new Random();

    @Autowired
    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public void setRandom(Random random){
        this.random = random;
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

    private String generateUniqueShortId(){
        String chars = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String out = "";
        for(int i=0;i<10;i++){
            out+=chars.charAt(random.nextInt(chars.length()));
        }
        return out;
    }

    private String getNewShortId(){
        while(true){
            String shortId = generateUniqueShortId();
            Optional<User> otherUser = userDAO.findUserByShortId(shortId);
            if (otherUser.isEmpty()){ return shortId; }
        }
    }

    public OutgoingUser register(User user){
        validateNewUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setShortId(getNewShortId());
        User saved_user = userDAO.save(user);
        return new OutgoingUser(saved_user);
    }

    public OutgoingUser login(String username, String password){
        User valid_user = userDAO.findUserByUsername(username);
        if (valid_user == null){
            throw new InvalidAccountException("No user exists for that username");
        }
        if (password== null){
            throw new InvalidAccountException("Incorrect Password");
        }
        if (password.equals(valid_user.getPassword())){

            throw new InvalidAccountException("Incorrect Password");
        }
        return new OutgoingUser(valid_user);
    }

    public User getUserByUsername(String username){
        User valid_user = userDAO.findUserByUsername(username);
        if (valid_user == null){
            throw new InvalidAccountException("No user exists for that username");
        }
        return valid_user;
    }

    public User getUserByShortId(String shortId){
        Optional<User> optionalUser = userDAO.findUserByShortId(shortId);
        if (optionalUser.isEmpty()){
            throw new InvalidAccountException("No user exists for that Id");
        }
        return optionalUser.get();
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

    public OutgoingUser retrieveUser(UUID userId){
        /*
        Session validation logic goes here
         */
        User valid_user = userDAO.getById(userId);
        if (valid_user == null){
            throw new UnauthorizedLogin("Unauthorized Session");
        }
        return new OutgoingUser(valid_user);
    }

    public User getUserProfile(UUID userId){
        Optional<User> optionalUser = userDAO.findById(userId);
        if (optionalUser.isEmpty()){
            throw new InvalidAccountException("No such user");
        }
        User user = optionalUser.get();
        User loggedUser = AuthAspect.getSessionUser();

        if ((user.getUserId().equals(loggedUser.getUserId()) ) && (!AuthAspect.getSessionUserRoles().contains("Manager"))){
            throw new InvalidAccountException("Unauthorized profile view");
        }
        return user;
    }

    public OutgoingUser updateUser(User user){
        //probably would have done other functions like this if I knew before

        System.out.println("------------------------------");

        System.out.println(AuthAspect.getSessionUserRoles());
        System.out.println(AuthAspect.getSessionUser());
        System.out.println(user.getUserId());


        User loggedUser = AuthAspect.getSessionUser();

        if ((user.getUserId().equals(loggedUser.getUserId()) ) && (!AuthAspect.getSessionUserRoles().contains("Manager"))){
            throw new InvalidAccountException("Unauthorized profile view");
        }
        Optional<User> oldUserOptional = userDAO.findById(user.getUserId());
        if (oldUserOptional.isEmpty()){
            throw new IllegalArgumentException("No such account");
        }

        User oldUser = oldUserOptional.get();
        if ((!oldUser.getRole().equals(user.getRole())) &&  (!AuthAspect.getSessionUserRoles().contains("Manager"))){
            throw new InvalidAccountException("Only mangers can change a users role");
        }

        /*
        TODO: check if passwords match or blank or whatever

         */

        return new OutgoingUser(userDAO.save(user));
    }

}
