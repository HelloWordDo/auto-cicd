package com.arkmon.autocicd.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    /**
     * 将一个类查询方式加入map（属性值为int型时，0时不加入，
     * 属性值为String型或Long时为null和“”不加入）
     *注：需要转换的必须是对象，即有属性
     */
    public static Map<String, Object> obj2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if(obj==null){
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();//获取类的各个属性值
        for(Field field : fields){
            String fieldName =  field.getName();//获取类的属性名称
            if(getValueByFieldName(fieldName,obj)!=null)//获取类的属性名称对应的值
            {
                map.put(fieldName,  getValueByFieldName(fieldName,obj));
            }
        }
        return map;
    }

    /**
     * 根据属性名获取该类此属性的值
     * @param fieldName
     * @param object
     * @return
     */
    private static Object getValueByFieldName(String fieldName, Object object){
        String firstLetter=fieldName.substring(0,1).toUpperCase();
        String getter = "get"+firstLetter+fieldName.substring(1);
        try {
            Method method = object.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(object, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }
}
