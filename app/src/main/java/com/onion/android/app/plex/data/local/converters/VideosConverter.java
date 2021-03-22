package com.onion.android.app.plex.data.local.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onion.android.app.plex.data.model.trailer.Video;

import java.lang.reflect.Type;
import java.util.List;


public class VideosConverter {

    private VideosConverter(){}

    @TypeConverter
    public static List<Video> convertStringToList(String videoString) {
        Type listType = new TypeToken<List<Video>>() {
        }.getType();
        return new Gson().fromJson(videoString, listType);
    }

    @TypeConverter
    public static String convertListToString(List<Video> videos) {
        Gson gson = new Gson();
        return gson.toJson(videos);
    }
}
