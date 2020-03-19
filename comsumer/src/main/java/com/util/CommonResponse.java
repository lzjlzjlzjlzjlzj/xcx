package com.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.sql.DataSourceDefinition;

/**
 * lzj
 * 公共返回类
 * @param <D>
 */
@Data
public class CommonResponse <D>{
    private int code;
    private String message;
    private D data;
    public CommonResponse(int code,String message){
        this.code=code;
        this.message=message;
    }
    public CommonResponse(int code,String message,D data){
        this.code=code;
        this.message=message;
        this.data=data;
    }
    public static CommonResponse fail(int code,String message){
        return new CommonResponse(code,message);
    }
    public static CommonResponse success(int code,String message){
        return new CommonResponse(code,message);
    }
    public static <D>CommonResponse success(int code,String message,D data){
        return new CommonResponse<D>(code,message,data);
    }
}
