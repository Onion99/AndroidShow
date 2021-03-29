package com.onion.android.app.plex.di.injector;

import com.onion.android.App;
import com.onion.android.app.plex.di.component.AppComponent;
import com.onion.android.app.plex.di.component.DaggerAppComponent;

import javax.inject.Inject;

/**
 * Dagger- init 第一步必须执行，不然会报错
 * 实现自动依赖注入
 * */
public class AppInjector {
    public AppInjector() {
    }

    public static void init(App app){
        DaggerAppComponent.builder()
                .application(app)
                .build()
                .inject(app);
    }
}