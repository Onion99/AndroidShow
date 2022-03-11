package com.onion.plugin.utils

import java.io.File

/**
 * @Author: leavesCZY
 * @Date: 2021/12/4 17:40
 * @Desc:
 */
object ClassUtils {

    // ------------------------------------------------------------------------
    // 判断是否Android生成的类，也就是说不是project或者第三库的类
    // ------------------------------------------------------------------------
    private fun isAndroidGeneratedClass(className: String): Boolean {
        return className.contains("R$") ||
                className.contains("R2$") ||
                className.contains("R.class") ||
                className.contains("R2.class") ||
                className == "BuildConfig.class"
    }

    // ------------------------------------------------------------------------
    // 是否为可供修改的传统jar文件
    // ------------------------------------------------------------------------
    fun isLegalJar(file: File): Boolean {
        return file.isFile &&
                file.length() > 0L &&
                file.name != "R.jar" &&
                file.name.endsWith(".jar")
    }

    // ------------------------------------------------------------------------
    // 是否为可供修改的传统Class文件
    // ------------------------------------------------------------------------
    fun isLegalClass(file: File): Boolean {
        return file.isFile && isLegalClass(file.name)
    }


    fun isLegalClass(fileName: String): Boolean {
        return fileName.endsWith(".class") && !isAndroidGeneratedClass(fileName)
    }

}