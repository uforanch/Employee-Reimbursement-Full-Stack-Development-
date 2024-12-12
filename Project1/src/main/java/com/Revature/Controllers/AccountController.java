package com.Revature.Controllers;

import com.Revature.Models.User;
import com.Revature.Services.ReimbursementService;
import com.Revature.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AccountController {
    private final UserService userService;
    private final ReimbursementService reimbursementService;

    @Autowired
    public AccountController(UserService userService, ReimbursementService reimbursementService) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user){
        User saved_user = userService.register(user);
        return ResponseEntity.ok(saved_user);
    }

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody User user){
        User valid_user = userService.login(user);
        return ResponseEntity.ok(valid_user);
    }


}
