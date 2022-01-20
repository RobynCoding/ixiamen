package com.ixiamen.activity.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射的一些实用方法。
 * 
 * @author luoyongbin
 * @version 1.0 
 */
public class ReflectHelper {

    /**
     * 不提供实例
     */
    private ReflectHelper() {
    }

    /**
     * 动态调用方法。
     * 
     * @param clazz Object 调方法的对象
     * @param method String 调用的方法名称
     * @param params Object[] 调用方法所用到的参数数组
     * @return Object 属性值
     * @throws InvocationTargetException 调用异常
     * @throws IllegalAccessException 非法访问
     * @throws IllegalArgumentException 参数非法
     * @throws NoSuchMethodException 没有该方法
     */
    public static Object invokeMethodOnClass(Object clazz, String method, Object[] params)
    		throws IllegalArgumentException, IllegalAccessException,
    		InvocationTargetException, NoSuchMethodException {
        return getMethodByName(clazz, method).invoke(clazz, params);
    }

    /**
     * 查找类的方法
     * 
     * @param clazz Object 类
     * @param name String 方法名
     * @throws NoSuchMethodException 没有该方法
     * @return Method 类的方法
     */
    public static Method getMethodByName(Object clazz, String name) throws NoSuchMethodException {
        Method[] mts = clazz.getClass().getMethods();
        for (Method mt : mts) {
            if (name.equals(mt.getName())) {
                return mt;
            }
        }
        throw new NoSuchMethodException(name);
    }

    /**
     * 构造Getter
     * @param property 属性名
     * @return String，如：name返回getName
     */
    public static String buildGetter(String property) {
        return toMethod("get", property);
    }

    /**
     * 构造Setter
     * @param property 属性名
     * @return String，如：name返回setName
     */
    public static String buildSetter(String property) {
        return toMethod("set", property);
    }

    // 构造BEAN的getter/setter
    private static String toMethod(String m, String property) {
        String re = m;
        re += property.substring(0, 1).toUpperCase();
        re += property.substring(1);
        return re;
    }
}
