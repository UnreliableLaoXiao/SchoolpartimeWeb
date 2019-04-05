package com.example.schoolparttime.controller;

import com.example.schoolparttime.entity.ChatRecord;
import com.example.schoolparttime.entity.base.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class ChatController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseBody
    @RequestMapping(value = "/chatrecord", produces = "application/json;charset=UTF-8")
    public ResultModel getRecord(long id) {
        System.out.println("请求聊天记录的id = " + id);
        ArrayList<ChatRecord> query = (ArrayList<ChatRecord>) jdbcTemplate.query("select * from t_chat_record where id = ?",
                new Object[]{id}, new BeanPropertyRowMapper(ChatRecord.class));
        return new ResultModel<>("得到聊天记录", query, "json", 200);
    }


}
