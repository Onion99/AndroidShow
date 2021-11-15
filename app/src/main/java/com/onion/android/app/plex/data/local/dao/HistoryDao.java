package com.onion.android.app.plex.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.onion.android.app.plex.data.local.entity.History;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;


@Dao
public interface HistoryDao {

    // Return Movies & Series From Favorite Table
    @Query("SELECT * FROM history ORDER BY createdAt DESC")
    Flowable<List<History>> getHistory();


    @Query("SELECT * FROM history WHERE tmdbId=:tmdbId")
    Flowable<List<History>> getHistoryByTmdb(Integer tmdbId);

    // Save the the movie or serie in the  Favorite Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMediaToFavorite(History history);




    @Query("SELECT * FROM history WHERE tmdbId_history=:tmdbId")
    LiveData<History> isHistory(int tmdbId);



    @Query("SELECT * FROM history WHERE tmdbId=:tmdbId")
    boolean isHistorytv(int tmdbId);


    @Query("DELETE FROM history")
    void deleteHistory();



    // Return true if the element in the featured is in the  Favorite Table
    @Query("SELECT * FROM history WHERE tmdbId=:tmdbId")
    boolean hasHistory(int tmdbId);


    @Query("SELECT * FROM history WHERE id=:id")
    boolean hasHistory3(int id);


    @Query("SELECT * FROM history WHERE id=:id")
    LiveData<History> hasHistory2(int id);


    @Delete
    void deleteMediaFromHistory(History mediaDetail);




}
