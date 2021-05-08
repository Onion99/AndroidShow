package com.onion.android.java.core.lang;

import androidx.annotation.NonNull;

import com.onion.android.java.core.func.Func0;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 简单缓存，无超时实现，默认使用{@link WeakHashMap}实现缓存自动清理
 * @interface Iterable<Map.Entry<K,V>> - 实现此接口可使对象成为“ for-each循环”语句的目标
 * @param <K> 键类型
 * @param <V> 值类型
 * @author Looly
 */
public class SimpleCache<K,V> implements Iterable<Map.Entry<K,V>> {
    /**
     * 构造
     * 默认使用{@link WeakHashMap}实现缓存自动清理
     */
    public SimpleCache(){
        this(new WeakHashMap<>());
    }
    /**
     * 构造
     * 通过自定义Map初始化，可以自定义缓存实现。<br>
     * 比如使用{@link WeakHashMap}则会自动清理key，使用HashMap则不会清理<br>
     * 同时，传入的Map对象也可以自带初始化的键值对，防止在get时创建
     *  @param initMap 初始Map，用于定义Map类型
     */
    public SimpleCache(Map<K,V> initMap){
        this.cache = initMap;
    }
    // 缓存池
    private final Map<K,V> cache;
    // 写的时候每个key一把锁，降低锁的粒度
    protected final Map<K, Lock> keyLockMap = new ConcurrentHashMap<>();
    /**
     * 乐观读写锁
     * 1.访问顺序
     *  a - 非公平模式（默认）,连续竞争的非公平锁可能会无限期地延迟一个或多个读取器或写入器线程，但通常比公平锁具有更高的吞吐量
     *  b - 公平模式 , 线程利用一个近似到达顺序的策略来争夺进入。当释放当前保持的锁时，可以为等待时间最长的单个writer线程分配写入锁，如果有一组等待时间大于所有正在等待的writer线程的reader，将为该组分配读者锁
     *  c - 试图获得公平写入锁的非重入的线程将会阻塞，除非读取锁和写入锁都自由（这意味着没有等待线程）。
     * 2.重入
     *  a - 此锁允许reader和writer按照 ReentrantLock 的样式重新获取读取锁或写入锁。在写入线程保持的所有写入锁都已经释放后，才允许重入reader使用读取锁。
     *  b - writer可以获取读取锁，但reader不能获取写入锁。
     * 3.锁降级
     *  a - 重入还允许从写入锁降级为读取锁，实现方式是：先获取写入锁，然后获取读取锁，最后释放写入锁。但是，从读取锁升级到写入锁是不可能的。
     * 4.锁获取的中断
     *  a - 读取锁和写入锁都支持锁获取期间的中断。
     * 5.Condition支持
     *  a - 写入锁提供了一个 Condition 实现，对于写入锁来说，该实现的行为与 ReentrantLock.newCondition() 提供的 Condition 实现对 ReentrantLock 所做的行为相同
     *  b - 此 Condition 只能用于写入锁。
     * 6.监测
     *  a - 此类支持一些确定是读取锁还是写入锁的方法。这些方法设计用于监视系统状态，而不是同步控制
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 取值
     * @param key 键
     * @return 值
     */
    public V get(K key){
        lock.readLock().lock();
        try {
            return cache.get(key);
        }finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 取值
     * @param key 键
     * @return 值
     */
    public V get(K key, Func0<V> supplier){
        V v = get(key);
        if(null == v && null != supplier){
            // 如果指定的键为null，则尝试使用给定的映射函数计算其值，除非null否则将其输入此映射
            final Lock keyLock = keyLockMap.computeIfAbsent(key, k -> new ReentrantLock());
            keyLock.lock();
            try {
                // 再次检查,防止在竞争锁的过程中已经有其它线程写入
                v = cache.get(key);
                if(null == v){
                    try {
                        v = supplier.call();
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                    put(key, v);
                }
            }finally {
                keyLock.unlock();
                keyLockMap.remove(key);
            }
        }
        return v;
    }
    /**
     * 取值
     *
     * @param key   键
     * @param value 值
     * @return 值
     */
    public V put(K key, V value){
        // 独占写锁
        lock.writeLock().lock();
        try {
            cache.put(key, value);
        }finally {
            lock.writeLock().unlock();
        }
        return value;
    }

    /**
     * 删值
     * @param key 键
     * @return 值
     */
    public V remove(K key){
        // 独占写锁
        lock.writeLock().lock();
        try {
            return cache.remove(key);
        }finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 清空缓存池
     */
    public void clear(){
        // 独占写锁
        lock.writeLock().lock();
        try {
            this.cache.clear();
        }finally {
            lock.writeLock().unlock();
        }
    }

    @NonNull
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return null;
    }
}
