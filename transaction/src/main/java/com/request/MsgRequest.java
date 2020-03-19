package com.request;

import lombok.Data;

@Data
public class MsgRequest {
    private int code;
    private String roomId;
    private String userId;
}
