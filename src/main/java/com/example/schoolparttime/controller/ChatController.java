package com.example.schoolparttime.controller;

import com.example.schoolparttime.entity.ChatRecord;
import com.example.schoolparttime.entity.Message;
import com.example.schoolparttime.entity.UserInfo;
import com.example.schoolparttime.entity.base.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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


    @ResponseBody
    @RequestMapping(value = "/chatwithboss", produces = "application/json;charset=UTF-8")
    public ResultModel addRecord(long userid,@RequestParam(value ="bossid",defaultValue = "0")long bossid) {
        System.out.println("新增聊天记录的userid = " + userid );
        System.out.println("新增聊天记录的userid = " + userid + " , bossid = " + bossid);

        ArrayList<ChatRecord> query = (ArrayList<ChatRecord>) jdbcTemplate.query("select * from t_chat_record where id = ? and other_id = ?",
                new Object[]{userid,bossid},new BeanPropertyRowMapper(ChatRecord.class) );

        if (query.size() > 0){
            return new ResultModel<>("得到聊天记录列表", query.get(0), "json", 200);
        }else {
            ArrayList<UserInfo> query_boss = (ArrayList<UserInfo>) jdbcTemplate.query("select * from t_u_info where id = ?",
                    new Object[]{bossid},new BeanPropertyRowMapper(UserInfo.class) );
            System.out.println("query_boss  "+ query_boss.size());

            ArrayList<UserInfo> query_user = (ArrayList<UserInfo>) jdbcTemplate.query("select * from t_u_info where id = ?",
                    new Object[]{userid},new BeanPropertyRowMapper(UserInfo.class) );
            System.out.println("query_user  "+ query_user.size());

            Date date=new Date();
            jdbcTemplate.update("insert into t_chat_record values(?,?,?,?,?,?,?,?)",userid,"123123.jpg",query_boss.get(0).getUsername(),
                    "",bossid,1,df.format(date),0);
            jdbcTemplate.update("insert into t_chat_record values(?,?,?,?,?,?,?,?)",bossid,"123123.jpg",query_user.get(0).getUsername(),
                    "",userid,1,df.format(date),0);

            ChatRecord chatRecord = new ChatRecord(userid,"",query_boss.get(0).getUsername(),"",bossid,1,"",0);
            return new ResultModel<>("得到聊天记录列表", chatRecord, "json", 200);
        }
    }



}
