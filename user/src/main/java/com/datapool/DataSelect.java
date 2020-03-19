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
    @Autowired
    private UserReadA userReadA;
    @Autowired
    private UserWriteA userWriteA;
    @Autowired
    private UserReadB userReadB;
    @Autowired
    private UserWriteB userWriteB;
    @Autowired
    private UserReadC userReadC;
    @Autowired
    private UserWriteC userWriteC;
    @Autowired
    private UserReadD userReadD;
    @Autowired
    private UserWriteD userWriteD;
    @Autowired
    private UserReadE userReadE;
    @Autowired
    private UserWriteE userWriteE;
    private ParamClass paramClass=null;
    private ReturnClass returnClass=null;

    //读库操作代理
    public ReturnClass readData(String method,char jybkey,ParamClass paramClass){
        switch (jybkey){
            case 'A':
                System.out.println("进行用户A表查询");
                try {
                    returnClass=(ReturnClass) userReadA.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userReadA,paramClass);
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
                    returnClass=(ReturnClass) userReadB.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userReadB,paramClass);
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
                    returnClass=(ReturnClass) userReadC.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userReadC,paramClass);
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
                    returnClass=(ReturnClass) userReadD.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userReadD,paramClass);
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

                        returnClass=(ReturnClass) userReadE.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userReadE,paramClass);

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
                    Method method1=userWriteA.getClass().getMethod(method,new Class[]{paramClass.getClass()});

                        returnClass=(ReturnClass) method1.invoke(userWriteA,paramClass);

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
                    returnClass=(ReturnClass) userWriteB.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userWriteB,paramClass);
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
                    returnClass=(ReturnClass) userWriteC.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userWriteC,paramClass);
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
                    returnClass=(ReturnClass) userWriteD.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userWriteD,paramClass);
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
                    returnClass=(ReturnClass) userWriteE.getClass().getMethod(method,new Class[]{paramClass.getClass()}).invoke(userWriteE,paramClass);
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

    //查表用户数量静态代理
    public ReturnClass getUserCount(){
        String newkey=null;
        int r=new Random().nextInt(5);
        switch (r){
            case 0:
                newkey="AjybKey"+(userReadA.getUserCount()+1);
                break;
            case 1:
                newkey="BjybKey"+(userReadB.getUserCount()+1);
                break;
            case 2:
                newkey="CjybKey"+(userReadC.getUserCount()+1);
                break;
            case 3:
                newkey="DjybKey"+(userReadD.getUserCount()+1);
                break;
            case 4:
                newkey="EjybKey"+(userReadE.getUserCount()+1);
        }
        return (ReturnClass) newkey;
    }
}
