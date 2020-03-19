package com.service;

import com.datapool.DataMonneyHistory;
import com.datapool.DataSelect;
import com.domain.AddUserMoneyHistory;
import com.domain.UserInfoKey;
import com.domain.UserKey;
import com.mapper.read.*;
import com.mapper.write.*;
import com.request.*;
import com.response.CommonResponse;
import com.response.MoneyHistoryResponse;
import com.response.MoneyResponse;
import com.response.UserInfoResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DataSelect<GetUserInfoRequest,UserInfoResponse> dataSelect1;
    @Autowired
    private DataSelect<UserInfoKey,Integer> dataSelect2;
    @Autowired
    private UserKeyRead userKeyRead;
    @Autowired
    private UserKeyWrite userKeyWrite;
    @Autowired
    private DataSelect<String,String> dataSelect3;
    @Autowired
    private DataSelect<updateUserGoldRequest,Integer> updateUserGoldRequestDataSelect;
    @Autowired
    private DataMonneyHistory<AddUserMoneyHistory,Integer> addUserMoneyHistoryIntegerDataMonneyHistory;
    @Autowired
    private DataMonneyHistory<GetUserInfoRequest,List<MoneyHistoryResponse>> getUserInfoRequestListDataMonneyHistory;

    public CommonResponse<UserInfoResponse> getUserInfo(GetUserInfoRequest getUserInfoRequest) {
        //先从缓存中拿数据
        UserInfoResponse userInfoResponse = (UserInfoResponse)redisTemplate.opsForValue().get(getUserInfoRequest.getJybKey());
        //如果拿不到，就去数据库中查询
         if (userInfoResponse==null){
            //进行数据库的动态操作
            userInfoResponse=dataSelect1.readData("getUserInfo",getUserInfoRequest.getJybKey().charAt(0),getUserInfoRequest);
            //放入缓存中
            redisTemplate.opsForValue().set(getUserInfoRequest.getJybKey(), userInfoResponse, 1, TimeUnit.DAYS);
        }
        return CommonResponse.success(200,"请求成功",userInfoResponse);
    }

    //添加新用户信息
    public CommonResponse addUser(UserInfoKey userInfo) {
        //添加到缓存
        UserInfoResponse userInfoResponse=new UserInfoResponse();
        BeanUtils.copyProperties(userInfo,userInfoResponse);
        redisTemplate.opsForValue().set(userInfo.getJybKey(), userInfoResponse, 1, TimeUnit.DAYS);
        int result=dataSelect2.writeData("addUser","添加用户",userInfo.getJybKey().charAt(0),userInfo);
        return CommonResponse.success(200,"请求成功",result);
    }

    //修改用户信用分
   public CommonResponse updateUserXyScore(updateUserXyScoreRequest updateUserXyScoreRequest) {
        //修改缓存中的信息
        UserInfoResponse userInfoResponse=(UserInfoResponse) redisTemplate.opsForValue().get(updateUserXyScoreRequest.getJybKey());
        userInfoResponse.setXyScore(updateUserXyScoreRequest.getXyScore());
        //重新设置缓存信息实现更新
        redisTemplate.opsForValue().set(updateUserXyScoreRequest.getJybKey(), userInfoResponse, 1, TimeUnit.DAYS);
        //修改结果
        int result=-2;
        return CommonResponse.success(200,"请求成功",result);
    }

    //修改用户经验值
    public CommonResponse updateUserPx(updateUserPxRequest updateUserPxRequest) {
        //修改缓存中的信息
        UserInfoResponse userInfoResponse=(UserInfoResponse) redisTemplate.opsForValue().get(updateUserPxRequest.getJybKey());
        userInfoResponse.setPx(updateUserPxRequest.getUserPx());
        //重新设置缓存信息实现更新
        redisTemplate.opsForValue().set(updateUserPxRequest.getJybKey(), userInfoResponse, 1, TimeUnit.DAYS);
        //修改结果
        int result=-2;
        return CommonResponse.success(200,"请求成功",result);
    }

    //添加用户金币
    public CommonResponse updateUserGold(AddUserGoldRequest addUserGoldRequest) {
        //修改缓存中的信息
        UserInfoResponse userInfoResponse=(UserInfoResponse) redisTemplate.opsForValue().get(addUserGoldRequest.getJybKey());
        userInfoResponse.setGold(userInfoResponse.getGold()+(int)(addUserGoldRequest.getMoney()*100));
        updateUserGoldRequest updateUserGoldRequest=new updateUserGoldRequest();
        updateUserGoldRequest.setGold((int)(addUserGoldRequest.getMoney()*100));
        updateUserGoldRequest.setJybKey(addUserGoldRequest.getJybKey());
        //重新设置缓存信息实现更新
        redisTemplate.opsForValue().set(addUserGoldRequest.getJybKey(), userInfoResponse, 1, TimeUnit.DAYS);
        //修改结果
        int result=-2;
        int result2=-2;
        //
        AddUserMoneyHistory addUserMoneyHistory=new AddUserMoneyHistory();
        addUserMoneyHistory.setJybKey(addUserGoldRequest.getJybKey());
        addUserMoneyHistory.setChangeGold((int)(addUserGoldRequest.getMoney()*100));
        addUserMoneyHistory.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        addUserMoneyHistory.setTransactionType("金币充值");
        addUserMoneyHistory.setOperactionType("收入");
        //
        List<MoneyHistoryResponse> moneyHistoryResponseList = (List<MoneyHistoryResponse>)redisTemplate.opsForValue().get("getUserMoneyHistory"+addUserGoldRequest.getJybKey());
        if(moneyHistoryResponseList==null){
            List<MoneyHistoryResponse> moneyHistoryResponseList1=new ArrayList<>();
            MoneyHistoryResponse moneyHistoryResponse=new MoneyHistoryResponse();
            BeanUtils.copyProperties(addUserMoneyHistory,moneyHistoryResponse);
            moneyHistoryResponseList1.add(moneyHistoryResponse);
            //放入缓存中
            redisTemplate.opsForValue().set("getUserMoneyHistory"+addUserGoldRequest.getJybKey(), moneyHistoryResponseList1, 1, TimeUnit.DAYS);
        }else {
            MoneyHistoryResponse moneyHistoryResponse=new MoneyHistoryResponse();
            BeanUtils.copyProperties(addUserMoneyHistory,moneyHistoryResponse);
            moneyHistoryResponseList.add(moneyHistoryResponse);
            //放入缓存中
            redisTemplate.opsForValue().set("getUserMoneyHistory"+addUserGoldRequest.getJybKey(), moneyHistoryResponseList, 1, TimeUnit.DAYS);
        }
        result=updateUserGoldRequestDataSelect.writeData("updateUserGold","金币充值",addUserGoldRequest.getJybKey().charAt(0),updateUserGoldRequest);
        result2=addUserMoneyHistoryIntegerDataMonneyHistory.writeData("addUserMoneyHistory","金币详情添加",addUserGoldRequest.getJybKey().charAt(0),addUserMoneyHistory);
        return CommonResponse.success(200,"请求成功",result);
    }


   //查询个表用户数来生成jybKey值
    String buildLzjKey(){
        return dataSelect3.getUserCount();
    }


    //添加用户oepnid唯一对应的jybKey信息
    public int addUserKey(UserKey userKey){
        return userKeyWrite.addUserKey(userKey);
    }

    //或取用户已经过时的key并重新返回前台
    public UserKey getUserKey(String openId){
        return userKeyRead.getUserKey(openId);
    }

    //查询用户金币
    public CommonResponse<MoneyResponse> getUserMoney(GetUserInfoRequest getUserInfoRequest){
        MoneyResponse moneyResponse=new MoneyResponse();
        //先从缓存中拿数据
        UserInfoResponse userInfoResponse = (UserInfoResponse)redisTemplate.opsForValue().get(getUserInfoRequest.getJybKey());
        //如果拿不到，就去数据库中查询
        if (userInfoResponse==null){
            userInfoResponse=dataSelect1.readData("getUserInfo",getUserInfoRequest.getJybKey().charAt(0),getUserInfoRequest);
            //放入缓存中
            redisTemplate.opsForValue().set(getUserInfoRequest.getJybKey(), userInfoResponse, 1, TimeUnit.DAYS);
        }
        moneyResponse.setGold(userInfoResponse.getGold());
        return CommonResponse.success(200,"请求成功",moneyResponse);
    }

    //查询用户金币详情
    public CommonResponse<List<MoneyHistoryResponse>> getUserMoneyHistory(GetUserInfoRequest getUserInfoRequest){
        //先从缓存中拿数据
        List<MoneyHistoryResponse> moneyHistoryResponseList = (List<MoneyHistoryResponse>)redisTemplate.opsForValue().get("getUserMoneyHistory"+getUserInfoRequest.getJybKey());
        //如果拿不到，就去数据库中查询
        if (moneyHistoryResponseList==null){
            //进行数据库的动态操作
            moneyHistoryResponseList=getUserInfoRequestListDataMonneyHistory.readData("getUserMoneyHistory",getUserInfoRequest.getJybKey().charAt(0),getUserInfoRequest);
            //放入缓存中
            redisTemplate.opsForValue().set("getUserMoneyHistory"+getUserInfoRequest.getJybKey(), moneyHistoryResponseList, 1, TimeUnit.DAYS);
        }
        return CommonResponse.success(200,"请求成功",moneyHistoryResponseList);
    }
}
