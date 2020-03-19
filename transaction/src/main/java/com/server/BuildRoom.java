package com.server;

import com.domain.BuildUser;
import com.domain.CheckRoomTransactionType;
import com.domain.JoinUser;
import com.domain.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.request.*;
import com.response.CommonResponse;
import com.response.MsgResponse;
import com.response.MsgToastResponse;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class BuildRoom {
    public static Map<String, Room> room = new HashMap<String, Room>();


    /**
     * 创建房间
     *
     * @param
     */
    public CommonResponse buildRoom(BuildRoomRequset buildRoomRequset) {
        Room room = new Room();
        room.setRoomId(buildRoomRequset.getUserId());
        BuildUser buildUser=new BuildUser();
        buildUser.setUserId(buildRoomRequset.getUserId());
        buildUser.setName(buildRoomRequset.getName());
        buildUser.setHeadImg(buildRoomRequset.getHeadImg());
        WebSocket webSocket=WebSocket.getWebSocketServer(buildRoomRequset.getUserId());
        webSocket.setRoomId(buildRoomRequset.getUserId());
        buildUser.setWebSocket(webSocket);
        buildUser.setTransactionSelect(buildRoomRequset.getTransactionSelect());
        room.setBuildUser(buildUser);
        BuildRoom.room.put(buildRoomRequset.getUserId(), room);
        return CommonResponse.success(200, "房间创建成功", buildRoomRequset.getUserId());
    }


    /**
     * 根据房间号加入房间，如果邀请人不存在则创建房间并返回房间号否则返回ok
     *
     * @param
     */
    public CommonResponse joinRoom(JoinRoomRequest joinRoomRequest) {
        if (!room.containsKey(joinRoomRequest.getRoomId())) {
            Room room = new Room();
            BuildUser buildUser=new BuildUser();
            buildUser.setUserId(joinRoomRequest.getUserId());
            buildUser.setName(joinRoomRequest.getName());
            buildUser.setHeadImg(joinRoomRequest.getHeadImg());
            WebSocket webSocket=WebSocket.getWebSocketServer(joinRoomRequest.getUserId());
            webSocket.setRoomId(joinRoomRequest.getUserId());
            buildUser.setWebSocket(webSocket);
            room.setBuildUser(buildUser);
            BuildRoom.room.put(joinRoomRequest.getUserId(), room);
            return CommonResponse.success(200, "创建房间成功", joinRoomRequest.getUserId());
        } else {
            JoinUser joinUser=new JoinUser();
            joinUser.setUserId(joinRoomRequest.getUserId());
            joinUser.setName(joinRoomRequest.getName());
            joinUser.setHeadImg(joinRoomRequest.getHeadImg());
            WebSocket webSocket=WebSocket.getWebSocketServer(joinRoomRequest.getUserId());
            webSocket.setRoomId(joinRoomRequest.getRoomId());
            joinUser.setWebSocket(webSocket);
            BuildRoom.room.get(joinRoomRequest.getRoomId()).setJoinUser(joinUser);
            //发送一条加入房间信息
            MsgRequest   msgRequest=new MsgRequest();
            msgRequest.setRoomId(joinRoomRequest.getRoomId());
            msgRequest.setCode(101);
            sendToast(msgRequest);
        }
        return CommonResponse.success(200, "ok");
    }


    /**
     * 通过房间号发送房间信息
     */
    public void sendRoomMsg(MsgRequest msgRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

    }


    /**
     * 通过userid销毁房间
     *
     * @param userid
     */
    public void destoryRoom(String userid) {
        if (room.containsKey(userid)) {
            room.remove(userid);
            System.out.println(userid + "号交易房间已经销毁");
        }
    }


    /**
     * 通过房间号实时推送房间交易流程
     */
    public void sendMsg(MsgRequest roomRequest){
        if(room.get(roomRequest.getRoomId())!=null){
            if(room.get(roomRequest.getRoomId()).getBuildUser().getWebSocket()!=null){
                MsgResponse msgResponse=new MsgResponse();
                msgResponse.setCode(0);
                switch (room.get(roomRequest.getRoomId()).getFlow()){
                    case 0:
                        msgResponse.setMsg(TransactionCode.TRANSACTION_FLOW_ZERO.msg);
                        msgResponse.setContent(TransactionCode.TRANSACTION_FLOW_ZERO.content);
                        break;
                    case 1:
                        msgResponse.setMsg(TransactionCode.TRANSACTION_FLOW_ONE.msg);
                        msgResponse.setContent(TransactionCode.TRANSACTION_FLOW_ONE.content);
                        break;
                    case 2:
                        msgResponse.setMsg(TransactionCode.TRANSACTION_FLOW_TWO.msg);
                        msgResponse.setContent(TransactionCode.TRANSACTION_FLOW_TWO.content);
                        break;
                    case 3:
                        msgResponse.setMsg(TransactionCode.TRANSACTION_FLOW_THREE.msg);
                        msgResponse.setContent(TransactionCode.TRANSACTION_FLOW_THREE.content);
                        break;
                    case 4:
                        msgResponse.setMsg(TransactionCode.TRANSACTION_FLOW_FOUR.msg);
                        msgResponse.setContent(TransactionCode.TRANSACTION_FLOW_FOUR.content);

                }
                try {
                    //向房间创建者更新交易流程
                    room.get(roomRequest.getRoomId()).getBuildUser().getWebSocket().sendMessage(new ObjectMapper().writeValueAsString(msgResponse));
                    if(room.get(roomRequest.getRoomId()).getJoinUser()!=null){
                        //当房间加入者存在时实现推送交易流程
                        room.get(roomRequest.getRoomId()).getJoinUser().getWebSocket().sendMessage(new ObjectMapper().writeValueAsString(msgResponse));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 当状态码为1时实时发送状态弹出信息
     */
    public void sendToast(MsgRequest msgRequest){
        MsgToastResponse msgToastResponse=new MsgToastResponse();
        CheckRoomTransactionType checkRoomTransactionType=new CheckRoomTransactionType();
        msgToastResponse.setCode(1);
        checkRoomTransactionType.setRoomId(msgRequest.getRoomId());
        if(msgRequest.getCode()==TransactionCode.OK_PAY.code){
            checkRoomTransactionType.setType(1);
            msgToastResponse.setMsg(TransactionCode.OK_PAY.msg);
            checkTransactionType(checkRoomTransactionType,msgToastResponse);
        }else if(msgRequest.getCode()==TransactionCode.IS_BUILD_NOW.code){
            //向加入者发送,交易订单只能创建者创建和设置
            checkRoomTransactionType.setType(3);
            msgToastResponse.setMsg(TransactionCode.IS_BUILD_NOW.msg);
            checkTransactionType(checkRoomTransactionType,msgToastResponse);
        }else if(msgRequest.getCode()==TransactionCode.IS_SETTING_NOW.code){
            checkRoomTransactionType.setType(3);
            msgToastResponse.setMsg(TransactionCode.IS_SETTING_NOW.msg);
            checkTransactionType(checkRoomTransactionType,msgToastResponse);
        }else if(msgRequest.getCode()==TransactionCode.IS_SETTINGTIME_NOW.code){
            checkRoomTransactionType.setType(3);
            msgToastResponse.setMsg(TransactionCode.IS_SETTINGTIME_NOW.msg);
            checkTransactionType(checkRoomTransactionType,msgToastResponse);
        }else if(msgRequest.getCode()==TransactionCode.IS_LOCKING_NOW.code){
            if(msgRequest.getRoomId().equals(msgRequest.getUserId())){
                //则为房间创建者
                checkRoomTransactionType.setType(3);
            }else {
                checkRoomTransactionType.setType(4);
            }
            //
            msgToastResponse.setCode(005);
            msgToastResponse.setMsg(TransactionCode.IS_LOCKING_NOW.msg);
            checkTransactionType(checkRoomTransactionType,msgToastResponse);
        }else if(msgRequest.getCode()==TransactionCode.IS_PAYING__NOW.code){
            checkRoomTransactionType.setType(1);
            msgToastResponse.setMsg(TransactionCode.IS_PAYING__NOW.msg);
            checkTransactionType(checkRoomTransactionType,msgToastResponse);
        }else if(msgRequest.getCode()==TransactionCode.CANCEL_PAY.code){
            checkRoomTransactionType.setType(1);
            msgToastResponse.setMsg(TransactionCode.CANCEL_PAY.msg);
            checkTransactionType(checkRoomTransactionType,msgToastResponse);
        }else if(msgRequest.getCode()==TransactionCode.JOIN_OK.code){
            checkRoomTransactionType.setType(4);
            //向创建者发送加入房间成功消息
            msgToastResponse.setCode(101);
            msgToastResponse.setMsg(room.get(msgRequest.getRoomId()).getJoinUser().getName()+TransactionCode.JOIN_OK.msg);
            checkTransactionType(checkRoomTransactionType,msgToastResponse);
        }else if(msgRequest.getCode()==TransactionCode.EXIT_ROOM.code){
            checkRoomTransactionType.setType(2);
            //向加入发送退出房间信息
            msgToastResponse.setCode(102);
            msgToastResponse.setMsg(room.get(msgRequest.getRoomId()).getJoinUser().getName()+TransactionCode.EXIT_ROOM.msg);
            checkTransactionType(checkRoomTransactionType,msgToastResponse);
        }
    }


    /**
     * 通过房间号选择某一方发送消息
     * type 0,向出资方发送 1,向收资方发送 2,向双方发送 3,向加入者发送 4,向房间创建者发送
     */
    private void checkTransactionType(CheckRoomTransactionType checkRoomTransactionType,MsgToastResponse msgToastResponse){
        if(checkRoomTransactionType.getType()==0){
            if(room.get(checkRoomTransactionType.getRoomId()).getBuildUser().getTransactionSelect().equals("出资方")){
                //创建者为出资方
                try {
                    room.get(checkRoomTransactionType.getRoomId()).getBuildUser().getWebSocket().sendMessage(new ObjectMapper().writeValueAsString(msgToastResponse));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }else {
                //创建者为收资方加入者为出资方
                try {
                    room.get(checkRoomTransactionType.getRoomId()).getJoinUser().getWebSocket().sendMessage(new ObjectMapper().writeValueAsString(msgToastResponse));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }

            }
        }else if(checkRoomTransactionType.getType()==1){
            if(room.get(checkRoomTransactionType.getRoomId()).getBuildUser().getTransactionSelect().equals("收资方")){
                //创建者为收资方
                try {
                    room.get(checkRoomTransactionType.getRoomId()).getBuildUser().getWebSocket().sendMessage(new ObjectMapper().writeValueAsString(msgToastResponse));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }else {
                //创建者为出资方，加入者为收资方
                try {
                    room.get(checkRoomTransactionType.getRoomId()).getJoinUser().getWebSocket().sendMessage(new ObjectMapper().writeValueAsString(msgToastResponse));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }

            }
        }else if(checkRoomTransactionType.getType()==2){
            //向双方发送
            try {
                room.get(checkRoomTransactionType.getRoomId()).getBuildUser().getWebSocket().sendMessage(new ObjectMapper().writeValueAsString(msgToastResponse));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            try {
                room.get(checkRoomTransactionType.getRoomId()).getJoinUser().getWebSocket().sendMessage(new ObjectMapper().writeValueAsString(msgToastResponse));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }else if(checkRoomTransactionType.getType()==3){
            //向房间加入者发送
            try {
                room.get(checkRoomTransactionType.getRoomId()).getJoinUser().getWebSocket().sendMessage(new ObjectMapper().writeValueAsString(msgToastResponse));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }else {
            //向房间创建者发送
            try {
                room.get(checkRoomTransactionType.getRoomId()).getBuildUser().getWebSocket().sendMessage(new ObjectMapper().writeValueAsString(msgToastResponse));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 交易者退出房间
     */
    public void exitRoom(ExitRoomRequest exitRoomRequest){
        if(exitRoomRequest.getRoomId()==null){
            //无房间则直接退出
            System.out.println("退出信息:");
            return;
        }else {
            if(exitRoomRequest.getRoomId().equals(exitRoomRequest.getUserId())){
                //为房间创建者
                if (room.containsKey(exitRoomRequest.getRoomId())) {
                    //向加入者发送退出房间toast消息
                    MsgRequest msgRequest=new MsgRequest();
                    msgRequest.setCode(102);
                    msgRequest.setRoomId(exitRoomRequest.getRoomId());
                    sendToast(msgRequest);
                    room.remove(exitRoomRequest.getRoomId());
                    System.out.println(exitRoomRequest.getRoomId() + "号交易房间已经销毁");
                }

            }else {
                //为房间加入者
                if(room.containsKey(exitRoomRequest.getRoomId())){
                    System.out.println(room.get(exitRoomRequest.getRoomId()).getJoinUser().getName()+"退出了"+exitRoomRequest.getRoomId()+"号交易房间 jybkey:"+exitRoomRequest.getUserId());
                    //向创建者发送退出房间toast消息
                    MsgRequest msgRequest=new MsgRequest();
                    msgRequest.setCode(102);
                    msgRequest.setRoomId(exitRoomRequest.getRoomId());
                    sendToast(msgRequest);
                    room.get(exitRoomRequest.getRoomId()).setJoinUser(null);
                }
            }
        }

    }



}