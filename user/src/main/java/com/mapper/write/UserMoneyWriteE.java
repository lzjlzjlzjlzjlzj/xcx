package com.mapper.write;

import com.domain.AddUserMoneyHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMoneyWriteE {
    int addUserMoneyHistory(AddUserMoneyHistory addUserMoneyHistory);
}
