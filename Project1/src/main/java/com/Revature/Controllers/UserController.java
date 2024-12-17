package com.Revature.Controllers;

import com.Revature.Models.DTOs.OutgoingReimbursement;
import com.Revature.Models.DTOs.OutgoingUser;
import com.Revature.Models.Reimbursement;
import com.Revature.Models.User;
import com.Revature.Services.ReimbursementService;
import com.Revature.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;
    private final ReimbursementService reimbursementService;

    @Autowired
    public UserController(UserService userService, ReimbursementService reimbursementService) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
    }

    /*
    validate if manager

     */

    @GetMapping()
    public ResponseEntity<List<OutgoingUser>> getAllUsers(@RequestBody User user){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("login")
    public ResponseEntity<OutgoingUser> login(@RequestBody User user){
        OutgoingUser valid_user = userService.login(user);

        //return ResponseEntity.noContent().header("username", valid_user.getusername()).build();
        //need user id for future calls
        return ResponseEntity.ok(valid_user);
    }


    @PostMapping("register")
    public ResponseEntity<OutgoingUser> register(@RequestBody User user){
        OutgoingUser saved_user = userService.register(user);

        //return ResponseEntity.status(HttpStatus.CREATED).body("succesfully registered");
        //need user id for future calls
        return ResponseEntity.status(HttpStatus.CREATED).body(saved_user);
    }



    @PatchMapping("{userId}/delete")
    public  ResponseEntity<String>  deleteUser(@PathVariable int userId){
        OutgoingUser valid_user = userService.validate_session(userId);
        userService.delete_user(valid_user);

        return ResponseEntity.accepted().body("successfully deleted");
    }




    @GetMapping("/{userId}/reimbursements")
    public ResponseEntity<List<Reimbursement>> viewReimbursments(@PathVariable int userId){
        System.out.println("reached this endpoint");
        userService.validate_session(userId);

        return ResponseEntity.ok().body(reimbursementService.getReimbursements(userId));
    }


    @GetMapping("/{userId}/reimbursements/pending")
    public ResponseEntity<List<Reimbursement>> viewReimbursmentsPending(@PathVariable int userId){
        OutgoingUser valid_user = userService.validate_session(userId);

        return ResponseEntity.ok().body(reimbursementService.getReimbursmentsByStatus(valid_user, "Pending"));
    }


    @PatchMapping("{userId}/reimbursements/delete")
    public  ResponseEntity<String>  deleteReimbursement(@PathVariable int userId, Reimbursement reimbursement){
        User valid_user = userService.validate_session(userId);
        Reimbursement valid_r = reimbursementService.validateReimbursement(valid_user, reimbursement.getReimbursementId());

        reimbursementService.deleteReimbursement(valid_r);

        return ResponseEntity.accepted().body("successfully deleted");
    }








}
