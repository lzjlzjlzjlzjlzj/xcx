package com.domain;

import lombok.Data;

@Data
public class AddUserMoneyHistory {
    int id;
    String jybKey;
    String time;
    String transactionType;
    String operactionType;
    int changeGold;
}
