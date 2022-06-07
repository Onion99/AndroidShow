package com.dong.onionui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dong.onionui.material.MaterialShowActivity
import com.dong.onionui.telegram.LaunchActivity

class MainShowActivity : AppCompatActivity() {
    // ----默认布局----
    private var mTag = "activity_main_show"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mTag)
    }

    // ------------------------------------------------------------------------
    // 根据View的Tag重新设置布局
    // ------------------------------------------------------------------------
    fun show(view: View) {
        mTag = view.tag.toString()
        setContentView(mTag)
    }

    // ------------------------------------------------------------------------
    // 根据Tag获取资源文件
    // ------------------------------------------------------------------------
    private fun setContentView(tag: String) {
        val layoutId = resources.getIdentifier(tag, "layout", packageName)
        setContentView(layoutId)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (mTag == "activity_main_show") {
            super.onBackPressed()
        } else setContentView("activity_main_show")
    }

    fun showTelegram(view: View) {
        startActivity(Intent(this,LaunchActivity::class.java))
    }

    fun showMaterialBuildShow(view: View) {
        startActivity(Intent(this,MaterialShowActivity::class.java))
    }

}