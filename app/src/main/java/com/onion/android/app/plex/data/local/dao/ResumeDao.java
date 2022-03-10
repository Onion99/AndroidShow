package com.onion.android.app.plex.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.onion.android.app.plex.data.model.media.Resume;

@Dao
public interface ResumeDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMediaToResume(Resume resume);


    @Query("SELECT * FROM resume WHERE tmdb=:tmdbId")
    LiveData<Resume> hasResume(int tmdbId);


    @Query("DELETE FROM resume")
    void deleteHistory();
}
