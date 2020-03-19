package com.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * lzj
 */
@Data
public class WeChatMessage {
    //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
    @NotNull(message = "验证参数不能为空")
    private String signature;
    //时间戳
    @NotNull(message = "验证参数不能为空")
    private String timestamp;
    //随机数
    @NotNull(message = "验证参数不能为空")
    private String nonce;
   //随机字符串
   @NotNull(message = "验证参数不能为空")
   private String echostr;
}
