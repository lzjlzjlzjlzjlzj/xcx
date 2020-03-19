package com.mapper.read;

import com.domain.UserInfoKey;
import com.request.GetUserInfoRequest;
import com.response.UserInfoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserReadA {
    //获取用户基本信息
    UserInfoResponse getUserInfo(GetUserInfoRequest getUserInfoRequest);

    //获取当前表用户数量生成唯一key值
    int getUserCount();
}
