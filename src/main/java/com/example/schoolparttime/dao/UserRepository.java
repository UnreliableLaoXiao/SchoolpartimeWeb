package com.example.schoolparttime.dao;

import com.example.schoolparttime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    User findByUsername(String username);
    User findByUsernameAndVerifypsw(String username,String verifypsw);
    User findByUsernameAndPassword(String username,String password);
}
