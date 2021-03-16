package com.onion.android.ui.dialog.extend

import android.view.View

/**
 * 防止双击操作，弹出多个Dialog
 * 涉及知识： 并发
 * 重入锁+synchronized+volatile https://blog.csdn.net/qq_34589749/article/details/105861550
 */
internal object Debouncer {

    @Volatile
    private var enabled: Boolean = true

    private val enableAgain = Runnable {
        enabled = true;
    }

    fun canPerform(view: View): Boolean {
        if (enabled) {
            enabled = false
            view.post { enableAgain }
            return true
        }
        return false
    }
}

internal fun <T : View> T.onClickDebounced(click: (view: T) -> Unit) {
    setOnClickListener {
        if (Debouncer.canPerform(it)) {
            @Suppress("UNCHECKED_CAST")
            click(it as T)
        }
    }
}