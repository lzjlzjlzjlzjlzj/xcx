package com.mapper.write;

import com.domain.TransactionOrder;
import com.domain.TransactionOrderMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TransactionWriteE {
    //添加一个订单映射
    public int addOrderMapper(TransactionOrderMapper transactionOrderMapper);
    //添加一条交易订单
    public int addOrder(TransactionOrder transactionOrder);

}
