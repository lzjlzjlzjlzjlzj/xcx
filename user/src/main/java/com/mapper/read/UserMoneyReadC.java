package com.mapper.read;

import com.request.GetUserInfoRequest;
import com.response.MoneyHistoryResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMoneyReadC {
    List<MoneyHistoryResponse> getUserMoneyHistory(GetUserInfoRequest getUserInfoRequest);
}
