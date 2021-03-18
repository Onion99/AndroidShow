package com.onion.android.app.plex.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.onion.android.app.plex.data.local.entity.Download;
import com.onion.android.app.plex.data.local.entity.Media;

import java.util.List;
import io.reactivex.Flowable;

@Dao
public interface FavoriteDao {


    @Query("SELECT * FROM favorite")
    Flowable<List<Media>> getFavoriteMovies();

    @Query("SELECT * FROM favorite WHERE tmdbId=:tmdbId")
    LiveData<Media> isFavoriteMovie(int tmdbId);

    @Query("SELECT * FROM favorite WHERE tmdbId=:tmdbId")
    boolean isFeaturedFavoriteMovie(int tmdbId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMediaToFavorite(Media mediaDetail);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMediaToFavorite1(Download mediaDetail);

    @Delete
    void deleteMediaFromFavorite(Media mediaDetail);

    @Query("DELETE FROM favorite")
    void deleteMediaFromFavorite();

}

