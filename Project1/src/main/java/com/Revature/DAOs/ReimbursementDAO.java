package com.Revature.DAOs;

import com.Revature.Models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement,Integer> {
}
