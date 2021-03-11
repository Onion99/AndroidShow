package com.onion99.android.ui.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.onion99.android.R
import com.onion99.android.ui.dialog.extend.onClickDebounced

class MaterialDialogActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_dialog)
    }

    /**
     * 简化点击事件
     */
    private fun Int.onClickDebounced(click: () -> Unit){
        findViewById<Button>(this).onClickDebounced {
            click()
        }
    }
}