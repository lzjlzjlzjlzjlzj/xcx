package com.datapool;

import com.mapper.read.*;
import com.mapper.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class DataMonneyHistory<ParamClass,ReturnClass> {
    @Autowired
    private UserMoneyReadA userMoneyReadA;
    @Autowired
    private UserMoneyWriteA userMoneyWriteA;
    @Autowired
    private UserMoneyReadB userMoneyReadB;
    @Autowired
    private UserMoneyWriteB userMoneyWriteB;
    @Autowired
    private UserMoneyReadC userMoneyReadC;
    @Autowired
    private UserMoneyWriteC userMoneyWriteC;
    @Autowired
    private UserMoneyReadD userMoneyReadD;
    @Autowired
    private UserMoneyWriteD userMoneyWriteD;
    @Autowired
    private UserMoneyReadE userMoneyReadE;
    @Autowired
    private UserMoneyWriteE userMoneyWriteE;
    private ParamClass paramClass=null;
    private ReturnClass returnClass=null;
    //读库操作代理
    public ReturnClass readData(String method,char jybkey,ParamClass paramClass){
        switch (jybkey){
            case 'A':
                System.out.println("进行用户A表查询");
                try {
                    returnClass=(ReturnClass) userMoneyReadA.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userMoneyReadA,paramClass);
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
                try {
                    returnClass=(ReturnClass) userMoneyReadB.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userMoneyReadB,paramClass);
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
                try {
                    returnClass=(ReturnClass) userMoneyReadC.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userMoneyReadC,paramClass);
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
                try {
                    returnClass=(ReturnClass) userMoneyReadD.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userMoneyReadD,paramClass);
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
                try {

                    returnClass=(ReturnClass) userMoneyReadE.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userMoneyReadE,paramClass);

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
                    returnClass=(ReturnClass) userMoneyWriteA.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userMoneyWriteA,paramClass);

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
                    returnClass=(ReturnClass) userMoneyWriteB.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userMoneyWriteB,paramClass);
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
                    returnClass=(ReturnClass) userMoneyWriteC.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userMoneyWriteC,paramClass);
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
                    returnClass=(ReturnClass) userMoneyWriteD.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userMoneyWriteD,paramClass);
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
                    returnClass=(ReturnClass) userMoneyWriteE.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userMoneyWriteE,paramClass);
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
}
