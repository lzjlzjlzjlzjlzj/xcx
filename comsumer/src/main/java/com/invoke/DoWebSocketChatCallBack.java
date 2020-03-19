package com.invoke;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class DoWebSocketChatCallBack implements FallbackFactory <DoWebSocketChat> {
    @Override
    public DoWebSocketChat create(Throwable throwable) {
        return new DoWebSocketChat() {
            @Override
            public String buildRoom(String userid) {
                return "服务正在检查，暂时无法交易";
            }
            @Override
            public String joinRoom(String userid, String roomid) {
                return "服务正在检查，暂时加入不了交易";
            }

            @Override
            public void sendRoomMsg(String roomid, String code) {

            }
        };
    }
}
