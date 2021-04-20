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
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.exoplayer2.util.Util;
import com.onion.android.R;
// 导入包域静态变量
import java.nio.charset.StandardCharsets;

import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;



public class Tools {
    public Tools() {
        throw new IllegalStateException("Utility class");
    }

    private static final Handler handler = new Handler(Looper.getMainLooper());
    // Hadnler 子线程事件
    public static void postDelayed(Runnable runnable,int delayTime){
        handler.postDelayed(runnable, delayTime);
    }
    // 如果参数为null的话，会将所有的Callbacks和Messages全部清除掉,避免内存泄露
    public static void clearHandler(){
        handler.removeCallbacksAndMessages(null);
    }

    // 状态栏 - 透明
    public static void setSystemBarTransparent(Activity activity){
        if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP){
            Window window = activity.getWindow();
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 隐藏系统状态栏
     * @param activity 当前上下文
     * @param immediate 是否沉浸式
     */
    public static void hideSystemBar(@NonNull final Activity activity, final boolean immediate){
        hideSystemBar(activity,immediate,2000);
    }

    public static void hideSystemBar(@NonNull final Activity activity, final boolean immediate,
        final int delayMs){
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        View decorView = activity.getWindow().getDecorView();
        // 设置沉浸式FLAG
        // 设置状态栏覆盖到视图
        // 当状态栏显示或隐藏的时候不要调整
        int uiState = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // 隐藏导航栏
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // 隐藏状态
        if(Util.SDK_INT > 18){
            // |= 意指 uiState = uiState | View.SYSTEM_UI_FLAG_LAY
            uiState |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
        }else{
            final Handler handler = new Handler(Looper.getMainLooper());
            decorView.setOnSystemUiVisibilityChangeListener(
                    visibility -> {
                        if(visibility == View.VISIBLE){
                            Runnable runnable = () -> hideSystemBar(activity,false);
                            if(immediate){
                                handler.post(runnable);
                            }else{
                                handler.postDelayed(runnable, delayMs);
                            }
                        }
                    }
            );
        }
        decorView.setSystemUiVisibility(uiState);
    }

    /**
     * 加载网络图片
     */
    public static void loadHttpImg(Context context, ImageView imageView,String url){
        Glide.with(context).asBitmap().load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade())
                .skipMemoryCache(true)
                .into(imageView);
    }

    // 检查网络状态
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

    //  dp 到 px 的转换
    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    // 加载视频封面(Movie - Serie - Stream - Anime)
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

    // 解析编码 (BASE64)
    public static final String BYTE_TO_MB = "aHR0cHM6Ly9hcGkueW9iZGV2LmxpdmUvaW5mby9hcGkv";
    public static  final  String PLAYER = "aHR0cHM6Ly9hcGkuZW52YXRvLmNvbS92My8=";
    public static String getPlayer(){
        byte[] valueDecoded;
        valueDecoded = Base64.decode(PLAYER.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }


}