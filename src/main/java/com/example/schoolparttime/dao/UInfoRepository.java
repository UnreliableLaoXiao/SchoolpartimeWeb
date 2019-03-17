package com.example.schoolparttime.dao;

import com.example.schoolparttime.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UInfoRepository extends JpaRepository<UserInfo, Long> {
    List<UserInfo> findAll();
}
