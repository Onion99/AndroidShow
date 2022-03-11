package com.onion.plugin.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.onion.plugin.base.BaseTransform
import com.onion.plugin.utils.Log
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode

class TestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("Test Plugin : target = [${target}]")
        val appExtension: AppExtension = target.extensions.getByType()
        appExtension.registerTransform(TestVisitorTransform())
    }
}

class TestClassVisitor(visitor: ClassVisitor) : ClassVisitor(Opcodes.ASM4, visitor) {

}

class TestVisitorTransform : BaseTransform() {

    companion object {
        private var isPrint = true
    }

    private var isChange = false

    override fun modifyClass(byteArray: ByteArray): ByteArray {
        // 解析class文件
        val classReader = ClassReader(byteArray)
        // 将class文件内容写入到ClassWriter中
        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        // 赋予对应的ClassVisitor读写能力
        val classVisitor = TestClassVisitor(classWriter)
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
        // 打印测试
        if (isPrint) {
            isPrint = false
            Log.log(classVisitor.toString())
        }
        // 如果文件对classNode有修改的情况下，需要这样处理
        if (isChange) {
            return classWriter.toByteArray()
        }
        // 返回修改内容
        return byteArray
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return mutableSetOf(
            QualifiedContent.Scope.PROJECT
        )
    }
}

class TestTreeTransform : BaseTransform() {

    companion object {
        private var isPrint = true
    }

    private var isChange = false

    override fun modifyClass(byteArray: ByteArray): ByteArray {
        val classReader = ClassReader(byteArray)
        val classNode = ClassNode()
        // 通过传递 ClassReader 解析对应 ClassFile 生成 ClassNode
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES)
        // 打印测试
        if (isPrint) {
            isPrint = false
            Log.log(classNode.toString())
            for (methodNode in classNode.methods) {
                Log.log("${classNode.name} ------>>> $methodNode")
            }
        }
        // 如果文件对classNode有修改的情况下，需要这样处理
        if (isChange) {
            val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
            classNode.accept(classWriter)
            return classWriter.toByteArray()
        }
        // 返回修改内容
        return byteArray
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return mutableSetOf(
            QualifiedContent.Scope.PROJECT
        )
    }
}