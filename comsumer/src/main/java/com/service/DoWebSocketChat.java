package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoWebSocketChat {
    @Autowired
    DoWebSocketChat doWebSocketChat;
    public String bulidRoom(String userid){
        return doWebSocketChat.bulidRoom(userid);
    }

    public void joinRoom(String userid,String roomid){
        doWebSocketChat.joinRoom(userid,roomid);
    }

    public void sendRoomMsg(String roomid,String code){
        doWebSocketChat.sendRoomMsg(roomid,code);
    }
}
