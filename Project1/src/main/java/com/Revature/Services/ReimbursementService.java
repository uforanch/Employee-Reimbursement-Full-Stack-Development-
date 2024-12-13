package com.Revature.Services;

import com.Revature.DAOs.ReimbursementDAO;
import com.Revature.DAOs.UserDAO;
import com.Revature.Models.Reimbursement;
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
}
