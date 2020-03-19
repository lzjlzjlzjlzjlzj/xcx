package com.request;

import lombok.Data;

@Data
public class BuildRoomRequset {
    private String userId;
    private String headImg;
    private String name;
    //交易方类型
    private String transactionSelect;
}
