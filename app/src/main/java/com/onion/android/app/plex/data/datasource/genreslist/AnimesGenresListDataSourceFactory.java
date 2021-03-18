package com.onion.android.app.plex.data.datasource.genreslist;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.manager.SettingsManager;

public class AnimesGenresListDataSourceFactory extends DataSource.Factory<Integer, Media> {

    private AnimesGenreListDataSource animesGenreListDataSource;
    private final String query;
    private final SettingsManager settingsManager;


    public AnimesGenresListDataSourceFactory(AnimesGenreListDataSource animesGenreListDataSource, String query,SettingsManager settingsManager) {
        this.animesGenreListDataSource = animesGenreListDataSource;
        this.settingsManager = settingsManager;
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Media> create() {
        return new AnimesGenreListDataSource(query,this,settingsManager);
    }
}
