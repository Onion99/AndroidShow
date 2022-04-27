package com.onion.android

import io.reactivex.rxjava3.core.Observable
import org.junit.Test
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_sort() {
        val list = arrayListOf(1, 7, 2, 6, 3, 4)
        // 遍历集合
        for (index in list.indices) {
            for (childIndex in index + 1 until list.size) {
                // 前后交换判断
                if (list[index] > list[childIndex]) {
                    val temp = list[index]
                    list[index] = list[childIndex]
                    list[childIndex] = temp
                }
            }
        }
        println("emit = $list")
    }

    @Test
    fun test() {
        var array = arrayOf<Int>()
        for (i in 0..50) {
            when (i) {
                0 -> {
                    print(0)
                    array[0] = 0
                }

                1 -> {
                    print(1)
                    array[1] = 1
                }
                else -> {
                    array[i] = array[i - 2] + array[i - 1]
                    print(array[i])
                }
            }
        }
    }

    @Test
    fun test2() {
        val array1 = arrayOf(1, 2, 3, 4)
        val array2 = arrayOf(10, 6, 7, 8)
        val array3 = arrayOf(11, 12, 13, 14)
        val array4 = arrayOf(0, 0, 0, 0)
        println(array1)
        println(array2)
        println(array3)
        println(array4)
        for (i in array4.indices) {
            var testArray = arrayOf<Int>(1, 2, 3, 4)
            testArray[i] = array4[i]
            for (index in 0..2) {
                when (index) {
                    0 -> testArray[index] = array3[i]
                    1 -> testArray[index] = array2[i]
                    2 -> testArray[index] = array1[i]
                }
            }
            println(testArray)
        }

    }

    @Test
    fun observer() {
        Observable.create<String> {
            it.onNext("purple")
            // 等待0.51秒,让第一个发送成功
            Thread.sleep(520)
            it.onNext("blue")
            it.onNext("gray green")
            it.onNext("green")
            // 等待0.51秒,让最后一个发送成功
            Thread.sleep(510)
        }.debounce(500, TimeUnit.MILLISECONDS).subscribe {
            println("emit = $it")
        }
    }

    @Test
    fun checkParameterized() {
        val bean = ParameterizedTypeTest()
        val filed = bean::class.java.declaredFields
        filed.forEach {
            println("${it.name}具不具有参数化类型:${it.genericType is ParameterizedType}")
        }
    }


    class ParameterizedTypeTest() {
        val stringList = mutableListOf<String>()
        val singleChar = "A"
    }

}