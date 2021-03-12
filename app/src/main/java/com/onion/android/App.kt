package com.onion99.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import info.guardianproject.netcipher.proxy.OrbotHelper
import info.guardianproject.netcipher.webkit.WebkitProxy
import java.lang.Exception


@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        /**
         * Netcipher-step-1-Creating the OrbotHelper
         * OrbotHelper是一个单例，它管理应用程序和Orbot之间的大量异步通信。
         * 它被设计为在应用程序生命周期的早期进行初始化。一种可能的候选方法是拥有一个定制的应用程序子类，在该子类中重写onCreate（）并设置OrbotHelper。
         * 可选 - 设置webview代理
         * */
        OrbotHelper.get(this).init()
        try {
            WebkitProxy.setProxy(
                App::class.java.name,
                this.applicationContext,
                null,
                "localhost",
                8118
            )
        } catch (e: Exception) { e.printStackTrace() }
    }
}