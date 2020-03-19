package com.controller;

import com.domain.UserInfoKey;
import com.request.GetUserInfoRequest;
import com.response.CommonResponse;
import com.response.UserInfoResponse;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RefreshScope
public class TestController {
    @Value("${server.port}")
    private String port;
    @Autowired
    private UserService userService;
    @RequestMapping("/helloword")
    String helloWord(){
        UserInfoKey userInfoKey=new UserInfoKey();
        userInfoKey.setAvatarUrl("123");
        userInfoKey.setCity("长沙");
        userInfoKey.setCountry("中国");
        userInfoKey.setGender("qwe");
        userInfoKey.setGold(100);
        userInfoKey.setJybKey("B0000001");
        userInfoKey.setNickName("yymt");
        userInfoKey.setOpenId("1122");
        userInfoKey.setPx(100);
        userInfoKey.setProvince("湖南");
        userInfoKey.setUnionId("19991122");
        userInfoKey.setXyScore(100);
        userService.addUser(userInfoKey);
        return "成功";
    }

    @RequestMapping("/getuserinfo")
    public CommonResponse<UserInfoResponse> getUserInfo(GetUserInfoRequest getUserInfoRequest) {
        return userService.getUserInfo(getUserInfoRequest);
    }


}
