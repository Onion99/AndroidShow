package com.onion.android.app.plex.data.datasource.movie;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.remote.ApiInterface;
import com.onion.android.app.plex.manager.SettingsManager;

import javax.inject.Inject;

public class MovieDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Media>> itemLiveDataSource = new MutableLiveData<>();

    private final ApiInterface requestInterface;
    private final SettingsManager settingsManager;

    @Inject
    public MovieDataSourceFactory(ApiInterface requestInterface,SettingsManager settingsManager) {
        this.requestInterface = requestInterface;
        this.settingsManager = settingsManager;
    }

    @Override
    public DataSource create() {

        MovieDataSource movieDataSource = new MovieDataSource(requestInterface,settingsManager);
        itemLiveDataSource.postValue(movieDataSource);

        return movieDataSource;

    }

    public MutableLiveData<PageKeyedDataSource<Integer, Media>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }

}
