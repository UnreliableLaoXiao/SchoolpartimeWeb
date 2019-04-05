package com.example.schoolparttime.controller;

import com.example.schoolparttime.entity.Message;
import com.google.gson.Gson;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
        Gson gson = new Gson();
        Message message1 = gson.fromJson(message, Message.class);
        // 这里选择的是让其他客户端都知道消息，类似于转发的聊天室，可根据使用场景使用
        for (WebSocketServer socketServer : sWebSocketServers) {
            socketServer.sendMessage("server have rcv you message");
            if (socketServer.mid == message1.getTo()) {
                socketServer.sendMessage(message1.getMes());
            }
        }
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

    /**
     * 对某个机器发送消息
     *
     * @param message
     * @param vmcNo   机器编号
     * @return true, 返回发送的消息, false，返回failed字符串
     */
    public static String sendMessage(String message, long vmcNo) {
        boolean success = false;
        for (WebSocketServer server : sWebSocketServers) {
            if (server.mid == vmcNo) {
                success = server.sendMessage(message);
                break;
            }
        }
        return success ? message : "failed";
    }
}