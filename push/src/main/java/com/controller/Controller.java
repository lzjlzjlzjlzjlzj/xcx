package com.controller;

import com.request.WeChatMessage;
import com.server.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin
public class Controller {
    @Autowired
    private Service service;
    @RequestMapping(value="/getMessage")
    public String getMessage( WeChatMessage message) {
            return service.checkSignature(message);
    }
}
