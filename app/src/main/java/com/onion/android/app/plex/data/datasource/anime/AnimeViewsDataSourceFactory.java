package com.onion.android.app.plex.data.datasource.anime;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.remote.ApiInterface;
import com.onion.android.app.plex.manager.SettingsManager;

import javax.inject.Inject;

public class AnimeViewsDataSourceFactory extends DataSource.Factory {

    private final MutableLiveData<PageKeyedDataSource<Integer, Media>> serieLiveDataSource = new MutableLiveData<>();

    private final ApiInterface requestInterface;
    private final SettingsManager settingsManager;

    @Inject
    public AnimeViewsDataSourceFactory(ApiInterface requestInterface,SettingsManager settingsManager) {
        this.requestInterface = requestInterface;
        this.settingsManager = settingsManager;
    }

    @Override
    public DataSource create() {

        AnimeYearsDataSource serieDataSource = new AnimeYearsDataSource(requestInterface,settingsManager);
        serieLiveDataSource.postValue(serieDataSource);

        return serieDataSource;

    }

    public MutableLiveData<PageKeyedDataSource<Integer, Media>> getItemLiveDataSource() {
        return serieLiveDataSource;
    }

}
