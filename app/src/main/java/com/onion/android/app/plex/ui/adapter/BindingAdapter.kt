package com.onion.android.app.plex.ui.adapter

import androidx.databinding.BindingAdapter
import com.onion.android.app.plex.ui.view.StatusImageButton

object BindingAdapter {
    @BindingAdapter("setCheckedState")
    fun onStateChanged(imageButton: StatusImageButton, checked: Boolean) {
        imageButton.setChecked(checked)
    }
}