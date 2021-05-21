package com.onion.android.app.plex.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.onion.android.app.plex.data.datasource.anime.AnimeDataSourceFactory;
import com.onion.android.app.plex.data.datasource.movie.MovieDataSourceFactory;
import com.onion.android.app.plex.data.datasource.series.SerieDataSource;
import com.onion.android.app.plex.data.datasource.series.SerieDataSourceFactory;
import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.remote.ApiInterface;
import com.onion.android.app.plex.manager.SettingsManager;

import javax.inject.Inject;

public class GenresViewModel extends ViewModel {

    public final LiveData<PagedList<Media>> moviePagedList;
    public final LiveData<PagedList<Media>> seriePagedList;
    public final LiveData<PagedList<Media>> animePagedList;

    @Inject
    public GenresViewModel(ApiInterface requestInterface, SettingsManager settingsManager) {
        PagedList.Config serieCongig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(SerieDataSource.PAGE_SIZE)
                        .setPrefetchDistance(SerieDataSource.PAGE_SIZE)
                        .setInitialLoadSizeHint(SerieDataSource.PAGE_SIZE)
                        .build();
        // 电影源
        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory(requestInterface, settingsManager);
        moviePagedList = (new LivePagedListBuilder(movieDataSourceFactory, serieCongig)).build();
        // 剧场源
        SerieDataSourceFactory serieDataSourceFactory = new SerieDataSourceFactory(requestInterface, settingsManager);
        seriePagedList = (new LivePagedListBuilder(serieDataSourceFactory, serieCongig)).build();
        // 动画源
        AnimeDataSourceFactory animeDataSourceFactory = new AnimeDataSourceFactory(requestInterface, settingsManager);
        animePagedList = (new LivePagedListBuilder(animeDataSourceFactory, serieCongig)).build();
    }

}
