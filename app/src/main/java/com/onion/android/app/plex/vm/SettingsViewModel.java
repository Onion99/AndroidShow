package com.onion.android.app.plex.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.onion.android.app.plex.data.model.settings.Settings;
import com.onion.android.app.plex.data.repository.SettingsRepository;
import com.onion.android.app.plex.manager.SettingsManager;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class SettingsViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final SettingsRepository settingsRepository;
    public final MutableLiveData<Settings> settingsMutableLiveData = new MutableLiveData<>();

    @Inject
    SettingsManager settingsManager;
    @Inject
    public SettingsViewModel(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public void getSettingsDetails() {
        // Fetch Settings Details
        compositeDisposable.add(settingsRepository.getSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(settingsMutableLiveData::postValue, this::handleError));
    }

    // Handle Errors
    private void handleError(Throwable e) {
        Timber.i("In onError()%s", e.getMessage());
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
