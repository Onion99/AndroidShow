package com.onion.android.algorithm.base.sort

fun main() {
    val list = arrayListOf(1, 7, 2, 6, 3, 4)
    // 遍历集合
    for (index in list.indices) {
        for (childIndex in index + 1 until list.size) {
            if (list[index] > list[childIndex]) {
                val temp = list[index]
                list[index] = list[childIndex]
                list[childIndex] = temp
            }
        }
    }
    print(list)
}