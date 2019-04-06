package com.example.schoolparttime.controller;

import com.example.schoolparttime.dao.JdbcTemplateObject;
import com.example.schoolparttime.entity.Message;
import com.google.gson.Gson;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket/{id}") // 客户端URI访问的路径
@Component
public class WebSocketServer {

    JdbcTemplateObject jdbcTemplateObject = new JdbcTemplateObject();
    JdbcTemplate jdbcTemplate = jdbcTemplateObject.getJdbcTemplate();
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    /**
     * 保存所有连接的webSocket实体
     * CopyOnWriteArrayList使用了一种叫写时复制的方法，
     * 当有新元素添加到CopyOnWriteArrayList时，
     * 先从原有的数组中拷贝一份出来，然后在新的数组做写操作，
     * 写完之后，再将原来的数组引用指向到新数组。
     * 具备线程安全，并且适用于高并发场景
     */
    private static CopyOnWriteArrayList<WebSocketServer> sWebSocketServers = new CopyOnWriteArrayList<>();
    private Session mSession; // 与客户端连接的会话，用于发送数据
    private long mid; // 客户端的标识(这里以机器编号)
    private Log mLog = LogFactory.getLog(WebSocketServer.class);
    private Gson gson = new Gson();


    @OnOpen
    public void onOpen(Session session, @PathParam("id") long id) {
        mSession = session;
        sWebSocketServers.add(this); // 将回话保存
        mLog.info("-->onOpen new connect vmcNo is " + id);
        mid = id;
    }

    @OnClose
    public void onClose() {
        sWebSocketServers.remove(this);
        mLog.info("-->onClose a connect");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        mLog.info("-->onMessage " + message);

        Message message1 = gson.fromJson(message, Message.class);

        // 这里选择的是让其他客户端都知道消息，类似于转发的聊天室，可根据使用场景使用
        for (WebSocketServer socketServer : sWebSocketServers) {
            if (socketServer.mid == message1.getMsg_to()) {
                message1.setMsg_state(1);
                socketServer.sendMessage(gson.toJson(message1));
            }
        }
        System.out.println(message1.toString());
        String sql  = "insert into t_message (msg_mes,msg_from,msg_to,msg_type,msg_state) values(?,?,?,?,?)";
        int rows = jdbcTemplate.update(sql,
                new Object[]{message1.getMsg_mes(),message1.getMsg_from(),message1.getMsg_to(),message1.getMsg_type(),message1.getMsg_state()});
        System.out.println("------------?>" +rows);

        String sql_update = "update t_chat_record set new_mes = ?,date = ? where id in (?,?) and other_id in (?,?)";

        Date date=new Date();
        int rows1 = jdbcTemplate.update(sql_update,
                new Object[]{message1.getMsg_mes(),df.format(date),message1.getMsg_from(),message1.getMsg_to()
                        ,message1.getMsg_from(),message1.getMsg_to()});
        System.out.println("------------?>" +rows1);
    }

    /**
     * 对外发送消息
     *
     * @param message
     */
    public boolean sendMessage(String message) {
        try {
            mSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            mLog.info(e.toString());
            return false;
        }
        return true;
    }
}