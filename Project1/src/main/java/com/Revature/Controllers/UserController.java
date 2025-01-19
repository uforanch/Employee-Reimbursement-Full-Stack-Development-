package com.Revature.Controllers;

import com.Revature.Aspects.AuthAspect;
import com.Revature.Aspects.ManagerOnly;
import com.Revature.Models.DTOs.OutgoingReimbursement;
import com.Revature.Models.DTOs.OutgoingUser;
import com.Revature.Models.Reimbursement;
import com.Revature.Models.User;
import com.Revature.Services.ReimbursementService;
import com.Revature.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {
    private final UserService userService;
    private final ReimbursementService reimbursementService;

    @Autowired
    public UserController(UserService userService, ReimbursementService reimbursementService) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
    }

    @ManagerOnly
    @GetMapping()
    public ResponseEntity<List<OutgoingUser>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @ManagerOnly
    @PatchMapping("{userId}/delete")
    public  ResponseEntity<String>  deleteUser(@PathVariable String userId){
        OutgoingUser valid_user = new OutgoingUser(userService.getUserByShortId(userId));
        userService.deleteUser(valid_user);
        return ResponseEntity.accepted().body("successfully deleted");
    }

    @PatchMapping()
    public  ResponseEntity<OutgoingUser>  updateUser(@RequestBody User update){
        System.out.println(update);
        return ResponseEntity.accepted().body(userService.updateUser(update));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<OutgoingUser> getUserProfile(@PathVariable String userId){
        return ResponseEntity.ok( new OutgoingUser(userService.getUserByShortId(userId)));
    }

    @GetMapping("/{userId}/reimbursements")
    public ResponseEntity<List<OutgoingReimbursement>> viewReimbursmentsForUser(@PathVariable String userId){
        return ResponseEntity.ok().body(reimbursementService.getReimbursementsByUserId(userId));
    }

    @GetMapping("/{userId}/reimbursements/pending")
    public ResponseEntity<List<OutgoingReimbursement>> viewReimbursmentsPending(@PathVariable String userId){
        User valid_user = AuthAspect.getSessionUser();
        if (!valid_user.getShortId().equals(userId) && !AuthAspect.getSessionUserRoles().contains("Manager")){
            throw new IllegalArgumentException("Unauthorized access to other user reimbursments.");
        }
        User searchedUser = userService.getUserByShortId(userId);

        return ResponseEntity.ok().body(reimbursementService.getReimburesmentsByStatus(new OutgoingUser(searchedUser), "Pending"));
    }








}
