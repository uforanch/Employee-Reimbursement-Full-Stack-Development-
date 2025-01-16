package com.Revature.Services;

import com.Revature.Aspects.AuthAspect;
import com.Revature.DAOs.ReimbursementDAO;
import com.Revature.DAOs.UserDAO;
import com.Revature.Exception.InvalidAccountException;
import com.Revature.Exception.UnauthorizedLogin;
import com.Revature.Models.DTOs.OutgoingReimbursement;
import com.Revature.Models.DTOs.OutgoingUser;
import com.Revature.Models.Reimbursement;
import com.Revature.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReimbursementService {
    UserDAO userDAO;
    ReimbursementDAO reimbursementDAO;

    @Autowired
    public ReimbursementService(UserDAO userDAO, ReimbursementDAO reimbursementDAO) {
        this.userDAO = userDAO;
        this.reimbursementDAO = reimbursementDAO;
    }


    public OutgoingReimbursement getReimbursment(int r_id){
        Optional<Reimbursement> optional_valid_r = reimbursementDAO.findById(r_id);
        if (optional_valid_r.isEmpty()){
            throw new IllegalArgumentException("No such reimbursement");
        }
        Reimbursement valid_r = optional_valid_r.get();
        User loggedUser = userDAO.findUserByUsername(AuthAspect.getSessionUsername());
        if (!valid_r.getUser().getUserId().equals(loggedUser.getUserId() ) && !AuthAspect.getSessionUserRoles().contains("Manager")){
            throw  new InvalidAccountException("Other user's reimbursmenet not viewable");
        }
        return new OutgoingReimbursement(valid_r);
    }

    public OutgoingReimbursement createReimbursement(OutgoingReimbursement outgoingReimbursement){


        Optional<User> optional_valid_user = userDAO.findById(outgoingReimbursement.getUser().getUserId());

        if (optional_valid_user.isEmpty()){
            throw new UnauthorizedLogin("User does not exist");
        }
        Reimbursement reimbursement = new Reimbursement(outgoingReimbursement.getReimbursementId(),
                outgoingReimbursement.getValue(),
                outgoingReimbursement.getDescription(),
                outgoingReimbursement.getStatus(),
                outgoingReimbursement.getDate_issued(),
                optional_valid_user.get());
        //status is pending on creation
        if (!"Pending".equals(reimbursement.getStatus())&&(!AuthAspect.getSessionUserRoles().contains("Manager"))){
            throw new IllegalArgumentException("Unauthorized to create non pending reimbursment");
        }
        User loggedUser = userDAO.findUserByUsername(AuthAspect.getSessionUsername());
        if ((!outgoingReimbursement.getUser().getUserId().equals(loggedUser.getUserId()))&&(!AuthAspect.getSessionUserRoles().contains("Manager"))){
            throw new IllegalArgumentException("Unauthorized to create reimbursment for others if not manager");
        }
        Reimbursement saved_reimbursment = reimbursementDAO.save(reimbursement);
        return new OutgoingReimbursement(saved_reimbursment);
    }

    public List<OutgoingReimbursement> getAllReimbursements(){
        return reimbursementDAO.findAll().stream().map((item)->{return new OutgoingReimbursement(item);}).toList();
    }

    public List<OutgoingReimbursement> getReimbursementsByStatus(String status){
        return reimbursementDAO.findByStatus(status).stream().map((item)->{return new OutgoingReimbursement(item);}).toList();
    }

    public List<OutgoingReimbursement> getReimbursementsByUserId(UUID userId){
        User loggedUser = userDAO.findUserByUsername(AuthAspect.getSessionUsername());
        if (!loggedUser.getUserId().equals(userId)&& !AuthAspect.getSessionUserRoles().contains("Manager")){
            throw new IllegalArgumentException("Unauthorized access to other user reimbursments.");
        }

        return reimbursementDAO.findByUserUserId(userId).stream().map((item)->{return new OutgoingReimbursement(item);}).toList();
    }

    public List<OutgoingReimbursement> getReimburesmentsByStatus(OutgoingUser user, String status){
        Optional<User> valid_user = userDAO.findById(user.getUserId());
        if (valid_user.isEmpty()){
            // error should happen before this but keeping this here
            throw new InvalidAccountException("No such account");
        }

        return reimbursementDAO.findByUserAndStatus(valid_user.get(), status).stream().map((item)->{return new OutgoingReimbursement(item);}).toList();
    }



    public OutgoingReimbursement updateReimbursement(OutgoingReimbursement reimbursement){
        Optional<Reimbursement> oldReimbursementOptional = reimbursementDAO.findById(reimbursement.getReimbursementId());
        if (oldReimbursementOptional.isEmpty()){
            throw new IllegalArgumentException("No such reimburesment");
        }
        Reimbursement oldReimbursement = oldReimbursementOptional.get();
        System.out.println(reimbursement.getDescription());
        oldReimbursement.setDescription(reimbursement.getDescription());

        System.out.println(oldReimbursement);


        if (!AuthAspect.getSessionUserRoles().contains("Manager")){
            if (!oldReimbursement.getStatus().equals(reimbursement.getStatus())){
                throw new InvalidAccountException("Only managers can change status of reimbursement");
            }
            if (oldReimbursement.getValue()!= reimbursement.getValue()){
                throw new InvalidAccountException("Cannot change value on reimbursement unless manager");
            }
        }
        oldReimbursement.setStatus(reimbursement.getStatus());


        System.out.println(oldReimbursement);
        return new OutgoingReimbursement(reimbursementDAO.save(oldReimbursement));
    }

    public void deleteReimbursementById(int reimbursementId){
        Optional<Reimbursement> reimbursement = reimbursementDAO.findById(reimbursementId);
        if (reimbursement.isEmpty()){
            throw new IllegalArgumentException("No such reimbursment");
        }

        User user = reimbursement.get().getUser();
        reimbursementDAO.delete(reimbursement.get());
        user.getReimbursementList().clear();

    }
}
