package com.server;

import lombok.Data;

/**
 * lzj
 */
public enum TransactionCode {
    TRANSACTION_FLOW_ZERO(0,"交易等待流程","请耐心等待对方加入房间"),
    TRANSACTION_FLOW_ONE(1,"交易锁定流程","双方锁定交易后方可进行下一步操作"),
    TRANSACTION_FLOW_TWO(2,"交易订单生成流程","交易方正在生成订单中..."),
    TRANSACTION_FLOW_THREE(3,"交易订单支付流程","交易方正在支付交易订单，此流程不可取消锁定"),
    TRANSACTION_FLOW_FOUR(4,"订单确定流程","请双方确定订单，交易方已经支付订单"),
    OK_PAY(101,"交易方已经完成支付"),
    IS_BUILD_NOW(102,"交易方正在设置订单"),
    IS_SETTING_NOW(103,"交易金额设置中"),
    IS_SETTINGTIME_NOW(104,"交易有限时间设置中"),
    IS_LOCKING_NOW(105,"对方已锁定"),
    IS_PAYING__NOW(106,"交易方正在支付交易订单"),
    CANCEL_PAY(107,"交易方已经取消支付"),
    TX_LOCK(108,"对方提醒你锁定交易"),
    JOIN_OK(201,"成功加入房间"),
    EXIT_ROOM(202,"已退出房间"),
    CANCEL_LOCK(203,"对方已取消锁定"),
    ;
    public int code;
    public String msg;
    public String content;
    TransactionCode(int code,String msg,String content){
        this.code=code;
        this.msg=msg;
        this.content=content;
    }
    TransactionCode(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

}
