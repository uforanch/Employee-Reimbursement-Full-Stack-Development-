package com.Revature.Services;

import com.Revature.DAOs.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

}
