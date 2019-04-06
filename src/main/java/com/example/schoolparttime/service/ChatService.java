package com.example.schoolparttime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author lvhaosir 2019/4/6 18:30
 * @Description: TODO()
 * @Version v1.0
 **/
@Service
public class ChatService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void test() {
        int rows = jdbcTemplate.update("insert into t_message (msg_mes,msg_from,msg_to,msg_type,msg_state) values(?,?,?,?,?)",
                new Object[]{"test", 1, 2, 4, 5});
    }
}
