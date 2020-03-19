package com.domain;

import lombok.Data;

/**
 * lzj
 */
@Data
public class TransactionOrder {
    //主键id
    int id;
    //分表订单号
    String ddNumber;
    //出资方
    String toMoney;
    //收资方
    String getMoney;
    //创建时间
    String buildTime;
    //到期时间
    String endTime;
    //交易金额
    double money;
    //出资方是否确定订单
    int toMoneyOk;
    //收资方是否确定订单
    int getMoneyOk;
}
