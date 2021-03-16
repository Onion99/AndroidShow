package com.onion.android.java.funtion;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


/*
* 函数式接口
* Consumer - 代表输入参数的操作，不返回任何结果
* Function - 代表输入参数的操作，返回一个结果
* Operator - 代表同类型操作符的操作，并且返回了操作符同类型的结果
* Predicate - 接受输入参数，返回一个布尔值结果
* */
public class FunctionalInterface {
    public static void main(String[] args) {
        // BiConsumer<T,U>,代表了一个接受两个输入参数的操作，并且不返回任何结果
        BiConsumer<String,String> stringConcat = (s, s2) -> {
            System.out.println("stringConcat = "+s+s2);
        };
        stringConcat.accept("bic"," consumer");
        // BiConsumer<T,U,R>,代表了一个接受两个输入参数的方法，并且返回一个结果
        BiFunction<Integer, Integer, Integer> mathAdd = (integer, integer2) -> integer + integer2;
        System.out.println("mathAdd = " + mathAdd.apply(1,2));

        //...
        // Predicate,接受一个输入参数，返回一个布尔值结果。
        Predicate<Integer> checkNum = integer -> {
            return  integer > 100;
        };
        // Supplier<T> , 无参数，返回一个结果
        Supplier<Integer> supplier = () -> 1;
        System.out.println("supplier = " + supplier.get());
        coutFuntion(integer -> integer <= 10);
    }

    public static void coutFuntion(Predicate<Integer> predicate){
        if(predicate.test(1)){
            System.out.println("predicate = " + predicate);
        }
    }
}
