package com.onion.android.java.core.util;

import java.util.Arrays;

/**
 * 数组工具类
 */
public class ArrayUtil {

    /**
     * 数组是否为空
     */
    public static <T> boolean isEmpty(T[] array){
        return array == null || array.length == 0;
    }

    /**
     * 对象是否为数组对象
     */
    public static boolean isArray(Object object){
        return null != object && object.getClass().isArray();
    }

    /**
     * 数组或集合转String
     */
    public static String toString(Object obj) {
        if (null == obj) return null;
        if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        } else if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        } else if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        } else if (ArrayUtil.isArray(obj)) {
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception ignore) {}
        }
        return obj.toString();
    }

    /**
     * 数组对象转为斜接字符串
     */
    public static <T> String array2JoinString(T[] array, CharSequence joinString ){
        return array2JoinString(array, joinString, null, null);
    }

    public static <T> String array2JoinString(T[] array, CharSequence joinString,String prefix,String suffix){
        if( null == array) return null;
        final StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        for (T item : array){
            if(isFirst){
                isFirst = false;
            } else {
                stringBuilder.append(joinString);
            }
            if(isArray(array)){
                stringBuilder.append(array2JoinString(ArrayUtil.wrap(item), joinString, prefix, suffix));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 包装数组对象
     */
    public static Object[] wrap(Object object){
        if(null == object) return null;
        if(isArray(object)){
            return (Object[]) object;
        }
        throw new RuntimeException();
    }
}
