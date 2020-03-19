package com.domain;

import com.server.WebSocket;
import lombok.Data;


@Data
public class Room {
    //记录当前房间交易流程
    private  int flow=0;
    //房间号
    private String roomId=null;
    //创建者对象
    private BuildUser buildUser=null;
    //加入者对象
    private JoinUser joinUser=null;
}