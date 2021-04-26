package com.onion.android.app.base

/**
 * 注解的附加属性可以通过用元注解标注注解类来指定：
 * @Target 指定可以用该注解标注的元素的可能的类型（类、函数、属性、表达式等）；
 * @Retention 指定该注解是否存储在编译后的 class 文件中，以及它在运行时能否通过反射可见 （默认都是 true）；
 *  AnnotationRetention.BINARY - 注存储在二进制输出中，但对于反射不可见
 * @Repeatable 允许在单个元素上多次使用相同的该注解；
 * @MustBeDocumented 指定该注解是公有 API 的一部分，并且应该包含在生成的 API 文档中显示的类或方法的签名中。
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
)
@DslMarker
@Retention(AnnotationRetention.BINARY)
internal annotation class BindingOnly
