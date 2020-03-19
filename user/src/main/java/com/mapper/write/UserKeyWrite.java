package com.mapper.write;

import com.domain.UserKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserKeyWrite {
    int addUserKey(UserKey userKey);
}
