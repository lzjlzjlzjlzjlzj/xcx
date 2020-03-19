package com.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestController {
    @Value("${server.port}")
    private String port;
    @RequestMapping("/helloword")
    String helloWord(){
        return "hello,小程序我是"+port+"端口";
    }
}
