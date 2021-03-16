package com.onion.android.java.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;

/*
* Stream（流）
* 1. 一个来自数据源的元素队列并支持聚合操作
* 2. 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算
* 3. 数据源 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
* 4. 聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
* 基础的特征：
* 1. Pipelining: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
* 2. 内部迭代： 以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。 Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。
* API:
* forEach - 迭代流中的每个数据
* map - 映射每个元素到对应的结果
* filter - 通过设置的条件过滤出元素
* limit - 用于获取指定数量的流
* sorted - 用于对流进行排序
* Collectors - 将流转换成集合和聚合元素
* SummaryStatistics - 统计- 最大，最小，和，平均数
* 生成流：
* stream - 为集合创建串行流
* parallelStream - 为集合创建并行流
* */
public class StreamAll {
    public static void main(String[] args) {
        // parallelStream
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 56, 7, 8, 9, 10);
        System.out.println("integerList size = " + integerList.parallelStream().filter(integer -> integer > 9).count());
        // SummaryStatistics
        IntSummaryStatistics intSummaryStatistics = integerList.stream().mapToInt( integer -> integer).summaryStatistics();
        System.out.println("integerList max num = " + intSummaryStatistics.getMax());
        // stream
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);
        //
        List<String> stringList = Arrays.asList("", "aaa", "", "ccc", "");
        System.out.println("stringList size = " + stringList.stream().filter(String::isEmpty).count());

    }
}
