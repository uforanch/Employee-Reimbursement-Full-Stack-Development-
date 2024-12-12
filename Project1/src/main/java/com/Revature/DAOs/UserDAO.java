package com.Revature.DAOs;

import com.Revature.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
}
