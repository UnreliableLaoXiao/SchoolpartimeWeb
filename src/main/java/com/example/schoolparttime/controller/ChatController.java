package com.example.schoolparttime.controller;

import com.example.schoolparttime.entity.ChatRecord;
import com.example.schoolparttime.entity.Message;
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
        ArrayList<ChatRecord> query = (ArrayList<ChatRecord>) jdbcTemplate.query("select * from t_chat_record where id = ? and state = 1",
                new Object[]{id}, new BeanPropertyRowMapper(ChatRecord.class));
        System.out.println(query.get(0).toString());
        return new ResultModel<>("得到聊天记录列表", query, "json", 200);
    }

    @ResponseBody
    @RequestMapping(value = "/messages", produces = "application/json;charset=UTF-8")
    public ResultModel getChatRecord(long from,long to) {
        System.out.println("from = " + from + " , to = " + to);
        ArrayList<Message> query = (ArrayList<Message>) jdbcTemplate.query("select * from t_message where msg_from in (?,?) and msg_to in (?,?)",
               new Object[]{from,to,from,to},new BeanPropertyRowMapper(Message.class) );
        return new ResultModel<>("得到20条聊天记录",query,"json",200);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllNoread", produces = "application/json;charset=UTF-8")
    public ResultModel getChatRecord(long id) {
        System.out.println("from = " + id );
        int sum = jdbcTemplate.queryForObject("select sum(no_read) from t_chat_record where id = ?" , new Object[]{id},int.class);
        System.out.println("得到未读消息总和"+sum);
        return new ResultModel<Integer>("得到未读消息总和",sum,"json",200);
    }


}
