package com.datapool;

import com.mapper.read.*;
import com.mapper.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * 动态代理数据库分表对象
 * @param <ParamClass>
 * @param <ReturnClass>
 */
@Service
public class DataSelect<ParamClass,ReturnClass> {
    private ParamClass paramClass=null;
    private ReturnClass returnClass=null;
    @Autowired
    TransactionReadA transactionReadA;
    @Autowired
    TransactionReadB transactionReadB;
    @Autowired
    TransactionReadC transactionReadC;
    @Autowired
    TransactionReadD transactionReadD;
    @Autowired
    TransactionReadE transactionReadE;
    @Autowired
    TransactionWriteA transactionWriteA;
    @Autowired
    TransactionWriteB transactionWriteB;
    @Autowired
    TransactionWriteC transactionWriteC;
    @Autowired
    TransactionWriteD transactionWriteD;
    @Autowired
    TransactionWriteE transactionWriteE;

    //读库操作代理
    public ReturnClass readData(String method,String log,char jybkey,ParamClass paramClass){
        switch (jybkey){
            case 'A':
                System.out.println("进行用户A表查询");
                System.out.println("log:"+log);
                try {
                    returnClass=(ReturnClass) transactionReadA.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(transactionReadA,paramClass);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case 'B':
                System.out.println("进行用户B表查询");
                System.out.println("log:"+log);
                try {
                    returnClass=(ReturnClass) transactionReadB.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(transactionReadB,paramClass);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case 'C':
                System.out.println("进行用户C表查询");
                System.out.println("log:"+log);
                try {
                    returnClass=(ReturnClass) transactionReadC.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(transactionReadC,paramClass);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case 'D':
                System.out.println("进行用户D表查询");
                System.out.println("log:"+log);
                try {
                    returnClass=(ReturnClass) transactionReadD.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(transactionReadD,paramClass);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                break;
            case 'E':
                System.out.println("进行用户E表查询");
                System.out.println("log:"+log);
                try {

                        returnClass=(ReturnClass) transactionReadE.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(transactionReadE,paramClass);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
        }
        return returnClass;
    }

    //写库操作代理
    public ReturnClass writeData(String method,String log,char jybkey,ParamClass paramClass){
        int result=-2;
        //添加到数据库分表
        switch (jybkey){
            case 'A':
                try {
                   // returnClass=(ReturnClass) userWriteA.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(method,new Object[]{paramClass.getClass()});
                    Method method1=transactionWriteA.getClass().getMethod(method,new Class[]{paramClass.getClass()});

                        returnClass=(ReturnClass) method1.invoke(transactionWriteA,paramClass);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                System.out.println("进行用户A表"+log);
                break;
            case 'B':
                try {
                    returnClass=(ReturnClass) transactionWriteB.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(transactionWriteB,paramClass);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                System.out.println("进行用户B表"+log);
                break;
            case 'C':
                try {
                    returnClass=(ReturnClass) transactionWriteC.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(transactionWriteC,paramClass);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                System.out.println("进行用户C表"+log);
                break;
            case 'D':
                try {
                    returnClass=(ReturnClass) transactionWriteD.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(transactionWriteD,paramClass);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                System.out.println("进行用户D表"+log);
                break;
            case 'E':
                try {
                    returnClass=(ReturnClass) transactionWriteE.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(transactionWriteE,paramClass);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                System.out.println("进行用户E表"+log);
        }
        return returnClass;
    }

    //查询并生成订单编号
    public ReturnClass getUserDDnumber(){
        String ddnumber=null;
        int r=new Random().nextInt(5);
        switch (r){
            case 0:
                ddnumber="A000000"+(transactionReadA.getDDnumber()+1);
                break;
            case 1:
                ddnumber="B000000"+(transactionReadB.getDDnumber()+1);
                break;
            case 2:
                ddnumber="C000000"+(transactionReadC.getDDnumber()+1);
                break;
            case 3:
                ddnumber="D000000"+(transactionReadD.getDDnumber()+1);
                break;
            case 4:
                ddnumber="E000000"+(transactionReadE.getDDnumber()+1);
        }
        return (ReturnClass) ddnumber;
    }
}
