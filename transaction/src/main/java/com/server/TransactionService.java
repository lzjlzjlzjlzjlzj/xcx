package com.server;

import com.datapool.DataSelect;
import com.request.GetOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    //注册加载生成订单号的对象
    @Autowired
    DataSelect<String,String> dataSelect;

    //装载通过用户jybkey查询订单号集合对象
    @Autowired
    DataSelect<List<String>, GetOrderRequest> listGetOrderRequestDataSelect;
}
