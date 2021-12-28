package com.onion.android.app.plex.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onion.android.app.plex.data.local.entity.Media
import com.onion.android.app.plex.data.model.MovieResponse
import com.onion.android.app.plex.data.repository.MediaRepository
import com.onion.android.app.plex.manager.SettingsManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DetailVideModel @Inject constructor() : ViewModel() {
    lateinit var media: Media
    var mediaLoad = false
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var mediaRepository: MediaRepository

    @Inject
    lateinit var settingsManager: SettingsManager
    val movieDetailMutableLiveData = MutableLiveData<Media>()
    val movieRelatesMutableLiveData = MutableLiveData<MovieResponse>()

    ///////////////////////////////////////////////////////////////////////////
    // 获取电影详情
    ///////////////////////////////////////////////////////////////////////////
    fun getMediaDetail(tmdb: String) {
        compositeDisposable.add(
            mediaRepository.getMovie(tmdb, settingsManager.settings.apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(movieDetailMutableLiveData::postValue, this::handleError)
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // 获取类似电影
    ///////////////////////////////////////////////////////////////////////////
    fun getRelatedMovies(id: Int) {
        compositeDisposable.add(
            mediaRepository.getRelateds(id, settingsManager.settings.apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(movieRelatesMutableLiveData::postValue, this::handleError)
        )
    }

    // Handle Errors
    private fun handleError(e: Throwable) {
        Timber.e("In onError()%s", e.message)
    }
}