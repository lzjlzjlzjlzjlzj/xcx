package com.request;

import lombok.Data;

@Data
public class JoinRoomRequest {
    //房间号
    private String roomId;
    //用户在jyb的关键key
    private String UserId;
    //头像
    private String headImg;
    //昵称
    private String name;
}
