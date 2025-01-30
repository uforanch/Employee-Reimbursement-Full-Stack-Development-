package com.Revature.DAOs;

import com.Revature.Models.Reimbursement;
import com.Revature.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement,Integer> {
    List<Reimbursement> findByUserUserId(UUID userId);

    List<Reimbursement> findByUserAndStatus(User user, String status);

    List<Reimbursement> findByStatus(String status);

    //@Query("SELECT r FROM Reimbursement r WHERE r.user=?1 AND r.status=?2")
    //List<Reimbursement> testQuery(User user, String status);

}
