package com.response;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserInfoResponse implements Serializable {
    //微信用户昵称
    String nickName;
    //
    String gender;
    //所在地
    String city;
    //
    String province;
    //
    String country;
    //头像地址
    String avatarUrl;
    //信用分
    int xyScore;
    //经验
    int px;
    //金币
    int gold;
}
