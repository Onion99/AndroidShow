package com.onion.android.app.plex.ui.bindingAdapter;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onion.android.app.utils.GlideApp;
import com.onion.android.app.utils.UITools;

public class CommonBindingAdapter {

    @BindingAdapter(value = {"imageUrl"})
    public static void imageUrl(AppCompatImageView imageView, String url) {
        GlideApp.with(imageView.getContext()).asBitmap().load(url)
                .fitCenter()
                .placeholder(new ColorDrawable(UITools.getCoolColor()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade())
                .into(imageView);
    }

    @BindingAdapter(value = {"plex_adapter"})
    public static void adapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(AppCompatImageView.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(8);
    }
}
