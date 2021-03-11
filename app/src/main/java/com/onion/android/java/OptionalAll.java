package com.onion.android.java;

import java.util.Arrays;
import java.util.Optional;

/*
* Optional 类是一个可以为null的容器对象,Optional类的引入很好的解决空指针异常
* filter - 如果值存在，并且这个值匹配给定的 predicate，返回一个Optional用以描述这个值，否则返回一个空的Optional。
* flatMap - 如果值存在，返回基于Optional包含的映射方法的值，否则返回一个空的Optional
* map - 如果返回值不为 null，则创建包含映射返回值的Optional作为map方法返回值
* get - 如果在这个Optional中包含这个值，返回值，否则抛出异常：NoSuchElementException
* ifPresent - 如果值存在则使用该值调用 consumer , 否则不做任何事情
* of - 返回一个指定非null值的Optional
* ofNullable - 如果为非空，返回 Optional 描述的指定值，否则返回空的 Optional
* orElse - 如果存在该值，返回值， 否则返回另一个值
* orElseGet - 如果存在该值，返回值，否则触发 Supplier，并返回 Supplier 调用的结果
* */
public class OptionalAll {
    public static void main(String[] args) {
        String value = "Optional";
        Optional<String> optionalS = Optional.of(value);
        // 判断值是否存在
        System.out.println(optionalS.isPresent());
        // 获取值
        System.out.println("optionalS.get() = " + optionalS.get());
        optionalS.ifPresent( s -> {
            System.out.println("optionalS.get() = " + s);
        });

        String nullValue = null;
        // 具有Null值对象的设置
        Optional<String> optionalS1 = Optional.ofNullable(nullValue);
        // 如果为Null的情况赋予其他值
        System.out.println("optionalS1.get() = " + optionalS1.orElse("Another Optional"));
        // 在值是否存在的情况下
        optionalS1.ifPresent( s -> {
            System.out.println("optionalS1 = " + s);
        });
    }
}
