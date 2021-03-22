package com.onion.android.app.plex.data.local.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.onion.android.app.plex.data.model.substitles.MediaSubstitle;

import java.util.ArrayList;
import java.util.List;


public class MediaSubstitlesConverter {


    private MediaSubstitlesConverter(){


    }

    private static String strSeparator = "__,__";

    @TypeConverter
    public static List<MediaSubstitle> convertStringToList(String castString) {
        String[] castArray = castString.split(strSeparator);
        List<MediaSubstitle> mediaSubstitles = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 0; i < castArray.length - 1; i++) {
            mediaSubstitles.add(gson.fromJson(castArray[i], MediaSubstitle.class));
        }
        return mediaSubstitles;
    }

    @TypeConverter
    public static String convertListToString(List<MediaSubstitle> substitles) {
        Gson gson = new Gson();
        return gson.toJson(substitles);
    }
}
