package com.server;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.request.ExitRoomRequest;
import com.request.MsgRequest;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
@ServerEndpoint("/msgserver/{userid}")
@Component
public class WebSocket {
    private static int onlineCount = 0;
    //旧：concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //private static CopyOnWriteArraySet<ImController> webSocketSet = new CopyOnWriteArraySet<ImController>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //新：使用map对象，便于根据userId来获取对应的WebSocket
    private static ConcurrentHashMap<String,WebSocket> websocketList = new ConcurrentHashMap<>();
    //接收sid
    private  String userId="";
    //交易人房间号
    private  String roomId=null;
    public void setRoomId(String roomId){
        this.roomId=roomId;
    }
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userid") String userid) {
        this.session = session;
        this.userId=userid;
        if(WebSocket.websocketList.containsKey(userid)){
            //已经在线则关闭当前连接
            addOnlineCount();
            onClose();
        }else {
            //如果不在线则加入
            WebSocket.websocketList.put(userId,this);
            addOnlineCount();
            System.out.println("有人加入小程序:"+userId+",当前在线人数为" + getOnlineCount());
        }


    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(websocketList.get(this.userId)!=null){
            websocketList.remove(this.userId);
            //webSocketSet.remove(this);  //从set中删除
            subOnlineCount();           //在线数减1
            ExitRoomRequest exitRoomRequest=new ExitRoomRequest();
            exitRoomRequest.setRoomId(this.roomId);
            exitRoomRequest.setUserId(this.userId);
            new BuildRoom().exitRoom(exitRoomRequest);
            System.out.println("有人退出小程序！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到来自窗口"+userId+"的信息:"+message);
        MsgRequest msgRequest =new MsgRequest();
        msgRequest.setCode(Integer.parseInt(JSONObject.parseObject(message).getString("code")));
        msgRequest.setRoomId(JSONObject.parseObject(message).getString("roomId"));
        if(msgRequest.getCode()==0){
            //推送交易流程
            new BuildRoom().sendMsg(msgRequest);
        }else {
            //实时发送弹出消息
            msgRequest.setUserId(this.userId);
            new BuildRoom().sendToast(msgRequest);
        }

    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException, EncodeException {
        this.session.getBasicRemote().sendText(message);
        //实时更新状态
    }


    /**
     * 群发自定义消息
     * */
    /*public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("推送消息到窗口"+userId+"，推送内容:"+message);
        for (ImController item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(userId==null) {
                    item.sendMessage(message);
                }else if(item.userId.equals(userId)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }*/

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

    //获取WebSocketServer对象
    public static WebSocket getWebSocketServer(String userid){
        return WebSocket.websocketList.get(userid);
    }
}
