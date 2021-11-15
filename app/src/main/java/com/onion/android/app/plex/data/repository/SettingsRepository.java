package com.onion.android.app.plex.data.repository;

import com.onion.android.app.plex.data.model.MovieResponse;
import com.onion.android.app.plex.data.model.ads.Ads;
import com.onion.android.app.plex.data.model.settings.Settings;
import com.onion.android.app.plex.data.model.status.Status;
import com.onion.android.app.plex.data.remote.ApiInterface;
import com.onion.android.app.plex.manager.SettingsManager;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Observable;

;


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class SettingsRepository {


    @Inject
    ApiInterface apiInterface;


    @Inject
    SettingsManager settingsManager;


    @Inject
    @Named("status")
    ApiInterface requestStatusApi;


    @Inject
    SettingsRepository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }




    // Return List of Added Ads for the Player
    public Observable<Ads> getAdsSettings() {
        return apiInterface.getAdsSettings(settingsManager.getSettings().getApiKey());
    }



    // Return App Settings
    public Observable<Settings> getSettings() {
        return apiInterface.getSettings(settingsManager.getSettings().getApiKey());
    }



    // Return Status
    public Observable<Status> getStatus() {
        return apiInterface.getStatus();
    }


    // Return Status
    public Observable<Status> getApiStatus(String key) {
        return requestStatusApi.getApiStatus(key);
    }



    public Observable<Status> getApp(String key) {
        return requestStatusApi.getApp(key);
    }


    public Observable<MovieResponse> getPlans() {
        return apiInterface.getPlans(settingsManager.getSettings().getApiKey());
    }



}

