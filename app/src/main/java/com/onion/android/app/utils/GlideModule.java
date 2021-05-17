package com.onion.android.app.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * GlideModule-1-实现GlideModule接口
 * 1 - GlideModule接口：为了延迟配置Glide（包括用GlideBuilder设置选项，为Glide注册ModelLoader）
 * 所有的GlideModule实现类必须是public的，并且只拥有一个空的构造器，以便在Glide延迟初始化时，可以通过反射将它们实例化
 * 2 - Glide module 是一个抽象方法，全局改变 Glide 行为的一个方式。
 * 如果你需要访问 GlideBuilder，它要在你要做的地方创建 Glide 实例，这是要做的一种方法。为了定制 Glide，你需要去实现一个 GlideModule 接口的公共类
 */
@com.bumptech.glide.annotation.GlideModule
public class GlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        // 自定义glide配置
        final RequestOptions defaultOptions = new RequestOptions();
        // 除非我们在低RAM设备上，否则请选择高质量的图像
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        defaultOptions.format(manager.isLowRamDevice() ? DecodeFormat.PREFER_RGB_565 : DecodeFormat.PREFER_ARGB_8888);
        // 禁用硬件位图，因为它们在Palette(即在获取图片主色调中)中不能很好地发挥作用
        defaultOptions.disallowHardwareConfig();
        builder.setDefaultRequestOptions(defaultOptions);
        /**
         * 内存占用配置
         * MemoryCache和BitmapPool的默认大小由MemorySizeCalculator类决定
         * MemorySizeCalculator会根据给定屏幕大小可用内存算出合适的缓存大小，这也是推荐的缓存大小，我们可以根据这个推荐大小做出调整
         *     MemorySizeCalculator calculator = new MemorySizeCalculator(context);
         *     int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
         *     int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
         *
         *     int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
         *     int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
         *
         *     builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
         *     builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
         *
         * setMemoryCacheScreens - 设置MemoryCache应该能够容纳的设备屏幕的像素数目
         * setMemoryCache - 缓存方式
         */
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        // 配置默认过度动画
        builder.setDefaultTransitionOptions(Drawable.class, DrawableTransitionOptions.withCrossFade());
    }

    /**
     * 禁止解析Manifest文件
     * 主要针对V3升级到v4的用户，可以提升初始化速度，避免一些潜在错误
     * AppGlideModule 如果Glide应该检查AndroidManifest中的GlideModule则返回true 。
     * 在实现及其依赖项已迁移到Glide的注释处理器之后，实现应返回false
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
