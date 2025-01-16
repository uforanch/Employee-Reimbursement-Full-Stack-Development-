package com.Revature.Controllers;


import com.Revature.Aspects.ManagerOnly;
import com.Revature.Models.DTOs.OutgoingReimbursement;
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
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
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

    @ManagerOnly
    @GetMapping()
    public ResponseEntity<List<OutgoingReimbursement>> getAllReimbursements(){
        return ResponseEntity.ok(reimbursementService.getAllReimbursements());
    }

    @GetMapping("/{r_id}")
    public ResponseEntity<OutgoingReimbursement> getReimbursement(@PathVariable int r_id){
        return ResponseEntity.ok(reimbursementService.getReimbursment(r_id));
    }


    @ManagerOnly
    @GetMapping("/pending")
    public ResponseEntity<List<OutgoingReimbursement>> getAllReimbursementsByStatus(){
        return ResponseEntity.ok(reimbursementService.getReimbursementsByStatus("Pending"));
    }



    @PostMapping()
    public ResponseEntity<OutgoingReimbursement> createReimbursement(@RequestBody OutgoingReimbursement reimbursment){
        return ResponseEntity.ok(reimbursementService.createReimbursement(reimbursment));
    }

    /*
     update:
     validate if same user or manager
     */
    @PatchMapping()
    public  ResponseEntity<OutgoingReimbursement> updateReimbursement(@RequestBody OutgoingReimbursement reimbursement){
        System.out.println(reimbursement);
        return ResponseEntity.ok().body(reimbursementService.updateReimbursement(reimbursement));
    }

    @ManagerOnly
    @PatchMapping("/{reimbursementId}/delete")
    public  ResponseEntity<String>  deleteReimbursement(@PathVariable int reimbursementId){
        reimbursementService.deleteReimbursementById(reimbursementId);
        return ResponseEntity.accepted().body("successfully deleted");
    }
}
