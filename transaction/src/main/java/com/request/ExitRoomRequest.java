package com.request;

import lombok.Data;

@Data
public class ExitRoomRequest {
    //用户id
    private String userId;
    //交易房间号
    private String roomId;
}
