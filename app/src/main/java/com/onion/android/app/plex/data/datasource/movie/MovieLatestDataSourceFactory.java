package com.onion.android.app.plex.data.datasource.movie;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.remote.ApiInterface;
import com.onion.android.app.plex.manager.SettingsManager;
import javax.inject.Inject;

public class MovieLatestDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Media>> itemLiveDataSource = new MutableLiveData<>();

    private final ApiInterface requestInterface;
    private final SettingsManager settingsManager;


    @Inject
    public MovieLatestDataSourceFactory(ApiInterface requestInterface, SettingsManager settingsManager) {
        this.requestInterface = requestInterface;
        this.settingsManager = settingsManager;
    }

    @Override
    public DataSource create() {

        MovieLatestDataSource movieLatestDataSource = new MovieLatestDataSource(requestInterface,settingsManager);
        itemLiveDataSource.postValue(movieLatestDataSource);


        return movieLatestDataSource;

    }

    public MutableLiveData<PageKeyedDataSource<Integer, Media>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }



    public MutableLiveData<PageKeyedDataSource<Integer, Media>> getItemLiveDataSource2() {
        return itemLiveDataSource;
    }
}
