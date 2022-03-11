package com.onion.plugin.base

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.onion.plugin.utils.ClassUtils
import com.onion.plugin.utils.DigestUtils
import com.onion.plugin.utils.Log
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

abstract class BaseTransform : Transform() {

    // ------------------------------------------------------------------------
    // 实现转换逻辑
    // ------------------------------------------------------------------------
    override fun transform(transformInvocation: TransformInvocation?) {
        //去掉默认处理，super.transform(transformInvocation)
        super.transform(transformInvocation)
        if (transformInvocation == null) return
        // transform 的 输入源
        val inputs = transformInvocation.inputs
        // transform 的 输出源, output 的内容不是任意指定，而是根据input的内容和设置的scopes等有TransformOutputProvider生成。
        val outputProvider = transformInvocation.outputProvider
        // transform 的 上下文
        val context = transformInvocation.context
        // 是否为增量
        val isIncremental = transformInvocation.isIncremental
        // 如果不为增量，则清初原有输出源
        if (isIncremental) {
            outputProvider.deleteAll()
        }
        // 存在两种转换输入，一种是目录，一种是Jar文件(三方库)
        // 有输入进来就必须将其输出，否则会出现类缺失的问题
        inputs.forEach { transformInput ->
            // 处理jar中的文件输入
            transformInput.jarInputs.forEach { jarInput ->
                handleJarInput(jarInput, outputProvider, context, isIncremental)
            }
            // 处理目录中的文件输入
            transformInput.directoryInputs.forEach {

            }
        }
    }

    // ------------------------------------------------------------------------
    // 处理输入源中的Jar文件
    // ------------------------------------------------------------------------
    private fun handleJarInput(
        jarInput: JarInput,
        outputProvider: TransformOutputProvider,
        context: Context,
        isIncremental: Boolean
    ) {
        Log.log("jarInput: " + jarInput.file.absolutePath)
        val destFile = outputProvider.getContentLocation(
            DigestUtils.generateJarFileName(jarInput.file),
            jarInput.contentTypes,
            jarInput.scopes,
            Format.JAR
        )
        // 是否为增量修改编译
        if (isIncremental) {
            when (jarInput.status) {
                Status.NOTCHANGED -> {

                }
                Status.REMOVED -> {
                    Log.log("处理 jar： " + jarInput.file.absoluteFile + " REMOVED")
                    if (destFile.exists()) {
                        FileUtils.forceDelete(destFile)
                    }
                    return
                }
                Status.ADDED, Status.CHANGED -> {
                    Log.log("处理 jar： " + jarInput.file.absoluteFile + " ADDED or CHANGED")
                }
                else -> {
                    return
                }
            }
        }
        // 清除原来就存在的输出文件
        if (destFile.exists()) {
            FileUtils.forceDelete(destFile)
        }
        // 判断 jar 文件是否可以由下层修改处理
        val modifiedJar = if (ClassUtils.isLegalJar(jarInput.file)) {
            modifyJar(jarInput.file, context.temporaryDir)
        } else {
            Log.log("不处理： " + jarInput.file.absoluteFile)
            jarInput.file
        }
        FileUtils.copyFile(modifiedJar, destFile)
    }

    // ------------------------------------------------------------------------
    // 修改Jar文件
    // ------------------------------------------------------------------------
    private fun modifyJar(jarFile: File, temporaryDir: File): File {
        Log.log("处理 jar： " + jarFile.absoluteFile)
        val tempOutputJarFile = File(temporaryDir, DigestUtils.generateJarFileName(jarFile))
        if (tempOutputJarFile.exists()) {
            tempOutputJarFile.delete()
        }
        val jarOutputStream = JarOutputStream(FileOutputStream(tempOutputJarFile))
        val inputJarFile = JarFile(jarFile, false)
        try {
            //  当你有了该JAR文件的一个引用之后，你就可以读取其文件内容中的目录信息了。JarFile的entries方法返回所有entries的枚举集合 (Enumeration)。
            //  通过每一个entry，你可以从它的manifest文件得到它的属性，任何认证信息，以及其他任何该entry的信息，如它的名字或者大小等
            val enumeration = inputJarFile.entries()
            while (enumeration.hasMoreElements()) {
                val jarEntry = enumeration.nextElement()
                val jarEntryName = jarEntry.name
                // 排除* .SF和* .DSA文件 处理
                if (jarEntryName.endsWith(".DSA") || jarEntryName.endsWith(".SF")) {
                    //ignore
                } else {
                    // 为了从JAR文件中真正读取一个指定的文件，你必须到其entry的InputStream。这和JarEntry不一样。
                    // 这是因为JarEntry只是包含该entry的有关信息，但是并不实际包含该entry的内容。这和File和FileInputStream的区别有点儿相似。
                    val inputStream = inputJarFile.getInputStream(jarEntry)
                    try {
                        val sourceClassBytes = IOUtils.toByteArray(inputStream)
                        val modifiedClassBytes =
                            if (jarEntry.isDirectory || !ClassUtils.isLegalClass(jarEntryName)) {
                                null
                            } else {
                                // 提供字节流给外部处理
                                modifyClass(sourceClassBytes)
                            }
                        jarOutputStream.putNextEntry(JarEntry(jarEntryName))
                        jarOutputStream.write(modifiedClassBytes ?: sourceClassBytes)
                        jarOutputStream.closeEntry()
                    } finally {
                        IOUtils.closeQuietly(inputStream)
                    }
                }
            }
        } finally {
            // 强制推送Stream内容（可理解为保存）
            jarOutputStream.flush()
            // 关闭流，回收在内存中的占用
            IOUtils.closeQuietly(jarOutputStream)
            IOUtils.closeQuietly(inputJarFile)
        }
        return tempOutputJarFile
    }


