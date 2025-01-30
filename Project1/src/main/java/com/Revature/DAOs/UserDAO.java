package com.Revature.DAOs;

import com.Revature.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDAO extends JpaRepository<User, UUID> {
    User findUserByUsername(String username);
    Optional<User> findUserByShortId(String shortId);
}
