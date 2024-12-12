package com.Revature.Services;

import com.Revature.DAOs.ReimbursementDAO;
import com.Revature.DAOs.UserDAO;
import com.Revature.Models.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        return null
    }
}
