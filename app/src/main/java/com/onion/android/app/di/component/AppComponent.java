package com.onion.android.app.di.component;

import android.app.Application;

import com.onion.android.App;
import com.onion.android.app.di.module.PlexActivityModule;
import com.onion.android.app.di.module.PlexAppModule;
import com.onion.android.app.di.module.PlexFragmentBuilderModule;
import com.onion.android.app.di.module.PokedexActivityModule;
import com.onion.android.app.di.module.PokedexAppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


///////////////////////////////////////////////////////////////////////////
// Dagger-1 声明项目所需要的AppComponent，即用来声明哪些可以提供对象的Module
// Component 确定所有必须使用的模块的组件，以及这些依赖项注入应该在哪些类中工作
// GoogleLab https://developer.android.com/codelabs/android-dagger#1
// @Singleton 表示此只可以實例化一次
///////////////////////////////////////////////////////////////////////////
@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                PlexAppModule.class,
                PokedexAppModule.class,
                PokedexActivityModule.class,
                PlexActivityModule.class,
                PlexFragmentBuilderModule.class,
        }
)
public interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);
}
