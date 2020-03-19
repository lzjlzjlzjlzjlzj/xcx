package com.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.domain.UserInfoKey;
import com.domain.UserKey;
import com.response.CommonResponse;
import com.response.UserInfoKeyResponse;
import com.response.UserInfoResponse;
import com.tempjdbc.TempUser;
import com.util.AesCbcUtil;
import com.util.HttpRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LoginService {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    public CommonResponse<UserInfoKeyResponse> decodeUserInfo(String encryptedData, String iv, String code) {

       UserInfoKeyResponse u=new UserInfoKeyResponse();
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return CommonResponse.fail(201,"用户凭证为空");
        }

        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wxebe867e7470df671";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "126d556f0bf3a3101bb707a7ef626d53";
        //授权（必填）
        String grant_type = "authorization_code";


        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSON.parseObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");

        //////////////// 2、对encryptedData加密数据进行AES解密其中包含这openid和unionid ////////////////
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                JSONObject userInfoJSON = JSON.parseObject(result);
                UserInfoKey userInfo=new UserInfoKey();
                userInfo.setOpenId((String) userInfoJSON.get("openId"));
                userInfo.setAvatarUrl((String)userInfoJSON.get("avatarUrl"));
                userInfo.setCity((String)userInfoJSON.get("city"));
                userInfo.setNickName((String)userInfoJSON.get("nickName"));
                userInfo.setCountry((String)userInfoJSON.get("country"));
                userInfo.setUnionId((String)userInfoJSON.get("unionId"));
                userInfo.setGender((Integer.toString((Integer)userInfoJSON.get("gender"))));
                userInfo.setProvince((String)userInfoJSON.get("province"));
                //判断是是请求找回丢失的key
                //通过openid获取缓存信息
                UserKey userKey=(UserKey) redisTemplate.opsForValue().get((String) userInfoJSON.get("openId")) ;
                //设置jybkey
                u.setJybkey(userKey.getJybKey());
                if(userKey==null){
                    //没有缓存信息则判断数据库,有可能缓存过期
                    UserKey userKey1=userService.getUserKey((String) userInfoJSON.get("openId"));
                    if(userKey1==null){
                        //数据库也无相关信息，则添加用户所有信息
                        //定义交易宝key并生成
                        String key=userService.buildLzjKey();
                        //存入openid与jybKey映射信息
                        UserKey userKey2=new UserKey();
                        userKey2.setOpenId((String) userInfoJSON.get("openId"));
                        userKey2.setJybKey(key);
                        userKey2.setUnionId((String)userInfoJSON.get("unionId"));
                        //存库用户信息对象，及向前台返回对象赋
                        userInfo.setJybKey(key);
                        u.setJybkey(key);
                        //将用户信息存入缓存及openid与jybKey映射
                        redisTemplate.opsForValue().set(openid,userKey2);
                        //进行转换为UserInfoResponse
                        UserInfoResponse userInfoResponse=new UserInfoResponse();
                        BeanUtils.copyProperties(userInfo,userInfoResponse);
                        redisTemplate.opsForValue().set(key,userInfoResponse);
                        //用户信息存库
                        System.out.println("存入用户交易宝Key为"+userInfo.getJybKey()+"状态:"+userService.addUser(userInfo));
                        System.out.println("添加用户openId与jybKey对应信息-状态:"+userService.addUserKey(userKey2));
                    }else {
                        //写入缓存并返回jybKey
                        redisTemplate.opsForValue().set(openid,userKey1);
                        u.setJybkey(userKey1.getJybKey());
                        return CommonResponse.success(200,"找回jybKey成功",u);
                    }
                }

                return CommonResponse.success(200,"解密成功",u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponse.fail(202,"解密失败");
    }
}
