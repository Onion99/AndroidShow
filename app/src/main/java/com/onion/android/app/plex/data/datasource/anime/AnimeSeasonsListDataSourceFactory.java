package com.onion.android.app.plex.data.datasource.anime;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.onion.android.app.plex.data.model.episode.Episode;
import com.onion.android.app.plex.manager.SettingsManager;

public class AnimeSeasonsListDataSourceFactory extends DataSource.Factory<Integer, Episode> {

    private AnimeSeasonsListDataSource animeSeasonsListDataSource;
    private final String query;
    private final SettingsManager settingsManager;

    public AnimeSeasonsListDataSourceFactory(AnimeSeasonsListDataSource animeSeasonsListDataSource, String query, SettingsManager settingsManager) {
        this.animeSeasonsListDataSource = animeSeasonsListDataSource;
        this.settingsManager = settingsManager;
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Episode> create() {
        return new AnimeSeasonsListDataSource(query,this,settingsManager);
    }
}
