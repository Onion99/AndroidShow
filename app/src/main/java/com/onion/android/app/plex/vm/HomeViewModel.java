package com.onion.android.app.plex.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.onion.android.app.plex.data.model.MovieResponse;
import com.onion.android.app.plex.data.model.status.Status;
import com.onion.android.app.plex.data.model.suggestions.Suggest;
import com.onion.android.app.plex.data.repository.MediaRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class HomeViewModel extends ViewModel {

    private final MediaRepository mediaRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public final MutableLiveData<MovieResponse> movieChoosedMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse>  movieRecommendedMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse>  movieTrendingMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse>  movieLatestMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<Status>  playerMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse>  popularSeriesMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> latestSeriesMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> latestAnimesMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> thisweekMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> popularMoviesMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> featuredMoviesMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<Suggest> suggestMutableLiveData = new MutableLiveData<>();

    @Inject
    HomeViewModel(MediaRepository mediaRepository){
        this.mediaRepository = mediaRepository;
    }

    public void initData(){
    }

}