    // ------------------------------------------------------------------------
    // 处理输入源中的目录文件
    // ------------------------------------------------------------------------
    private fun handleDirectoryInput(
        directoryInput: DirectoryInput,
        outputProvider: TransformOutputProvider,
        context: Context,
        isIncremental: Boolean
    ) {
        val dir = directoryInput.file
        val dest = outputProvider.getContentLocation(
            directoryInput.name,
            directoryInput.contentTypes,
            directoryInput.scopes,
            Format.DIRECTORY
        )
        val srcDirPath = dir.absolutePath
        val destDirPath = dest.absolutePath
        val temporaryDir = context.temporaryDir
        // 创建输出文件目录
        FileUtils.forceMkdir(dest)

        if (isIncremental) {
            // 返回更改的文件。这仅在转换处于增量模式时才有效。
            val changedFilesMap = directoryInput.changedFiles
            loop@ for (mutableEntry in changedFilesMap) {
                val classFile = mutableEntry.key
                when (mutableEntry.value) {
                    Status.NOTCHANGED -> {
                        continue@loop
                    }
                    Status.REMOVED -> {
                        Log.log("处理 class： " + classFile.absoluteFile + " REMOVED")
                        //最终文件应该存放的路径
                        val destFilePath = classFile.absolutePath.replace(srcDirPath, destDirPath)
                        val destFile = File(destFilePath)
                        if (destFile.exists()) {
                            destFile.delete()
                        }
                        continue@loop
                    }
                    Status.ADDED, Status.CHANGED -> {
                        Log.log("处理 class： " + classFile.absoluteFile + " ADDED or CHANGED")
                        modifyClassFile(classFile, srcDirPath, destDirPath, temporaryDir)
                    }
                    else -> {
                        continue@loop
                    }
                }
            }
        } else {
            directoryInput.file.walkTopDown().filter { it.isFile }
                .forEach { classFile ->
                    modifyClassFile(classFile, srcDirPath, destDirPath, temporaryDir)
                }
        }
    }

    // ------------------------------------------------------------------------
    // 修改Class文件-1
    // ------------------------------------------------------------------------
    private fun modifyClassFile(
        classFile: File,
        srcDirPath: String,
        destDirPath: String,
        temporaryDir: File
    ) {
        Log.log("处理 class： " + classFile.absoluteFile)
        //最终文件应该存放的路径
        val destFilePath = classFile.absolutePath.replace(srcDirPath, destDirPath)
        val destFile = File(destFilePath)
        if (destFile.exists()) {
            destFile.delete()
        }
        //拿到修改后的临时文件
        val modifyClassFile = if (ClassUtils.isLegalClass(classFile)) {
            modifyClassFile(classFile, temporaryDir)
        } else {
            null
        }
        //将修改结果保存到目标路径
        FileUtils.copyFile(modifyClassFile ?: classFile, destFile)
        modifyClassFile?.delete()
    }

    // ------------------------------------------------------------------------
    // 修改Class文件-2
    // ------------------------------------------------------------------------
    private fun modifyClassFile(classFile: File, temporaryDir: File): File {
        val byteArray = IOUtils.toByteArray(FileInputStream(classFile))
        // 提供字节流给外部处理
        val modifiedByteArray = modifyClass(byteArray)
        val modifiedFile = File(temporaryDir, DigestUtils.generateClassFileName(classFile))
        if (modifiedFile.exists()) {
            modifiedFile.delete()
        }
        modifiedFile.createNewFile()
        val fos = FileOutputStream(modifiedFile)
        fos.write(modifiedByteArray)
        fos.close()
        return modifiedFile
    }


    // ------------------------------------------------------------------------
    // 由下层自行类文件修改
    // ------------------------------------------------------------------------
    protected abstract fun modifyClass(byteArray: ByteArray): ByteArray


    // ------------------------------------------------------------------------
    // 返回转换器的作用域，即处理范围。我们可以只处理 Project 里面的类，保留Scope.PROJECT就好
    // ------------------------------------------------------------------------
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return mutableSetOf(
            QualifiedContent.Scope.PROJECT,
            QualifiedContent.Scope.SUB_PROJECTS,
            QualifiedContent.Scope.EXTERNAL_LIBRARIES
        )
    }

    // ------------------------------------------------------------------------
    // 返回转换器需要消费的数据类型。我们需要处理所有的 class 内容
    // ------------------------------------------------------------------------
    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    // ------------------------------------------------------------------------
    // 是否为增量编译
    // ------------------------------------------------------------------------
    override fun isIncremental(): Boolean {
        // 简单点，先不支持
        return false
    }

    // ------------------------------------------------------------------------
    // 返回当前转换器的名称
    // ------------------------------------------------------------------------
    override fun getName(): String = javaClass.simpleName
}