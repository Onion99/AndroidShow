package com.onion.android;

import org.junit.Test;

import java.util.List;

public class Hencoder_Generic {

    @Test
    public void GenericTest() {
        Generic<String> sample = new Generic<>("Gen String");
        System.out.println(sample.getKey());
        // 如果一开始传入泛型实参,则后面类型可为任意类型
        Generic sample2 = new Generic<>(999);
        // 因为前面不指定泛型实参.这里产生类型擦除
        sample2.setKey("asc");
        System.out.println(sample2.getKey());
        // 不能对确切的泛型类型使用instanceof操作。如下面的操作是非法的，编译时会出错
        //System.out.println(sample2 instanceof  Generic<Integer>);
    }

    // 泛型接口
    @Test
    public void GenericInterfaceTest() {
        GenericInterface<String> stringGenericInterface = new GenericInterface<String>() {
            @Override
            public String getValue() {
                return "GenericInterface";
            }
        };
        System.out.println(stringGenericInterface.getValue());
    }

    // 泛型通配符
    @Test
    public void GenericPairTest() {
        Generic<String> sample = new Generic<>("Gen String");
        showGenericValue(sample);
        Generic<Integer> sample2 = new Generic<>(123);
        showGenericValue(sample2);
    }

    public void showGenericValue(Generic<?> key) {
        System.out.println("key = " + key.getKey());
    }

    @Test
    public void GenericMethod() {
        Pair<String, String> stringPair1 = new Pair<>("method", "GenericMethod");
        Pair<String, String> stringPair2 = new Pair<>("method", "GenericMethod");
        System.out.println(this.<String, String>compare(stringPair1, stringPair2));
        setPairValue(stringPair2, "has change");
        System.out.println(stringPair2.getValue());
    }

    public <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
        return p1.getKey() == p2.getKey() && p1.getValue() == p2.getValue();
    }

    public <K, V> Pair<K, V> setPairValue(Pair<K, V> p, V value) {
        p.setValue(value);
        return p;
    }

    public <T> void copy(List<? super T> output, List<? extends T> input) {
        for (int i = 0; i < input.size(); i++) {
            output.set(i, input.get(i));
        }
    }

    interface GenericInterface<T> {
        T getValue();
    }

    // 此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
    // 在实例化泛型类时，必须指定T的具体类型
    public class Generic<T> {
        // key这个成员变量的类型为T,T的类型由外部指定
        private T key;

        // 泛型构造方法形参key的类型也为T，T的类型由外部指定
        public Generic(T key) {
            this.key = key;
        }

        // 泛型方法getKey的返回值类型为T，T的类型由外部指定
        public T getKey() {
            return key;
        }

        public void setKey(T key) {
            this.key = key;
        }
    }

    class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}

