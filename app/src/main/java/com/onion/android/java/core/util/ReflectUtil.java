package com.onion.android.java.core.util;

import com.onion.android.java.core.lang.SimpleCache;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * 反射工具类
 */
public class ReflectUtil {

    // 构造对象缓存
    private static final SimpleCache<Class<?>, Constructor<?>[]> CONSTRUCTORS_CACHE = new SimpleCache<>();

    /**
     * 通过映射方式实例化对象
     * @param <T>    对象类型
     * @param tClass  类
     * @param params 构造函数参数
     * @return 对象
     */
    public static <T> T newInstance(Class<T> tClass,Object ... params) {
        if(ArrayUtil.isEmpty(params)){
            final Constructor<T> constructor = getConstructorWithParams(tClass);
            try {
                return constructor.newInstance();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

        final Class<?>[] paramTypes = ClassUtil.getClasses(params);
        final Constructor<T> constructor = getConstructorWithParams(tClass, paramTypes);
        if(null == constructor){
            throw new RuntimeException();
        }
        try {
            return constructor.newInstance();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
    /**
     * 查找类中的指定参数的构造方法，如果找到构造方法，并设置其为可访问状态
     *
     * @param <T>            对象类型
     * @param tClass          类
     * @param parameterTypes 参数类型，只要任何一个参数是指定参数的父类或接口或相等即可，此参数可以不传
     * @return 构造方法，如果未找到返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> getConstructorWithParams(Class<T> tClass, Class<?> ... parameterTypes){
        if( null == tClass) return null;
        final Constructor<?>[] constructors = getConstructor(tClass);
        Class<?>[] originParameterTypes;
        for (Constructor<?> constructor : constructors){
            originParameterTypes = constructor.getParameterTypes();
            if(ClassUtil.isFromSameClass(originParameterTypes,parameterTypes)){
                // 设置类构造可访问
                setAccessible(constructor);
                return (Constructor<T>) constructor;
            }
        }
        return null;
    }
    /**
     * 设置类构造为可访问（私有方法可以被外部调用）
     * @param <T>              AccessibleObject的子类，比如Class、Method、Field等
     * @param accessibleObject 可设置访问权限的对象，比如Class、Method、Field等
     * @return 被设置可访问的对象
     */
    public static <T extends AccessibleObject>T setAccessible(T accessibleObject){
        if(null != accessibleObject && false == accessibleObject.isAccessible()){
            accessibleObject.setAccessible(true);
        }
        return accessibleObject;
    }
    /**
     * 获得一个类中所有构造列表.优先从缓存获取
     * @param <T> 构造的对象类型
     * @param beanClass 类
     * @return 所有构造列表
     * @throws SecurityException 安全检查异常
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T>[] getConstructor(Class<T> beanClass) throws SecurityException{
        Objects.requireNonNull(beanClass);
        Constructor<?>[] constructors = CONSTRUCTORS_CACHE.get(beanClass);
        if(null != constructors) return (Constructor<T>[]) constructors;
        constructors = getConstructorsDirectly(beanClass);
        return (Constructor<T>[]) CONSTRUCTORS_CACHE.put(beanClass, constructors);
    }
    /**
     * 获得一个类中所有构造列表，直接反射获取，无缓存
     * @param beanClass 类
     * @return 所有构造列表
     */
    public static Constructor<?>[] getConstructorsDirectly(Class<?> beanClass) throws SecurityException{
        Objects.requireNonNull(beanClass);
        return beanClass.getDeclaredConstructors();
    }
}
