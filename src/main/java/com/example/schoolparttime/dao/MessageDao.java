package com.example.schoolparttime.dao;

import com.example.schoolparttime.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveMessage(Message message1) {

    }

}
