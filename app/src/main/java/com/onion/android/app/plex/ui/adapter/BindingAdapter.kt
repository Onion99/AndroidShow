package com.onion.android.app.plex.ui.adapter

import androidx.databinding.BindingAdapter
import com.onion.android.app.plex.ui.view.SeasonsImageButton

object BindingAdapter {
    @BindingAdapter("setCheckedState")
    fun onStateChanged(imageButton: SeasonsImageButton, checked: Boolean) {
        imageButton.setChecked(checked)
    }
}