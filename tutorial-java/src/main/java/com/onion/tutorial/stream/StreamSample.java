package com.onion.tutorial.stream;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamSample {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("c");
        stringList.add("a");
        stringList.add("x");
        stringList.add("z");
        // 过滤
        stringList.stream().filter( s -> !s.startsWith("a")).forEach(System.out::print); // cxz
        System.out.println();
        // 排序
        stringList.stream().sorted().forEach(System.out::print); // acxz
        System.out.println();
        // 映射
        stringList.stream().map(String::toUpperCase).sorted(Comparator.reverseOrder()).forEach(System.out::print); // ZXCA
        // 匹配
        System.out.println();
        System.out.println(stringList.stream().allMatch( s -> s.startsWith("x"))); // false
        System.out.println(stringList.stream().anyMatch( s -> s.startsWith("x"))); // true
        System.out.println(stringList.stream().noneMatch( s -> s.startsWith("b"))); // true
        // 计算
        System.out.println(stringList.stream().filter(s -> !s.startsWith("a")).count());
        // 累计
        Optional<String> reduced = stringList.stream().sorted().reduce(((s1, s2) -> s1 + "*" + s2));
        reduced.ifPresent(System.out::println);
    }

    static class CollectorSample{
        static class Person {
            String name;
            int age;
            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
        }
        public static void main(String[] args) {
            List<Person> persons = Arrays.asList(
                            new Person("Max", 18),
                            new Person("Peter", 23),
                            new Person("Pamela", 23),
                            new Person("David", 12));
            Collector<Person, StringJoiner, String> personNameCollector =
                    Collector.of(
                            () -> new StringJoiner(" | "),          // supplier
                            (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
                            StringJoiner::merge,               // combiner
                            StringJoiner::toString);                // finisher

            String names = persons
                    .stream()
                    .collect(personNameCollector);

            System.out.println(names); // MAX | PETER | PAMELA | DAVID
        }
    }

    static class IntStreamSample{
        public static void main(String[] args) {
            // 范围计算
            IntStream.range(0, 10)
                    .forEach(i -> {
                        if (i % 2 == 1) System.out.print(i);
                    }); // 13579
            System.out.println();
            // 范围累计计算
            OptionalInt reducedSum = IntStream.range(0, 10).reduce(Integer::sum);
            System.out.println(reducedSum.getAsInt()); //45
            // 数据类型转换
            Stream.of(new BigDecimal("1.2"), new BigDecimal("3.7"))
                    .mapToDouble(BigDecimal::doubleValue)
                    .average()
                    .ifPresent(System.out::println); // 2.45
            // 平均
            IntStream.builder()
                    .add(1)
                    .add(3)
                    .add(5)
                    .add(7)
                    .add(11)
                    .build()
                    .average()
                    .ifPresent(System.out::println); // 5.4

            int[] ints = {1, 3, 5, 7, 11};
            Arrays.stream(ints).average().ifPresent(System.out::println); // 5.4

            // 构造一个安全随机数生成器(RNG)，实现默认随机数算法。SecureRandom实例以指定的种子字节作为种子。 此构造函数遍历已注册的安全提供程序列表
            SecureRandom secureRandom = new SecureRandom(new byte[]{1, 3, 3, 7});
            int[] randoms = IntStream.generate(secureRandom::nextInt)
                    .filter(n -> n > 0)
                    .limit(10)
                    .toArray();
            System.out.println(Arrays.toString(randoms)); // [1011328993, 183254186, 1684794770, 536839172, 1049685462, 2049219084, 1748733484, 1788165456, 950349964, 619621986]

            int[] intList = IntStream.iterate(1, n -> n * 2)
                    .limit(11)
                    .toArray();
            System.out.println(Arrays.toString(intList)); // [1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024]
        }
    }

    static class OptionalSample{

        static class Outer {
            Nested nested = new Nested();
            public Nested getNested() { return nested; }
        }

        static class Nested {
            Inner inner = new Inner();
            public Inner getInner() { return null; }
        }

        static class Inner {
            String name = this.getClass().getSimpleName();
            public String getName() { return name; }
        }

        public static void main(String[] args) {
            Optional<String> optional = Optional.of("lol");
            optional.isPresent();           // true
            optional.get();                 // lol
            optional.orElse("lolm");    // lol
            optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // ";"
            // 如果有值，则执行提供的映射函数
            Optional.of(new Outer())
                    .map(Outer::getNested)
                    .map(Nested::getInner)
                    .map(Inner::getName)
                    .ifPresent(System.out::println); // 执行不到
            // 如果有值，则执行提供的Optional映射函数
            Optional.of(new Outer())
                    .flatMap(o -> Optional.ofNullable(o.nested))
                    .flatMap(n -> Optional.ofNullable(n.getInner()))
                    .flatMap(i -> Optional.ofNullable(i.name))
                    .ifPresent(System.out::println); // // 执行不到
            Optional.of(new Outer())
                    .flatMap(o -> Optional.ofNullable(o.nested))
                    .flatMap(n -> Optional.ofNullable(n.inner))
                    .flatMap(i -> Optional.ofNullable(i.name))
                    .ifPresent(System.out::println); // Inner
        }
    }
}

