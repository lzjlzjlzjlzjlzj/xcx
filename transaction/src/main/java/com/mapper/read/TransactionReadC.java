package com.mapper.read;

import com.domain.TransactionOrder;
import com.request.GetOrderRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface TransactionReadC {
    //通过jybkey查询用户订单号集合
    public List<String> getOrderList(GetOrderRequest getOrderRequest);

    //通过订单号集合查询订单详情集合
    public List<TransactionOrder> getUserTransactionOrder(List<String> list);

    //通过查询最后的主键id生成订单号
    public int getDDnumber();
}
