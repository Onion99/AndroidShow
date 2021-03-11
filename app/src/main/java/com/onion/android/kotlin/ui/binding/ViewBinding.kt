package com.onion99.android.kotlin.ui.binding

import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.card.MaterialCardView
import com.onion99.android.kotlin.simple.whatif.whatIfNotNull
import com.onion99.android.kotlin.simple.whatif.whatIfNotNullOrEmpty

object ViewBinding {

    /**
     * BindingAdapter databinding里通过声明调用方法
     */
    @JvmStatic
    @BindingAdapter("paletteImage", "paletteCard")
    fun bindLoadImage(view: AppCompatImageView ,url: String,paletteCard: MaterialCardView){
        Glide.with(view.context).load(url)
            .listener(
                GlidePalette.with(url).use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack {
                        it.whatIfNotNull {
                            palette ->
                            val rgb = palette.dominantSwatch?.rgb
                            rgb.whatIfNotNull {
                                color ->
                                paletteCard.setCardBackgroundColor(color)
                            }
                        }
                    }.crossfade(true)
            ).into(view)
    }

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text:String?){
        text.whatIfNotNullOrEmpty {
            Toast.makeText(view.context,it,Toast.LENGTH_SHORT).show()
        }
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}