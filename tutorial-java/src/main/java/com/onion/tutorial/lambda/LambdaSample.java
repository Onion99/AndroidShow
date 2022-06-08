package com.onion.tutorial.lambda;

import android.app.Person;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaSample {
    public static void main(String[] args) {
        // Predicates,执行条件
        Predicate<String> predicate = (s) -> s.length() > 0;
        // 正确逻辑
        predicate.test("foo");              // true
        // 错误逻辑
        predicate.negate().test("foo");     // false
        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
        System.out.println(isEmpty.test("")); // true

        // Functions
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);

        backToString.apply("123"); // 123

        // Suppliers
        Supplier<Object> personSupplier = Object::new;
        personSupplier.get();   // new Object

        // Runnable
        Runnable runnable = () -> System.out.println(UUID.randomUUID());
        runnable.run();

        // Callables
        Callable<UUID> callable = UUID::randomUUID;
        try {
            callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Consumers
        Consumer<Object> greeter = System.out::println;
        greeter.accept(new Object());


        // BiConsumer Example
        BiConsumer<String,Integer> printKeyAndValue
                = (key,value) -> System.out.println(key+"-"+value);

        printKeyAndValue.accept("One",1); // One-1
        printKeyAndValue.accept("Two",2); // Two-2
    }
}
