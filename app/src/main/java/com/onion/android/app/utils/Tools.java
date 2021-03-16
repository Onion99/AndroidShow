package com.onion.android.app.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
// 导入包域静态变量
import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;



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

    public static void setSystemBarTransparent(Activity activity){
        if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP){
            Window window = activity.getWindow();
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void loadHttpImg(Context context, ImageView imageView,String url){
        Glide.with(context).asBitmap().load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade())
                .skipMemoryCache(true)
                .into(imageView);
    }
}