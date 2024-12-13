package com.Revature.Controllers;

import com.Revature.Models.Reimbursement;
import com.Revature.Models.User;
import com.Revature.Services.ReimbursementService;
import com.Revature.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("{userId}/reimbursements")
    public ResponseEntity<Reimbursement> createReimbursement(@PathVariable int userId, @RequestBody Reimbursement reimbursment){
        User valid_user=userService.validate_session(userId);

        reimbursment.setUser(valid_user);


        Reimbursement saved_reimbursement = reimbursementService.createReimbursement(reimbursment);
        return ResponseEntity.ok(saved_reimbursement);
    }

    @GetMapping("{userId}/reimbursements")
    public ResponseEntity<List<Reimbursement>> viewReimbursments(@PathVariable int userId){
        userService.validate_session(userId);

        return ResponseEntity.ok().body(reimbursementService.getReimbursements(userId));
    }

    


}
