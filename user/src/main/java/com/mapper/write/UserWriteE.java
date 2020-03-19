package com.mapper.write;

import com.domain.UserInfoKey;
import com.request.updateUserGoldRequest;
import com.request.updateUserPxRequest;
import com.request.updateUserXyScoreRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserWriteE {
    int addUser(UserInfoKey userInfo);
    int updateUserXyScore(updateUserXyScoreRequest updateUserXyScoreRequest);
    int updateUserPx(updateUserPxRequest updateUserPxRequest);
    int updateUserGold(updateUserGoldRequest updateUserGoldRequest);
}
