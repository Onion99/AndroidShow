package com.onion.android.app.di.module;

import com.onion.android.app.plex.ui.MediaDetailsActivity;
import com.onion.android.app.plex.ui.PlexMainActivity;
import com.onion.android.app.plex.ui.PlexSplashActivity;
import com.onion.android.app.plex.ui.player.MainPlayerActivity;
import com.onion.android.customview.first_location.TestViewActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Dagger-2 声明相应提供对象的Module
 *
 * @Module - 用于提供对象
 * 用于标记提供对象的类，这个类负责提供一些我们需要的对象，
 * 比如SharedPreferences是通过构造函数无法直接提供的，这时候就可以通过一个有@Module的类，这个类负责提供内容
 * 绑定项目中的所有子组件。在此处添加其他子组件的绑定
 * 引入 @ContributesAndroidInjector 注释作用
 * 1. 创建用@Subcomponent注释的单独组件（需要定义@Subcomponent类）
 * 2. 编写自定义注释，如@PerActivity。
 */
@Module
public abstract class PlexActivityModule {


    @ContributesAndroidInjector
    abstract PlexSplashActivity contributeSplashActivity();

    @ContributesAndroidInjector
    abstract TestViewActivity contributeTestViewActivity();

    @ContributesAndroidInjector
    abstract MediaDetailsActivity contributeMediaDetailsActivity();

    @ContributesAndroidInjector
    abstract MainPlayerActivity contributeMainPlayerActivity();

    // 如果存在Fragment，则将Activity对应的FragmentModule依赖进来
    @ContributesAndroidInjector(modules = PlexFragmentBuilderModule.class)
    abstract PlexMainActivity contributeMainActivity();
}
