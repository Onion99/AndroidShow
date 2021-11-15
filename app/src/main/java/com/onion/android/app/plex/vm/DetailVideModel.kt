package com.onion.android.app.plex.vm

import androidx.lifecycle.ViewModel
import com.onion.android.app.plex.data.local.entity.Media
import com.onion.android.app.plex.data.repository.MediaRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class DetailVideModel : ViewModel {
    lateinit var media: Media
    var mediaLoad = false
    private val compositeDisposable = CompositeDisposable()

    @Inject
    constructor(mediaRepository: MediaRepository) : super()

    /*    fun getMediaDetail(tmdb:String){
        compositeDisposable.add(
            mediaRepository.getSerie(tmdb)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe()
        )
    }*/
}