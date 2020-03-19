package com.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserKey implements Serializable {
    private int id;
    private String unionId;
    private String openId;
    private String jybKey;
}
