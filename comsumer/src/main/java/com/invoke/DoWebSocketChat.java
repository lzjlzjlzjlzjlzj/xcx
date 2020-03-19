package com.invoke;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "websocketchat",fallbackFactory =DoWxServeraCallBack.class)
public interface DoWebSocketChat {
    @RequestMapping(value = "buildroom")
    String buildRoom(String userid);
    @RequestMapping(value = "joinroom")
    String joinRoom(String userid,String roomid);
    @RequestMapping(value = "sendroommsg")
    void sendRoomMsg(String roomid,String code);
}
