package com.onion.android.app.base.adpte

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BindingAdapter<T, VH : RecyclerView.ViewHolder> constructor(val callback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(callback) {

}