package com.Revature.DAOs;

import com.Revature.Models.Reimbursement;
import com.Revature.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement,Integer> {
    //List<User> FindByUser(User user);
}
