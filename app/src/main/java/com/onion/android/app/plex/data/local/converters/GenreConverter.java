package com.onion.android.app.plex.data.local.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onion.android.app.plex.data.model.genres.Genre;

import java.lang.reflect.Type;
import java.util.List;


public class GenreConverter {

    private GenreConverter(){}

    /**
     * @TypeConverter 将方法标记为类型转换器
     * 1. 一个类可以根据需要拥有任意数量的@TypeConverter方法
     * 2. 每个转换器方法应该接收1个参数并具有非空返回类型。
     * */
    @TypeConverter
    public static List<Genre> fromString(String value) {
        Type listType = new TypeToken<List<Genre>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Genre> genres) {
        Gson gson = new Gson();
        return gson.toJson(genres);
    }
}
