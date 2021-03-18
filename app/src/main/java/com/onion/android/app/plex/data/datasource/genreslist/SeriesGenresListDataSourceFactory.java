package com.onion.android.app.plex.data.datasource.genreslist;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.manager.SettingsManager;

public class SeriesGenresListDataSourceFactory extends DataSource.Factory<Integer, Media> {

    private SeriesGenreListDataSource streamDataSource;
    private String query;
    private final SettingsManager settingsManager;


    public SeriesGenresListDataSourceFactory(SeriesGenreListDataSource streamDataSource, String query,SettingsManager settingsManager) {
        this.streamDataSource = streamDataSource;
        this.settingsManager = settingsManager;
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Media> create() {
        return new SeriesGenreListDataSource(query,this,settingsManager);
    }
}
