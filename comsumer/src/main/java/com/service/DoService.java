package com.service;

import com.invoke.DoWxServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoService {
    @Autowired
    private DoWxServer doWxServer;
    public String wxHelloWord(){
        return doWxServer.helloword();
    }
}
