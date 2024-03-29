package com.onion.android.app.di.module;

import static android.content.Context.MODE_PRIVATE;
import static com.onion.android.app.constants.PlexConstants.PREF_FILE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.onion.android.app.plex.data.local.EasyPlexDataBase;
import com.onion.android.app.plex.data.local.dao.DownloadDao;
import com.onion.android.app.plex.data.local.dao.HistoryDao;
import com.onion.android.app.plex.data.local.dao.ResumeDao;
import com.onion.android.app.plex.data.local.dao.StreamListDao;
import com.onion.android.app.plex.data.remote.ApiInterface;
import com.onion.android.app.plex.data.remote.FsmPlayerApi;
import com.onion.android.app.plex.data.remote.ServiceGenerator;
import com.onion.android.app.plex.manager.AuthManager;
import com.onion.android.app.plex.manager.SettingsManager;
import com.onion.android.app.plex.manager.StatusManager;
import com.onion.android.app.plex.manager.TokenManager;
import com.onion.android.app.plex.player.controller.PlayerController;
import com.onion.android.app.plex.player.controller.PlayerUIController;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * Application域的注入
 * Module表示包含将提供依赖项的方法的类。
 */
@Module(includes = PlexViewModelModule.class)
public class PlexAppModule {
    // Rxjava CompositeDisposable注入
    @Provides
    @Singleton
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }


    // Application 注入
    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    // 全局配置提供
    @Provides
    @Singleton
    StatusManager provideStatusManager(Application application) {
        return new StatusManager(application.getSharedPreferences(PREF_FILE, MODE_PRIVATE));
    }

    @Provides
    @Singleton
    SettingsManager provideSettingsManager(Application application) {
        return new SettingsManager(application.getSharedPreferences(PREF_FILE, MODE_PRIVATE));
    }


    @Provides
    @Singleton
    AuthManager provideAuthManager(Application application) {
        return new AuthManager(application.getSharedPreferences(PREF_FILE, MODE_PRIVATE));
    }


    @Provides
    @Singleton
    SharedPreferences.Editor providesSharedPreferencesEditor(Application application) {
        return application.getSharedPreferences(PREF_FILE, MODE_PRIVATE).edit();
    }


    // Http Service - 注入
    @Singleton
    @Provides
    ApiInterface provideMoviesService() {
        return ServiceGenerator.createService(ApiInterface.class);
    }

    @Provides
    @Singleton
    @Named("app")
    ApiInterface provideServiceApp() {
        return ServiceGenerator.createServiceApp(ApiInterface.class);

    }

    @Provides
    @Singleton
    @Named("status")
    ApiInterface provideServiceStatus() {
        return ServiceGenerator.createServiceWithStatus(ApiInterface.class);

    }


    @Provides
    @Singleton
    @Named("imdb")
    ApiInterface provideMoviesServiceImdb() {
        return ServiceGenerator.createServiceImdb(ApiInterface.class);

    }


    @Provides
    @Singleton
    @Named("opensubs")
    ApiInterface provideMoviesServiceOpenSubs() {
        return ServiceGenerator.createServiceOpenSubs(ApiInterface.class);

    }


    @Provides
    @Singleton
    @Named("Auth")
    ApiInterface provideServiceAuth(TokenManager tokenManager) {
        return ServiceGenerator.createServiceWithAuth(ApiInterface.class, tokenManager);

    }

    @Provides
    @Singleton
    @Named("player")
    ApiInterface playerAdLogicControllerApi() {
        return FsmPlayerApi.playerAdLogicControllerApi(ApiInterface.class);
    }


    // Room 数据库 - 注入
    @Singleton
    @Provides
    EasyPlexDataBase provideDataBase(Application application) {
        return Room.databaseBuilder(application, EasyPlexDataBase.class, "plex.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Singleton
    @Provides
    DownloadDao provideProgressDao(EasyPlexDataBase dataBase) {
        return dataBase.progressDao();
    }

    @Singleton
    @Provides
    StreamListDao provideStreamyDao(EasyPlexDataBase dataBase) {
        return dataBase.streamListDao();
    }


    @Singleton
    @Provides
    ResumeDao provideResumeDao(EasyPlexDataBase dataBase) {
        return dataBase.resumeDao();
    }

    @Singleton
    @Provides
    HistoryDao provideHistoryDao(EasyPlexDataBase dataBase) {
        return dataBase.historyDao();
    }

    @Provides
    @Singleton
    PlayerUIController provideController() {
        return new PlayerUIController();
    }


    @Provides
    @Singleton
    PlayerController providePlayerController() {
        return new PlayerController();
    }


    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(PREF_FILE, MODE_PRIVATE);
    }
}
