package com.tianyu.controller;

import com.alibaba.fastjson.JSON;
import com.tianyu.caches.CachesSocket;
import com.tianyu.caches.PushInfoCache;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luobing
 */
@Slf4j
@ServerEndpoint(value = "/websocket/{token}")
@Component
public class WebSocketController {

    /**
     * token
     */
    private String token;

    /**
     * 连接池 目前方式不支持分布式且连接太多会导致内存溢出，后期要修改为redis
     * key:token,value:连接对象
     */
    public static Map<String, Session> sessionPool = new ConcurrentHashMap<>();

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketController.applicationContext = applicationContext;
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam(value="token") String token, Session session) {
        log.debug("==> token: {}", token);

//        try {

            sessionPool.put(token, session);
//        } catch (IOException e) {
//            log.warn("<== WebSocketController::onOpen() 异常 error:{}", e);
//        }
        log.debug("<==");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        sessionPool.remove(token);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        //心跳
        SocketResp<String> socketResp = new SocketResp<>();
        socketResp.setData(message);
        session.getAsyncRemote().sendText(JSON.toJSONString(socketResp));
    }

     @OnError
     public void onError(Session session, Throwable throwable) {
        log.warn("WebSocketController::onError() ==> token:{} error: {}", token, throwable.getMessage());

         PushInfoCache pushInfoCache = CachesSocket.getPushInfoCache(token);
         if (pushInfoCache != null) {
             CachesSocket.setPushInfoCache(token, null);
         }

         sessionPool.remove(token);
     }

     @Data
     class SocketResp<T> {
        private int type;

        private String msg;

        private T data;
    }
}