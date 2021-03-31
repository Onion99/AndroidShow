package com.onion.android

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.onion.android.app.plex.di.injector.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.hilt.android.HiltAndroidApp
import info.guardianproject.netcipher.client.StrongBuilder
import info.guardianproject.netcipher.client.StrongOkHttpClientBuilder
import info.guardianproject.netcipher.proxy.OrbotHelper
import info.guardianproject.netcipher.webkit.WebkitProxy
import okhttp3.OkHttpClient
import java.lang.Exception
import javax.inject.Inject

/**
 * 实现 ViewModelStoreOwner 接口以实现ViewModel
 * 实现 HasAndroidInjector 以实现Application注入
 * */
// Todo 这里加上 @HiltAndroidApp 的话 Hilt 和 Dagger 会发生冲突，导致某些注入需求找不到 ,因而现在先注释掉
// @HiltAndroidApp
class App : Application(),ViewModelStoreOwner,HasAndroidInjector,StrongBuilder.Callback<OkHttpClient>{

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private lateinit var mAppViewModelStore: ViewModelStore

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        AppInjector.init(this)
        /**
         * Netcipher-step-1-Creating the OrbotHelper
         * OrbotHelper是一个单例，它管理应用程序和Orbot之间的大量异步通信。
         * 它被设计为在应用程序生命周期的早期进行初始化。一种可能的候选方法是拥有一个定制的应用程序子类，在该子类中重写onCreate（）并设置OrbotHelper。
         * 可选 - 设置webview代理
         * */
        OrbotHelper.get(this).init()
        try {
            // Todo 暂时不设置 webkit代理了，会导致网络请求问题
/*            WebkitProxy.setProxy(
                App::class.java.name,
                this.applicationContext,
                null,
                "localhost",
                8118
            )*/
            // Netcipher-step-2-Creating a Activity Builder
            StrongOkHttpClientBuilder.forMaxSecurity(this)
                .withTorValidation()
                .build(this)
        } catch (e: Exception) { e.printStackTrace() }
        // 实例化mAppViewModelStore
        mAppViewModelStore = ViewModelStore()
    }

    companion object {
        @JvmStatic
        lateinit var context:Context
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    override fun androidInjector(): AndroidInjector<Any> {
        AppInjector.init(this)
        return androidInjector
    }

    override fun onConnected(connection: OkHttpClient?) {}

    override fun onConnectionException(e: Exception?) {}

    override fun onTimeout() {}

    override fun onInvalid() {}
}