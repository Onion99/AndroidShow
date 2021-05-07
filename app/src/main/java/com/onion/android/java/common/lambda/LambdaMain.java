package com.onion.android.java.common.lambda;

import java.util.function.UnaryOperator;

public class LambdaMain {
    public static void main(String[] args) {
        //UnaryOperator , 对数据进行操作，生成一个与同类型对象
        UnaryOperator<String> stringUnaryOperator =
                (String text) -> "stringUnaryOperator :" + text;
        System.out.println(stringUnaryOperator.apply("first test"));;
        // 组合式，先将此函数应用于其输入，然后将after函数应用于结果
        UnaryOperator<String> integerUnaryOperator =
                (String text) -> text + " in integerUnaryOperator \n";
        System.out.println(stringUnaryOperator.andThen(integerUnaryOperator).apply("first test"));;
    }
}
