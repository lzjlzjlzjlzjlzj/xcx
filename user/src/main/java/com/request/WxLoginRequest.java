package com.request;

import lombok.Data;

@Data
public class WxLoginRequest {
    String encryptedData;
    String iv;
    String code;
}
