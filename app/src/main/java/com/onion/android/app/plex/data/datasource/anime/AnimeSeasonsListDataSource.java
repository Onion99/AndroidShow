package com.onion.android.app.plex.data.datasource.anime;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.onion.android.app.plex.data.model.episode.Episode;
import com.onion.android.app.plex.data.remote.ApiInterface;
import com.onion.android.app.plex.data.remote.ServiceGenerator;
import com.onion.android.app.plex.manager.SettingsManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnimeSeasonsListDataSource extends PageKeyedDataSource<Integer, Episode> {

    private String genreId;
    private final AnimeSeasonsListDataSourceFactory animeSeasonsListDataSourceFactory;

    private final SettingsManager settingsManager;

    public AnimeSeasonsListDataSource(String genreId, AnimeSeasonsListDataSourceFactory animeSeasonsListDataSourceFactory, SettingsManager settingsManager){

        this.animeSeasonsListDataSourceFactory = animeSeasonsListDataSourceFactory;
        this.settingsManager = settingsManager;
        this.genreId = genreId;

    }

    public static final int PAGE_SIZE = 12;
    public static final int PAGE_SIZE_PLAYER = 4;
    private static final int FIRST_PAGE = 1;



    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Episode> callback) {

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Episode> call = apiInterface.getAnimeSeasonsPaginate(genreId,settingsManager.getSettings().getApiKey(),FIRST_PAGE);
        call.enqueue(new Callback<Episode>() {

            @Override
            public void onResponse(@NotNull Call<Episode> call, @NotNull Response<Episode> response) {


                if (response.isSuccessful()) {

                    callback.onResult(response.body().getGlobaldata(), null, FIRST_PAGE+1);


                }
            }

            @Override
            public void onFailure(@NotNull Call<Episode> call, @NotNull Throwable t) {

                //
            }
        });

    }

    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Episode> callback) {


        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Episode> call = apiInterface.getAnimeSeasonsPaginate(genreId,settingsManager.getSettings().getApiKey(),params.key);
        call.enqueue(new Callback<Episode>() {

            @Override
            public void onResponse(@NotNull Call<Episode> call, @NotNull Response<Episode> response) {


                if (response.isSuccessful()) {

                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    callback.onResult(response.body().getGlobaldata(), key);


                }
            }

            @Override
            public void onFailure(@NotNull Call<Episode> call, @NotNull Throwable t) {
                //
            }
        });


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Episode> callback) {

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);

        Call<Episode> call = apiInterface.getAnimeSeasonsPaginate(genreId,settingsManager.getSettings().getApiKey(),params.key);
        call.enqueue(new Callback<Episode>() {

            @Override
            public void onResponse(@NotNull Call<Episode> call, @NotNull Response<Episode> response) {


                if (response.isSuccessful()) {

                    callback.onResult(response.body().getGlobaldata(), params.key + 1);


                }
            }

            @Override
            public void onFailure(@NotNull Call<Episode> call, @NotNull Throwable t) {
                //
            }
        });


    }

}