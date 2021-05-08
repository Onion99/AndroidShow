package com.onion.android.java.core.util;

import com.onion.android.java.core.bean.NullWrapperBean;
import com.onion.android.java.core.convert.BasicType;

/**
 * 类工具类
 */
public class ClassUtil {
    /**
     * 是否为基本类型
     * @param clazz 类
     */
    public static boolean isBaseType(Class<?> clazz){
        if(null == clazz) return false;
        return (clazz.isPrimitive() || isPrimaryType(clazz));
    }
    /**
     * 是否为包装类型
     */
    public static boolean isPrimaryType(Class<?> clazz){
        if(null == clazz) return false;
        return BasicType.WRAPPER_PRIMITIVE_MAP.containsKey(clazz);
    }
    /**
     * 判断两组类属性参数是否相同
     * @param types1 类组1
     * @param types2 类组2
     * @return 是否相同、父类或接口
     */
    public static boolean isFromSameClass(Class<?>[] types1,Class<?>[] types2){
        if(ArrayUtil.isEmpty(types1) && ArrayUtil.isEmpty(types2)) return true;
        if( null == types1 || null == types2) return false;
        if(types1.length != types2.length) return false;
        Class<?> type1, type2;
        for (int i = 0; i < types1.length; i++) {
            type1 = types1[i];
            type2 = types2[i];
            if(isBaseType(type1) && isBaseType(type2)){
                if(BasicType.unWrap(type1) != BasicType.unWrap(type2)){
                    return false;
                }
            }else if(false == type1.isAssignableFrom(type2)){
                return false;
            }
        }
        return true;
    }

    public static Class<?>[] getClasses(Object ... objects){
        Class<?>[] classes = new Class<?>[objects.length];
        Object obj;
        for (int i = 0; i < classes.length; i++) {
            obj = classes[i];
            if(obj instanceof NullWrapperBean){
                classes[i] = ((NullWrapperBean<?>) obj).getWrappedClass();
            }else if(null == obj){
                classes[i] = Object.class;
            }else{
                classes[i] = obj.getClass();
            }
        }
        return classes;
    }
}
