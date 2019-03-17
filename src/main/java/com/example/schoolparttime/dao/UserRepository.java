package com.example.schoolparttime.dao;

import com.example.schoolparttime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    User findByUsername(String username);
    User findByUsernameAndVerifypsw(String username,String verifypsw);
    User findByUsernameAndPassword(String username,String password);
    User findUserById(long id);

    @Modifying
    @Query(value = "update t_user set type= :type where id=:id ")
    void updateTypeById(@Param("type") int type, @Param("id") long id);
}
