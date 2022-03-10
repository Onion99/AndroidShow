package com.onion.android.app.utils;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;
import static com.onion.android.app.plex.ui.MediaDetailsActivityKt.ARG_MOVIE;
import static com.onion.android.app.plex.ui.player.BasePlayerActivityKt.KEY_MEDIA;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.MimeTypes;
import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.model.media.MediaModel;
import com.onion.android.app.plex.data.model.stream.MediaStream;
import com.onion.android.app.plex.ui.player.MainPlayerActivity;

import java.nio.charset.StandardCharsets;
import java.util.Formatter;
import java.util.Locale;
import java.util.UUID;



public class Tools {

    public Tools() {
        throw new IllegalStateException("Utility class");
    }


    private static final Handler handler = new Handler(Looper.getMainLooper());
    public static final String USER_AGENT = "User-Agent";


    // ------------------------------------------------------------------------
    // 使Runnable添加到消息队列中，在指定的时间后运行
    // ------------------------------------------------------------------------
    public static void postDelayed(Runnable runnable, int delayTime) {
        handler.postDelayed(runnable, delayTime);
    }

    // ------------------------------------------------------------------------
    // 如果参数为null的话，会将所有的Callbacks和Messages全部清除掉,避免内存泄露
    // ------------------------------------------------------------------------
    public static void clearHandler() {
        handler.removeCallbacksAndMessages(null);
    }

    // ------------------------------------------------------------------------
    // 加载网络图片
    // ------------------------------------------------------------------------
    public static void loadHttpImg(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext()).asBitmap().load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade())
                .skipMemoryCache(true)
                .into(imageView);
    }

    // ------------------------------------------------------------------------
    // 检查网络状态
    // ------------------------------------------------------------------------
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

    // ------------------------------------------------------------------------
    // dp 到 px 的转换
    // ------------------------------------------------------------------------
    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    // ------------------------------------------------------------------------
    // 解析编码 (BASE64)
    // ------------------------------------------------------------------------
    public static final String PLAYER = "aHR0cHM6Ly9hcGkuZW52YXRvLmNvbS92My8=";

    public static String getPlayer() {
        byte[] valueDecoded;
        valueDecoded = Base64.decode(PLAYER.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }


    private static final StringBuilder formatBuilder = new StringBuilder();
    private static final Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    private static String uniqueID = null;

    // ------------------------------------------------------------------------
    // 将时间转为毫秒
    // ------------------------------------------------------------------------
    public static long progressToMilli(long playerDurationMs, SeekBar seekBar) {
        long duration = playerDurationMs < 1 ? C.TIME_UNSET : playerDurationMs;
        return duration == C.TIME_UNSET ? 0 : ((duration * seekBar.getProgress()) / seekBar.getMax());
    }

    // ------------------------------------------------------------------------
    // 获取时间进展
    // ------------------------------------------------------------------------
    public static String getProgressTime(long timeMs, boolean remaining) {
        if (timeMs == C.TIME_UNSET) timeMs = 0;
        long totalSeconds = (timeMs + 500) / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        formatBuilder.setLength(0);
        String formatHours = "%d:%02d:%02d";
        String formatMinutes = "%02d:%02d";
        String time = hours > 0 ? formatter.format(formatHours, hours, minutes, seconds).toString() : formatter.format(formatMinutes, minutes, seconds).toString();
        return remaining && timeMs != 0 ? "-" + time : time;
    }

    // ------------------------------------------------------------------------
    // 设备id
    // ------------------------------------------------------------------------
    public static synchronized String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.apply();
            }
        }
        return uniqueID;
    }

    // ------------------------------------------------------------------------
    // 获取字幕类型
    // ------------------------------------------------------------------------
    public static String getSubtitleMime(Uri uri) {
        final String path = uri.getPath();
        if (path.endsWith(".ssa") || path.endsWith(".ass")) {
            return MimeTypes.TEXT_SSA;
        } else if (path.endsWith(".vtt")) {
            return MimeTypes.TEXT_VTT;
        } else if (path.endsWith(".ttml") || path.endsWith(".xml") || path.endsWith(".dfxp")) {
            return MimeTypes.APPLICATION_TTML;
        } else {
            return MimeTypes.APPLICATION_SUBRIP;
        }
    }

    // ------------------------------------------------------------------------
    // 使用自带的播放器进行播放
    // ------------------------------------------------------------------------
    public static void useMainPlay(Context context, Media movieDetail, String url, String server, String mediaGenre, MediaStream mediaStream) {
        if (!movieDetail.getSubstitles().isEmpty() && movieDetail.getSubstitles() != null && movieDetail.getSubstitles().get(0).getZip() != 1) {
            String currentSubsTitle = movieDetail.getSubstitles().get(0).getLink();
            String currentSubsTitleType = movieDetail.getSubstitles().get(0).getType();
            String currentSubsTitleLang = movieDetail.getSubstitles().get(0).getLang();
            Intent intent = new Intent(context, MainPlayerActivity.class);
            intent.putExtra(KEY_MEDIA, MediaModel.media(movieDetail.getId(),
                    currentSubsTitleLang, mediaStream.getServer(), "0", movieDetail.getTitle(),
                    url, movieDetail.getBackdropPath(), currentSubsTitle, null
                    , null, null, null, null,
                    null, null, null,
                    null, mediaStream.getHls(), currentSubsTitleType, movieDetail.getImdbExternalId()
                    , movieDetail.getPosterPath(), movieDetail.getHasrecap(), movieDetail.getSkiprecapStartIn(), mediaGenre, null, movieDetail.getVoteAverage()));
            intent.putExtra(ARG_MOVIE, movieDetail);
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(context, MainPlayerActivity.class);
            intent.putExtra(KEY_MEDIA, MediaModel.media(movieDetail.getId(),
                    null, server, "0", movieDetail.getTitle(),
                    url, movieDetail.getBackdropPath(), null, null
                    , null, null, null, null,
                    null, null, null,
                    null, mediaStream.getHls(), movieDetail.getSubstype(), movieDetail.getImdbExternalId()
                    , movieDetail.getPosterPath(), movieDetail.getHasrecap(), movieDetail.getSkiprecapStartIn(), mediaGenre, null, movieDetail.getVoteAverage()));
            intent.putExtra(ARG_MOVIE, movieDetail);
            context.startActivity(intent);
        }
    }
}