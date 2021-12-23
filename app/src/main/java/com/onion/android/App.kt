package com.onion.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.onion.android.app.di.injector.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import info.guardianproject.netcipher.client.StrongBuilder
import info.guardianproject.netcipher.client.StrongOkHttpClientBuilder
import info.guardianproject.netcipher.proxy.OrbotHelper
import okhttp3.OkHttpClient
import javax.inject.Inject

// ------------------------------------------------------------------------
// 实现 ViewModelStoreOwner 接口以实现ViewModel
// 实现 HasAndroidInjector 以实现Application注入
// 现在主用Dagger，去掉hilt
// ------------------------------------------------------------------------

// @HiltAndroidApp
class App : Application(), HasAndroidInjector, StrongBuilder.Callback<OkHttpClient> {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    companion object {
        // 这会导致内存泄露,但这无所谓,但都要用到
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        // Dagger-3 Injector器初始化
        AppInjector.init(this)
        // Netcipher-step-1-Creating the OrbotHelper
        // OrbotHelper是一个单例，它管理应用程序和Orbot之间的大量异步通信。
        // 它被设计为在应用程序生命周期的早期进行初始化。一种可能的候选方法是拥有一个定制的应用程序子类，在该子类中重写onCreate（）并设置OrbotHelper。
        OrbotHelper.get(this).init()
        try {
            // 暂时不设置 webkit代理了，会导致网络请求问题
            // WebkitProxy.setProxy(App::class.java.name, this.applicationContext, null, "localhost", 8118)
            // Netcipher-step-2-Creating a Activity Builder
            StrongOkHttpClientBuilder.forMaxSecurity(this)
                .withTorValidation()
                .build(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // ------------------------------------------------------------------------
    //  Application 注入
    // 1. Application 包含 多个Activity，要实现对应的依赖注入绑定，我们必须去实现 HasActivityInjector 接口
    // 2. 如果Activity 包含 fragment,同样也必须在Activity中实现 HasFragmentInjector/HasSupportFragmentInjector 接口 .如果Activity 不包含 fragment,则无需注入任何东西，无需实现响应接口
    // ------------------------------------------------------------------------
    override fun androidInjector(): AndroidInjector<Any> {
        // Dagger-3 Injector器初始化
        AppInjector.init(this)
        return androidInjector
    }

    override fun onConnected(connection: OkHttpClient?) {}

    override fun onConnectionException(e: Exception?) {}

    override fun onTimeout() {}

    override fun onInvalid() {}
}