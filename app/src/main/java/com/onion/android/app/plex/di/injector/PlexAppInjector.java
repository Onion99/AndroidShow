package com.onion.android.app.plex.di.injector;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.onion.android.App;
import com.onion.android.app.plex.di.component.DaggerPlexAppComponent;

import dagger.android.AndroidInjection;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

/**
 * Dagger-1.5 init 第一步必须执行，不然会报错
 * 实现自动依赖注入
 */
public class PlexAppInjector {
    public PlexAppInjector() {
    }

    public static void init(App app) {
        DaggerPlexAppComponent.builder()
                .application(app)
                .build()
                .inject(app);
        // 监听Activity 创建回调,实现Activity 和 Fragment注入
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                handActivityInjectFragment(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    public static void handActivityInjectFragment(Activity activity){
        // 通過是否实现 HasAndroidInjector 判断 Activity是否需要注入
        if(activity instanceof HasAndroidInjector){
            AndroidInjection.inject(activity);
        }
        if(activity instanceof FragmentActivity ){
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                        @Override
                        public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                            super.onFragmentCreated(fm, f, savedInstanceState);
                            // 通过是否实现 Injectable 判断 Fragment是否需要依赖注入
                            if(f instanceof Injectable ){
                                // 旧API 用 AndroidSupportInjection ，新API 用 AndroidInjection
                                AndroidSupportInjection.inject(f);
                            }
                        }
                    },true);
        }
    }
}
