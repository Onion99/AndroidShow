package com.onion.android.app.pokemon.di.injector

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.onion.android.App
import com.onion.android.app.plex.di.injector.Injectable
import com.onion.android.app.pokemon.di.component.DaggerPokedexAppComponent
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

class PokedexAppInjector() {
    companion object {

        @JvmStatic
        fun init(app: App) {
            DaggerPokedexAppComponent.builder().application(app)
                .build()
                .inject(app)
            // 监听Activity 创建回调,实现Activity 和 Fragment注入
            // 监听Activity 创建回调,实现Activity 和 Fragment注入
            app.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handActivityInjectFragment(activity)
                }

                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {}
                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
                override fun onActivityDestroyed(activity: Activity) {}
            })
        }

        @JvmStatic
        fun handActivityInjectFragment(activity: Activity) {
            // 通過是否实现 HasAndroidInjector 判断 Activity是否需要注入
            if (activity is HasAndroidInjector) {
                AndroidInjection.inject(activity)
            }
            if (activity is FragmentActivity) {
                activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(object :
                        FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            super.onFragmentCreated(fm, f, savedInstanceState)
                            // 通过是否实现 Injectable 判断 Fragment是否需要依赖注入
                            if (f is Injectable) {
                                // 旧API 用 AndroidSupportInjection ，新API 用 AndroidInjection
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true)
            }
        }
    }
}