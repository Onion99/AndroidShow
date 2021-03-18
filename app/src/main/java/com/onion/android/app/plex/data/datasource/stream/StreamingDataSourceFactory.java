package com.onion.android.app.plex.data.datasource.stream;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.manager.SettingsManager;


public class StreamingDataSourceFactory extends DataSource.Factory<Integer, Media> {

    private StreamDataSource streamDataSource;
    private final String query;
    private final SettingsManager settingsManager;

    public StreamingDataSourceFactory(StreamDataSource streamDataSource, String query,SettingsManager settingsManager) {
        this.streamDataSource = streamDataSource;
        this.settingsManager = settingsManager;
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Media> create() {
        return new StreamDataSource( query,this,settingsManager);
    }
}
