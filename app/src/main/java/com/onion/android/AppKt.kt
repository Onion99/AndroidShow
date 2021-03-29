package com.onion.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// 旧版 Hilt 注入 Application ，现在Application的App主要用 Dagger
@HiltAndroidApp
class AppKt : Application()