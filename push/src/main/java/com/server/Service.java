package com.server;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.request.WeChatMessage;
import org.apache.tomcat.util.json.JSONParser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@org.springframework.stereotype.Service
public class Service {
    public String checkSignature(WeChatMessage message) {
        String signature = message.getSignature();
        String timestamp = message.getTimestamp();
        String nonce = message.getNonce();
        String echostr=message.getEchostr();
        String token = "lzjlzj";
        String aesKey ="99i9kDV6Ye1SJVD7PYYnrBQP3DFURLtbo7WIqglbeKP";
        System.out.println(message.getEchostr());
        System.out.println("执行解密............");
        /*
        1.将token、timestamp、nonce三个参数进行字典序排序
        2.将三个参数字符串拼接成一个字符串进行sha1加密
        3.开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        */
        String[] strarr = {token,timestamp,nonce};
        Arrays.sort(strarr);
        StringBuilder sb = new StringBuilder();
        for (String str : strarr) {
            sb.append(str);
        }
        String laststring = sb.toString();
        String checksign = this.sha1(laststring);
        if (checksign != null && checksign.equals(signature)) {
            return echostr;
        }
        return "Error";
    }

    private String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // 创建 16进制字符串
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
