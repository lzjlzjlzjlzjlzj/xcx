package com.response;

import lombok.Data;

@Data
public class ImgUrlResponse {
    String url;
    public ImgUrlResponse(String url){
        this.url=url;
    }
}
