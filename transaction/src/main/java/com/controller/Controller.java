package com.controller;

import com.request.*;
import com.response.CommonResponse;
import com.server.BuildRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 房间管理请求
 */
@RestController
@CrossOrigin
public class Controller {

    @Autowired
    BuildRoom buildRoom;

    /**
     * 用户创建房间
     * @param buildRoomRequset
     * @return
     */
    @RequestMapping("buildRoom")
    CommonResponse buildRoom(BuildRoomRequset  buildRoomRequset){
               return buildRoom.buildRoom(buildRoomRequset);
    }

    /**
     * 根据房间号将用户加入房间
     * @param joinRoomRequest
     * @return CommonResponse
     */
    @RequestMapping("joinRoom")
    CommonResponse joinRoom(JoinRoomRequest joinRoomRequest){
        return buildRoom.joinRoom(joinRoomRequest);
    }

    /**
     * 根据房间号发送消息
     * @param msgRequest
     */
    @RequestMapping("sendroommsg")
    void sendMsg(MsgRequest msgRequest){
        buildRoom.sendRoomMsg(msgRequest);
    }
}
