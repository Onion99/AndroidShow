package com.onion.android.app.plex.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.onion.android.app.plex.data.model.MovieResponse;
import com.onion.android.app.plex.data.repository.MediaRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class HomeViewModel extends ViewModel {
    private final MediaRepository mediaRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public final MutableLiveData<MovieResponse> featuredMoviesMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> latestStreamingMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> recommendedMovieMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> trendingMovieMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> movieReleaseMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> popularSeriesMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<MovieResponse> popularMoviesMutableLiveData = new MutableLiveData<>();


    // State
    public boolean mFeaturedLoaded;
    public boolean mScrollLoaded;

    @Inject
    HomeViewModel(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public void initData() {
        // 获取热门影视
        compositeDisposable.add(mediaRepository.getFeatured()
                // mediaRepository.getFeature 所在的线程
                .subscribeOn(Schedulers.io())
                // featuredMoviesMutableLiveData::postValue 所在的线程
                .observeOn(AndroidSchedulers.mainThread())
                // 是否缓存
                .cache()
                // 执行完毕后，通知谁
                .subscribe(featuredMoviesMutableLiveData::postValue, this::handleError)
        );
        // 获取最新直播频道
        compositeDisposable.add(mediaRepository.getLatestStreaming()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(latestStreamingMutableLiveData::postValue, this::handleError)
        );
        // 推荐影片
        compositeDisposable.add(mediaRepository.getRecommended()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(recommendedMovieMutableLiveData::postValue, this::handleError)
        );
        // 流行
        compositeDisposable.add(mediaRepository.getTrending()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(trendingMovieMutableLiveData::postValue, this::handleError)
        );
        // 发行
        compositeDisposable.add(mediaRepository.getLatestMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(movieReleaseMutableLiveData::postValue, this::handleError)
        );
        // 流行电视剧
        compositeDisposable.add(mediaRepository.getPopularSeries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(popularSeriesMutableLiveData::postValue, this::handleError)
        );
        // 最受欢迎
        compositeDisposable.add(mediaRepository.getPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(popularMoviesMutableLiveData::postValue, this::handleError)
        );

    }
    // HandleError
    private void handleError(Throwable e) {
        Timber.i("In onError()%s", e.getMessage());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public boolean checkDataLoaded(){
        return mFeaturedLoaded && mScrollLoaded;
    }
}
