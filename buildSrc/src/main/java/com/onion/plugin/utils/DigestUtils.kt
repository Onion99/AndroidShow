package com.onion.plugin.utils

import org.apache.commons.codec.binary.Hex
import java.io.File

/**
 * @Author: leavesCZY
 * @Date: 2021/12/8 15:45
 * @Desc:
 */
object DigestUtils {

    // ------------------------------------------------------------------------
    // 是否生成加密Jar文件名
    // ------------------------------------------------------------------------
    fun generateJarFileName(jarFile: File, encrypt: Boolean = true): String {
        if (!encrypt) return jarFile.name
        return getMd5ByFilePath(jarFile) + "_" + jarFile.name
    }

    // ------------------------------------------------------------------------
    // 是否生成加密Class文件名
    // ------------------------------------------------------------------------
    fun generateClassFileName(classFile: File, encrypt: Boolean = true): String {
        if (!encrypt) return classFile.name
        return getMd5ByFilePath(classFile) + "_" + classFile.name
    }

    // ------------------------------------------------------------------------
    // 将文件绝对路径转为十六进制字符串
    // ------------------------------------------------------------------------
    private fun getMd5ByFilePath(file: File): String {
        return Hex.encodeHexString(file.absolutePath.toByteArray()).substring(0, 8)
    }

}