package com.onion.android

import io.reactivex.rxjava3.core.Observable
import org.junit.Test
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
}