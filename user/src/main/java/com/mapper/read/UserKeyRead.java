package com.mapper.read;

import com.domain.UserKey;
import com.response.UserInfoKeyResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserKeyRead {
    UserKey getUserKey(String openId);
}
