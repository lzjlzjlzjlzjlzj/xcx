package com.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class MoneyHistoryResponse implements Serializable {
    String time;
    String transactionType;
    String operactionType;
    int changeGold;
}
