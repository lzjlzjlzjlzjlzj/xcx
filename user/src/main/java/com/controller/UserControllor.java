package com.controller;

import com.domain.UserInfoKey;
import com.mapper.read.UserReadA;
import com.mapper.write.UserWriteA;
import com.request.*;
import com.response.*;
import com.service.LoginService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class UserControllor{
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;
    @PostMapping("getlogin")
    private CommonResponse<UserInfoKeyResponse> getLogin(WxLoginRequest wxLoginRequest){
        return loginService.decodeUserInfo(wxLoginRequest.getEncryptedData(),wxLoginRequest.getIv(),wxLoginRequest.getCode());
    }

    @PostMapping("getUserInfo")
    private CommonResponse<UserInfoResponse> getUserInfo(GetUserInfoRequest getUserInfoRequest) {
        return userService.getUserInfo(getUserInfoRequest);
    }

    @PostMapping("addUser")
    private int addUser(UserInfoKey userInfo) {
        return 0;
    }

    @PostMapping("updateUserXyScore")
    private int updateUserXyScore(updateUserXyScoreRequest updateUserXyScoreRequest) {
        return 0;
    }

    @PostMapping("updateUserPx")
    private int updateUserPx(updateUserPxRequest updateUserPxRequest) {
        return 0;
    }

    @PostMapping("addUserGold")
    private CommonResponse updateUserGold(AddUserGoldRequest addUserGoldRequest) {
        return userService.updateUserGold(addUserGoldRequest);
    }

    @PostMapping("getUserMoney")
    private CommonResponse<MoneyResponse> getUserMoney(GetUserInfoRequest getUserInfoRequest){
        return userService.getUserMoney(getUserInfoRequest);
    }

    @PostMapping("getMoneyHistory")
    private CommonResponse<List<MoneyHistoryResponse>> getMoneyHistory(GetUserInfoRequest getUserInfoRequest){
        return userService.getUserMoneyHistory(getUserInfoRequest);
    }
}
