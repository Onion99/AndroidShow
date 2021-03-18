package com.onion.android.app.plex.data.datasource.genreslist;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.manager.SettingsManager;

public class ByGenresListDataSourceFactory extends DataSource.Factory<Integer, Media> {

    private ByGenreListDataSource byGenreListDataSource;
    private final SettingsManager settingsManager;
    private String query;

    public ByGenresListDataSourceFactory(ByGenreListDataSource byGenreListDataSource, String query,SettingsManager settingsManager) {
        this.byGenreListDataSource = byGenreListDataSource;
        this.settingsManager = settingsManager;
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Media> create() {
        return new ByGenreListDataSource(query,this,settingsManager);
    }
}
