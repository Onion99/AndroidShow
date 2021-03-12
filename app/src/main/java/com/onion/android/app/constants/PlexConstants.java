package com.onion.android.app.constants;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

/**
 * Plex Application CONSTANTS.
 */
public abstract class PlexConstants{
    // this is not your laravel api , this key will be used to authorise all api calls inside your app ( this key must be the same as API_KEY value in your .env file otherwise you will get
    //  "Invalid access key" error in your api calls )
    // for ex http://192.168.1.130/public/api/movies/featured/p2lbgWkFrykA4QyUmpHihzmc5BNzIABq
    public static final String API_KEY = "p2lbgWkFrykA4QyUmpHihzmc5BNzIABq";
    // this is your laravel main api ( Change this encoded value to Your Server Encoded URL ( BASE64 ) )
    // for ex http://192.168.1.130/public/api/          !note : url must be ending with /
    public static final String SERVER_ENCODED = "aHR0cHM6Ly9lYXN5cGxleC55b2JkZXYubGl2ZS9hcHAvcHVibGljL2NkanNreHFtdGRsbGVhamxucmptcGIv";
    // Don't Change
    public static final String IMDB_ENCODED = "aHR0cHM6Ly9hcGkudGhlbW92aWVkYi5vcmcvMy8=";
    // Don't Change
    public static final String SUBS_ENCODED = "aHR0cHM6Ly9yZXN0Lm9wZW5zdWJ0aXRsZXMub3JnLw==";
    // API Constants
    public static final String IMDB_BASE_URL = decodeImdbApi();
    public static final String SERVER_BASE_URL = decodeServerMainApi();
    public static final String SERVER_OPENSUBS_URL = decodeSubsApi();

    private static String decodeServerMainApi(){
        byte[] valueDecoded;
        valueDecoded = Base64.decode(SERVER_ENCODED.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }

    private static String decodeImdbApi(){
        byte[] valueDecoded;
        valueDecoded = Base64.decode(IMDB_ENCODED.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }


    private static String decodeSubsApi(){
        byte[] valueDecoded;
        valueDecoded = Base64.decode(SUBS_ENCODED.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }

}