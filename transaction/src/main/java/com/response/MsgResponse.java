package com.response;

import lombok.Data;

@Data
public class MsgResponse {
    private int code;
    private String msg;
    private String content;
}
