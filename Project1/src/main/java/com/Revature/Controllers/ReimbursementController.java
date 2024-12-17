package com.Revature.Controllers;


import com.Revature.Models.DTOs.OutgoingReimbursement;
import com.Revature.Models.DTOs.OutgoingUser;
import com.Revature.Models.Reimbursement;
import com.Revature.Models.User;
import com.Revature.Services.ReimbursementService;
import com.Revature.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reimbursements")
@CrossOrigin(origins = "http://localhost:5173")
public class ReimbursementController {
    private final UserService userService;
    private final ReimbursementService reimbursementService;
    @Autowired
    public ReimbursementController(UserService userService, ReimbursementService reimbursementService) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
    }

    /*
    validate if manager
     */
    @GetMapping()
    public ResponseEntity<List<OutgoingReimbursement>> getAllReimbursements(){
        return ResponseEntity.ok(null);
    }
    @PostMapping()
    public ResponseEntity<Reimbursement> createReimbursement(@RequestBody Reimbursement reimbursment){
        Reimbursement saved_reimbursement = reimbursementService.createReimbursement(reimbursment);
        return ResponseEntity.ok(saved_reimbursement);
    }

    /*
     update:
     validate if same user or manager
     */
    @PatchMapping()
    public  ResponseEntity<OutgoingReimbursement> updateReimbursement(@RequestBody Reimbursement reimbursement){
        updateReimbursement(reimbursement);
        return ResponseEntity.ok().body(new OutgoingReimbursement(reimbursement));
    }

    /*
        validate if manager
     */

    @PatchMapping("delete")
    public  ResponseEntity<String>  deleteReimbursement(@PathVariable int userId, Reimbursement reimbursement){
        User valid_user = userService.validate_session(userId);
        Reimbursement valid_r = reimbursementService.validateReimbursement(valid_user, reimbursement.getReimbursementId());

        reimbursementService.deleteReimbursement(valid_r);

        return ResponseEntity.accepted().body("successfully deleted");
    }
}
