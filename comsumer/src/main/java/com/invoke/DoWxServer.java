package com.invoke;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(value = "wxserver", fallbackFactory = DoWxServeraCallBack.class)
public interface DoWxServer {
    @RequestMapping(value = "/helloword",produces = APPLICATION_JSON_UTF8_VALUE)
    String helloword();
}
