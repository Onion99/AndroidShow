package com.onion.android.java.core.lang;

import com.onion.android.java.core.func.Func0;
import com.onion.android.java.core.text.StringFormatter;
import com.onion.android.java.core.util.ArrayUtil;
import com.onion.android.java.core.util.ReflectUtil;

import java.util.HashMap;
import java.util.Objects;

/**
 * 单例
 * 提供单例对象的统一管理，当调用get方法时，如果对象池中存在此对象，返回此对象，否则创建新对象返回<br>
 *
 * @author loolly
 */
public final class Singleton {

    private static final SimpleCache<String, Object> POOL = new SimpleCache<>(new HashMap<>());

    public Singleton() { }

    /**
     * 缓存key 构建
     * @param className 类名
     * @param params 参数列表
     * @return key
     */
    private static String buildKey(String className, Object ... params){
        if(ArrayUtil.isEmpty(params)){
            return className;
        }
        return StringFormatter.format("{}#{}", className, ArrayUtil.array2JoinString(params, "_"));
    }
    /**
     * 获得指定类的单例对象,同时传递当没有对象时调用的构建函数
     * @param <T>      单例对象类型
     * @param key      自定义键
     * @param supplier 单例对象的创建函数
     * @return 单例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Func0<T> supplier){
        return (T) POOL.get(key, supplier::call);
    }
    /**
     * 获得指定类的单例对象
     * 对象存在于池中返回，否则创建，每次调用此方法获得的对象为同一个对象<br>
     * 注意：单例针对的是类和参数，也就是说只有类、参数一致才会返回同一个对象
     *
     * @param <T>    单例对象类型
     * @param tClass  类
     * @param params 构造方法参数
     * @return 单例对象
     */
    public static <T> T get(Class<T> tClass,Object ... params){
        final String key = buildKey(tClass.getName(), params);
        return get(key, () -> ReflectUtil.newInstance(tClass, params));
    }
    /**
     * 将已有对象放入单例中，其Class做为键
     * @param obj 对象
     */
    public static void put(Object obj) {
        Objects.requireNonNull(obj);
        POOL.put(obj.getClass().getName(), obj);
    }
    /**
     * 移除指定Singleton对象
     */
    public static void remove(Class<?> clazz) {
        if (null != clazz) {
            POOL.remove(clazz.getName());
        }
    }
}
