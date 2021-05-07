package com.onion.android.java.common.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LambdaBasic {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("jhon", "cloud", "feizi");
        // Lambda 之前
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        // 使用Lambda 简化单个方法的接口
        Collections.sort(names,(String a,String b) ->{
            return a.compareTo(b);
        });
        // 单行方法可省略return
        Collections.sort(names,(String a,String b) -> a.compareTo(b));
        // 参数简化
        Collections.sort(names,( a, b) -> a.compareTo(b));
        // 配合Optional使用
        List<String> names2 = null;
        // ofNullable 不为空的情况下，则如果存在值，请使用该值调用指定的使用者，否则不执行任何操作。
        Optional.ofNullable(names2).ifPresent(
                list -> list.sort(Comparator.naturalOrder())
        );
    }
}
