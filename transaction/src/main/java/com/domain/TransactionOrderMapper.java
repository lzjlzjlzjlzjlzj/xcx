package com.domain;

import lombok.Data;

/**
 * lzj
 */
@Data
public class TransactionOrderMapper {
    //主键id
    int id;
    //交易宝用户key
    String jybKey;
    //订单号
    String ddNumber;
}
