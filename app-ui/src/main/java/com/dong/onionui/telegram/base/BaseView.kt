package com.dong.onionui.telegram.base

import android.content.Context
import android.os.Bundle
import android.view.View

abstract class BaseView(arg: Bundle = Bundle()) {

    protected var arguments:Bundle = arg
    lateinit var currentView: View

    abstract fun  createView(context: Context):View

    fun isInitView():Boolean = ::currentView.isInitialized
}