package com.onion.android.app.plex.data.datasource.genreslist;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.model.genres.GenresData;
import com.onion.android.app.plex.data.remote.ApiInterface;
import com.onion.android.app.plex.data.remote.ServiceGenerator;
import com.onion.android.app.plex.manager.SettingsManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ByGenreListDataSource extends PageKeyedDataSource<Integer, Media> {

    private final String genreId;
    private final ByGenresListDataSourceFactory byGenresListDataSourceFactory;
    private final SettingsManager settingsManager;



    public ByGenreListDataSource(String genreId, ByGenresListDataSourceFactory byGenresListDataSourceFactory,SettingsManager settingsManager){

        this.byGenresListDataSourceFactory = byGenresListDataSourceFactory;
        this.settingsManager = settingsManager;
        this.genreId = genreId;

    }

    public static final int PAGE_SIZE = 12;
    private static final int FIRST_PAGE = 1;



    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Media> callback) {

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GenresData> call = apiInterface.getContentByGenre(genreId,settingsManager.getSettings().getApiKey(),FIRST_PAGE);
        call.enqueue(new Callback<GenresData>() {

            @Override
            public void onResponse(@NotNull Call<GenresData> call, @NotNull Response<GenresData> response) {


                if (response.isSuccessful()) {

                    callback.onResult(response.body().getGlobaldata(), null, FIRST_PAGE+1);


                }
            }

            @Override
            public void onFailure(@NotNull Call<GenresData> call, @NotNull Throwable t) {

                //
            }
        });

    }

    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Media> callback) {


        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GenresData> call = apiInterface.getContentByGenre(genreId,settingsManager.getSettings().getApiKey(),params.key);
        call.enqueue(new Callback<GenresData>() {

            @Override
            public void onResponse(@NotNull Call<GenresData> call, @NotNull Response<GenresData> response) {


                if (response.isSuccessful()) {

                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    callback.onResult(response.body().getGlobaldata(), key);


                }
            }

            @Override
            public void onFailure(@NotNull Call<GenresData> call, @NotNull Throwable t) {
                //
            }
        });


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Media> callback) {

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);

        Call<GenresData> call = apiInterface.getContentByGenre(genreId,settingsManager.getSettings().getApiKey(),params.key);
        call.enqueue(new Callback<GenresData>() {

            @Override
            public void onResponse(@NotNull Call<GenresData> call, @NotNull Response<GenresData> response) {


                if (response.isSuccessful()) {

                    callback.onResult(response.body().getGlobaldata(), params.key + 1);


                }
            }

            @Override
            public void onFailure(@NotNull Call<GenresData> call, @NotNull Throwable t) {
                //
            }
        });


    }

}