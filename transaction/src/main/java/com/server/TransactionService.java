package com.server;

import com.datapool.DataSelect;
import com.domain.TransactionOrder;
import com.domain.TransactionOrderSelect;
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
    DataSelect<GetOrderRequest, List<String>> listGetOrderRequestDataSelect;

    //装载通过订单号集合查询订单详情对象
    @Autowired
    DataSelect<List<TransactionOrder>,List<String>> listListDataSelect;

    //通过用户jybkey查询订单详情
    public List<TransactionOrder> getTransactionOrder(GetOrderRequest getOrderRequest){
        //获取到订单号集合
        List ddlist=listGetOrderRequestDataSelect.readData("getOrderList","查询订单号集合",getOrderRequest.getJybKey().charAt(0),getOrderRequest);
         return null;
    }

    //将订单号集合转化为订单号对象
    public TransactionOrderSelect ListToTransactionOrderSelect(List  l){
        TransactionOrderSelect transactionOrderSelect=new TransactionOrderSelect();
        if(l==null){
            return transactionOrderSelect;
        }
        for (int i=0;i<l.size();i++){
            switch (l.get(i).toString().charAt(0)){
                case 'A':
                    transactionOrderSelect.getA().add(l.get(i));
                    break;
                case 'B':
                    transactionOrderSelect.getB().add(l.get(i));
                    break;
                case 'C':
                    transactionOrderSelect.getC().add(l.get(i));
                    break;
                case 'D':
                    transactionOrderSelect.getD().add(l.get(i));
                    break;
                case 'E':
                    transactionOrderSelect.getE().add(l.get(i));
                    break;
                    default:
                        break;
            }
        }
        return transactionOrderSelect;
    }

    //通过订单号对象查询订单对象集合
    public List<TransactionOrder> getListTransactionOrder(TransactionOrderSelect transactionOrderSelect){
        List listA=listListDataSelect.readData("getUserTransactionOrder","订单查询",'A',transactionOrderSelect.getA());
        List listB=listListDataSelect.readData("getUserTransactionOrder","订单查询",'B',transactionOrderSelect.getA());
        List listC=listListDataSelect.readData("getUserTransactionOrder","订单查询",'C',transactionOrderSelect.getA());
        List listD=listListDataSelect.readData("getUserTransactionOrder","订单查询",'D',transactionOrderSelect.getA());
        List listE=listListDataSelect.readData("getUserTransactionOrder","订单查询",'E',transactionOrderSelect.getA());
        return addList(listE,addList(addList(listC,listD),addList(listA,listB)));
    }

    //和并list
    public List addList(List l1,List l2){
        List l=null;
        if(l1==null){
            for (int i=0;i<l2.size();i++){
                l.add(l2.get(i));
            }
            return l;
        }
        for (int i=0;i<l1.size();i++){
            l.add(l1.get(i));
        }
        if(l2==null){
            return l;
        }
        for (int i=0;i<l2.size();i++){
            l.add(l2.get(i));
        }
        return l;
    }
}
