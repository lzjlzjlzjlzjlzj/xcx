package com.invoke;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class DoWxServeraCallBack implements FallbackFactory<DoWxServer> {
    @Override
    public DoWxServer create(Throwable throwable) {
        return new DoWxServer() {
            @Override
            public String helloword() {
                return "小程序，您好，该服务正在抢救中";
            }
        };
    }
}
