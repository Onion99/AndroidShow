package com.onion.android

import org.junit.Test

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
    }
}