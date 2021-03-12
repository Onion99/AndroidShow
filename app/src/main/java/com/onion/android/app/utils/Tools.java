package com.onion.android.app.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;

public class Tools {
    public Tools() {
        throw new IllegalStateException("Utility class");
    }

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static void postDelayed(Runnable runnable,int delayTime){
        handler.postDelayed(runnable, delayTime);
    }

    // 如果参数为null的话，会将所有的Callbacks和Messages全部清除掉,避免内存泄露
    public static void clearHandler(){
        handler.removeCallbacksAndMessages(null);
    }

    public static void loadHttpImg(Context context, ImageView imageView,String url){
        Glide.with(context).asBitmap().load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(BitmapTransitionOptions.withCrossFade())
                .skipMemoryCache(true)
                .into(imageView);
    }
}