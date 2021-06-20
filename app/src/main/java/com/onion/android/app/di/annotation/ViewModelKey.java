package com.onion.android.app.di.annotation;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

// 这个主要用于解决一个奇怪的问题
// https://stackoverflow.com/questions/55669810/dagger-missingbinding-java-util-mapjava-lang-class-extends-viewmodel-provide
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}

