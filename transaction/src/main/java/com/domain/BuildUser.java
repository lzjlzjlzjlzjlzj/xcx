package com.domain;

import com.server.WebSocket;
import lombok.Data;

/**
 * lzj
 * 房间创建者对象
 */
@Data
public class BuildUser {
    //用户id
    private String userId;
    //名字
    private String name;
    //图片
    private String headImg;
    //会话对象
    private WebSocket webSocket;
    //是否锁定交易
    private boolean isLock=false;
    //是否确定交易
    private boolean isConfirm=false;
    //交易方类型
    private String transactionSelect;
}
