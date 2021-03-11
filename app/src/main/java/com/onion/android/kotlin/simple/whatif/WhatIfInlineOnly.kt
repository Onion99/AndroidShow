package com.onion99.android.kotlin.simple.whatif

/**
 * @Target 表示该注解可以用于什么地方
 * @DslMarker 如果一个注解类使用@DslMarker注解标注，那么该注解类称为 DSL 标记。
 * 该注解允许限制来自DSL上下文中的外部外部作用域的接受者的使用
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
    AnnotationTarget.LOCAL_VARIABLE
)
@DslMarker
@Retention(AnnotationRetention.BINARY)
public annotation class WhatIfInlineOnly