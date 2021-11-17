package com.onion.android.kotlin.extension

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.exoplayer2.util.Util
import com.google.android.material.appbar.AppBarLayout

///////////////////////////////////////////////////////////////////////////
// 状态栏透明
///////////////////////////////////////////////////////////////////////////
fun Activity.setSystemBarTransparent() {
    val window = window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = Color.TRANSPARENT
}

///////////////////////////////////////////////////////////////////////////
// 隐藏状态栏
///////////////////////////////////////////////////////////////////////////
fun Activity.hideSystemBar() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
    val decorView = window.decorView
    // 设置内容在状态栏之下
    // 设置沉浸式 flag.
    // 状态栏的隐藏和显示不会重新调整大小
    var uiState = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            or View.SYSTEM_UI_FLAG_FULLSCREEN) // hide status bar

    if (Util.SDK_INT > 18) {
        uiState = uiState or (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }
    decorView.systemUiVisibility = uiState

}

fun AppCompatActivity.loadToolbar(toolbar: Toolbar, appBarLayout: AppBarLayout) {
    setSupportActionBar(toolbar)
    toolbar.setTitleTextColor(Color.WHITE)
    toolbar.title = null
    appBarLayout.bringToFront()
    supportActionBar?.setDisplayShowTitleEnabled(false)
}