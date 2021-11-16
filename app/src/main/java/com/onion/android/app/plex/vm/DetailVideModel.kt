package com.onion.android.app.plex.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onion.android.app.plex.data.local.entity.Media
import com.onion.android.app.plex.data.repository.MediaRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class DetailVideModel : ViewModel {
    lateinit var media: Media
    var mediaLoad = false
    private val compositeDisposable = CompositeDisposable()
    private lateinit var mediaRepository: MediaRepository
    val movieDetailMutableLiveData = MutableLiveData<Media>()


    @Inject
    constructor(mediaRepository: MediaRepository) : super() {
        this.mediaRepository = mediaRepository
    }

    fun getMediaDetail(tmdb: String) {
        mediaRepository.getSerie(tmdb)

/*        compositeDisposable.add(
            mediaRepository.getSerie(tmdb)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(movieDetailMutableLiveData::postValue, this::handleError)
        )*/
    }

    // Handle Errors
    private fun handleError(e: Throwable) {
        Timber.e("In onError()%s", e.message)
    }
}