package com.onion.android.java.common.funtion;

import java.util.ArrayList;
import java.util.function.Supplier;

/*
* 方法引用
* 1- 方法引用通过方法的名字来指向一个方法
* 2- 使用一对冒号 ::
* */
public class MethodReference {
    static class Car{
        public static Car create(final Supplier<Car> supplier){
            return supplier.get();
        }

        public static void collide(final  Car car){
            System.out.println("car = " + car);
        }

        public void repair(){
            System.out.println("repair car");
        }
    }

    public static void main(String[] args) {
        // 构造器引用
        final Car car = Car.create(Car::new);
        // 静态方法引用
        ArrayList<Car> carArrayList = new ArrayList<>(1);
        carArrayList.add(car);
        carArrayList.forEach(Car::collide);
        // 特定类的任意对象的方法引用
        carArrayList.forEach(Car::repair);
        // 特定对象的方法引用
        Supplier<User> userCreate = User::new;
        carArrayList.forEach(userCreate.get()::callName);
    }
}
class User{
    public void callName(){
        System.out.println("callName");
    }

    public void callName(MethodReference.Car car) {
        System.out.println("mine callName:"+car);
    }
}