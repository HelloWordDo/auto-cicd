package com.arkmon.autocicd.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author X.J
 * @date 2020/4/20
 */
@Slf4j
public class EnumUtil {
    private static final String GET_CODE_METHOD = "getCode";
    private static final String GET_NAME_METHOD = "getName";

    public static Enum<?> checkEnumByParameter(Class<? extends Enum<?>> clazz, Object enumParameter){
        Enum<?> t = checkEnumByParameter(clazz, GET_CODE_METHOD, enumParameter);
        if (t != null) {
            return t;
        }
        return checkEnumByParameter(clazz, GET_NAME_METHOD, enumParameter);
    }

    /**
     * 通过传入一个枚举参数，判断该参数是否在指定的枚举里，存在则返回枚举，不存在返回null
     * @author X.J
     * @date 14:22 2021/2/23
     * @param clazz 传入的枚举类名
     * @param getEnumMethodName 传入的枚举类clazz中的方法名称
     * @param enumParameter 枚举参数
     * @return T 具体的枚举值 或者 null
     */
    public static Enum<?> checkEnumByParameter(Class<? extends Enum<?>> clazz, String getEnumMethodName, Object enumParameter){
        Enum<?> result = null;
        try{
            //Enum接口中没有values()方法，我们仍然可以通过Class对象取得所有的enum实例
            Enum<?>[] arr = clazz.getEnumConstants();
            //获取定义的方法
            Method targetMethod = clazz.getDeclaredMethod(getEnumMethodName);
            if (targetMethod == null) {
                log.error("getEnumMethodName=" + getEnumMethodName + " 不存在");
                return null;
            }
            Object typeVal;
            //遍历枚举定义
            for(Enum<?> entity:arr){
                if (enumParameter instanceof Integer) {
                    typeVal = Integer.valueOf(String.valueOf(targetMethod.invoke(entity)));
                } else if (enumParameter instanceof String) {
                    //获取传过来方法的
                    typeVal = String.valueOf(targetMethod.invoke(entity)).replace(" ","");
                    //执行的方法的值等于参数传过来要判断的值
                    enumParameter = ((String) enumParameter).replace(" ", "");
                } else {
                    log.error("传入的enumType不是Integer也不是String类型");
                    return null;
                }
                if(typeVal.equals(enumParameter)){
                    //返回这个枚举
                    result = entity;
                    break;
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T extends Enum<T>> T getEnumByParameter(Class<T> clazz, Object enumParameter){
        T t = getEnumByParameter(clazz, GET_CODE_METHOD, enumParameter);
        if (t != null) {
            return t;
        }
        return getEnumByParameter(clazz, GET_NAME_METHOD, enumParameter);
    }

    public static <T extends Enum<T>> T getEnumByParameter(Class<T> clazz, String getEnumMethodName, Object enumParameter){
        T result = null;
        try{
            //Enum接口中没有values()方法，我们仍然可以通过Class对象取得所有的enum实例
            T[] arr = clazz.getEnumConstants();
            //获取定义的方法
            Method targetMethod = clazz.getDeclaredMethod(getEnumMethodName);
            if (targetMethod == null) {
                log.error("getEnumMethodName=" + getEnumMethodName + " 不存在");
                return null;
            }
            Object typeVal;
            //遍历枚举定义
            for(T entity:arr){
                if (enumParameter instanceof Integer) {
                    typeVal = Integer.valueOf(String.valueOf(targetMethod.invoke(entity)));
                } else if (enumParameter instanceof String) {
                    //获取传过来方法的
                    typeVal = String.valueOf(targetMethod.invoke(entity)).replace(" ","");
                    //执行的方法的值等于参数传过来要判断的值
                    enumParameter = ((String) enumParameter).replace(" ", "");
                } else {
                    log.error("传入的enumType不是Integer也不是String类型");
                    return null;
                }
                if(typeVal.equals(enumParameter)){
                    //返回这个枚举
                    result = entity;
                    break;
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }
}
