package com.domain;

import com.response.UserInfoKeyResponse;
import lombok.Data;

@Data
public class UserInfoKey extends UserInfoKeyResponse {
    //小程序openid
    String openId;
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
    //小程序唯一ID
    String unionId;
    //信用分
    int xyScore;
    //经验
    int px;
    //金币
    int gold;
    //交易宝公用key
    String jybKey;
}
