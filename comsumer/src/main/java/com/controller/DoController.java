package com.controller;

import com.service.DoService;
import com.service.DoWebSocketChat;
import com.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RefreshScope
public class DoController {
    @Autowired
    DoService doService;
    @Autowired
    DoWebSocketChat doWebSocketChat;
    @RequestMapping("/wxhelloword")
    CommonResponse wxHelloWord(){
        return CommonResponse.success(200,"请求成功",doService.wxHelloWord());
    }
    @RequestMapping("buildroom")
    String bulidRoom(String userid){
       return doWebSocketChat.bulidRoom(userid);
    }
}
