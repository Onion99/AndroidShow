package com.onion.android.kotlin.extension

import android.animation.AnimatorListenerAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.onion.android.R

fun ImageView.loadUrl(path: String) {
    Glide.with(context).asBitmap().load(path)
        .fitCenter()
        .placeholder(R.color.plex_background)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transition(BitmapTransitionOptions.withCrossFade())
        .skipMemoryCache(true)
        .into(this)
}

fun View.fadeOut(animatorListenerAdapter: AnimatorListenerAdapter? = null) {
    alpha = 1.0f
    animate().setDuration(500)
        .setListener(animatorListenerAdapter)
        .alpha(0.0f)
}