package com.ushaqi.zhuishushenqi.httpcore;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * 反射类型
 * Created by fly on 2018/4/20.
 */

public class JavaClassHelper {

    public static Type getClassType(Class clz) {

        Type[] genericInterfaces = clz.getGenericInterfaces();
        if (genericInterfaces.length > 0) {
            if (genericInterfaces[0] instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericInterfaces[0];
                Type[] actualTypeArguments = pt.getActualTypeArguments();
                return actualTypeArguments[0];
            }
        }
        return null;
    }

    public static boolean typeInstanceofList(Type type){
        if(type==null){
            return false;
        }
        return type.toString().matches("java.util.List<.*>");//.contains("java.util.List<>")?true:false;
    }
}
