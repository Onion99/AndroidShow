package com.onion.android.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Window;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onion.android.R;
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

    // Return True if user has an active Network
    public static boolean checkIfHasNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null) {
                    return (networkInfo.isConnected() && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                final Network network = connectivityManager.getActiveNetwork();

                if (network != null) {
                    final NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                    return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }
        }
        return false;
    }

    // Converting Pixels to DPI
    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    // Load Media Cover Path for Media Details (Movie - Serie - Stream - Anime)
    public static void onLoadMediaCover(Context context,ImageView
            imageView,String mediaCoverPath){
        Glide.with(context).asBitmap().load(mediaCoverPath)
                .fitCenter()
                .placeholder(R.color.black_70)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade())
                .skipMemoryCache(true)
                .into(imageView);
    }

}