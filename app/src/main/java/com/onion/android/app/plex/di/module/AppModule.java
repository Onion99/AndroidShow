package com.onion.android.app.plex.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.onion.android.app.plex.data.local.EasyPlexDataBase;
import com.onion.android.app.plex.data.local.dao.DownloadDao;
import com.onion.android.app.plex.data.local.dao.FavoriteDao;
import com.onion.android.app.plex.data.local.dao.HistoryDao;
import com.onion.android.app.plex.data.local.dao.StreamListDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Application域的注入
 * Module表示包含将提供依赖项的方法的类。
 * */
@Module
public class AppModule {

    // Application 注入
    @Singleton
    @Provides
    Context provideContext(Application application){
        return application.getApplicationContext();
    }

    // Room 数据库注入
    @Singleton
    @Provides
    EasyPlexDataBase provideDataBase(Application application){
        return Room.databaseBuilder(application,EasyPlexDataBase.class,"plex.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }
    // Room 表注入
    @Singleton
    @Provides
    FavoriteDao provideFavMoviesDao(EasyPlexDataBase dataBase) {
        return dataBase.favoriteDao();
    }

    @Singleton
    @Provides
    DownloadDao provideProgressDao(EasyPlexDataBase dataBase){
        return dataBase.progressDao();
    }

    @Singleton
    @Provides
    StreamListDao provideStreamyDao(EasyPlexDataBase dataBase){
        return dataBase.streamListDao();
    }


    @Singleton
    @Provides
    HistoryDao provideHistoryDao(EasyPlexDataBase dataBase){
        return dataBase.historyDao();
    }

}
