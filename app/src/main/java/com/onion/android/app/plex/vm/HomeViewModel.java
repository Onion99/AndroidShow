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


    @Inject
    HomeViewModel(MediaRepository mediaRepository){
        this.mediaRepository = mediaRepository;
    }

    public void initData(){
        // get feature data
        compositeDisposable.add(mediaRepository.getFeatured()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(featuredMoviesMutableLiveData::postValue,this::handleError)
        );
    }
    // HandleError
    private void handleError(Throwable e) {
        Timber.i("In onError()%s", e.getMessage());
    }

}
