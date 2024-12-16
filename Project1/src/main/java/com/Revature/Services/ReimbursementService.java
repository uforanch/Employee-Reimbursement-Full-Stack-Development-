package com.Revature.Services;

import com.Revature.DAOs.ReimbursementDAO;
import com.Revature.DAOs.UserDAO;
import com.Revature.Models.Reimbursement;
import com.Revature.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReimbursementService {
    UserDAO userDAO;
    ReimbursementDAO reimbursementDAO;

    @Autowired
    public ReimbursementService(UserDAO userDAO, ReimbursementDAO reimbursementDAO) {
        this.userDAO = userDAO;
        this.reimbursementDAO = reimbursementDAO;
    }

    public Reimbursement createReimbursement(Reimbursement reimbursement){
        Reimbursement saved_reimbursment = reimbursementDAO.save(reimbursement);
        return saved_reimbursment;
    }

    public List<Reimbursement> getReimbursements(int userId){
        return reimbursementDAO.findByUserUserId(userId);
    }

    public List<Reimbursement> getReimbursmentsByStatus(User user, String status){
        return reimbursementDAO.findByUserAndStatus(user, status);
    }

    public Reimbursement validateReimbursement(User user, int reimbursementId){
        Reimbursement valid_r = reimbursementDAO.getReferenceById(reimbursementId);
        if (valid_r == null) {throw new IllegalArgumentException("No such reimbursement exists");}
        if (!((valid_r.getUser().getUserId() == user.getUserId())||(user.getRole().equals("Admin")))){ throw new IllegalArgumentException("Reimbursement does not belong to this user");}
        return valid_r;
    }

    public Reimbursement updateReimbursement(Reimbursement reimbursement){
        return reimbursementDAO.save(reimbursement);
    }

    public void deleteReimbursement(Reimbursement reimbursement){
        reimbursementDAO.delete(reimbursement);
    }
}
