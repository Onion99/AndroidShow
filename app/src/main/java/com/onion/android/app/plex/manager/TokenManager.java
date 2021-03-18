package com.onion.android.app.plex.manager;

import android.content.SharedPreferences;

import com.onion.android.app.plex.data.model.auth.Login;





public class TokenManager {

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public TokenManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }


    public void saveToken(Login token){
        editor.putString(ACCESS_TOKEN, token.getAccessToken()).commit();
        editor.putString(REFRESH_TOKEN, token.getRefresh()).commit();
        editor.apply();
    }

    public void deleteToken(){
        editor.remove(ACCESS_TOKEN).commit();
        editor.remove(REFRESH_TOKEN).commit();
    }

    public Login getToken(){
        Login token = new Login();
        token.setAccessToken(prefs.getString(ACCESS_TOKEN, null));
        token.setRefresh(prefs.getString(REFRESH_TOKEN, null));
        return token;
    }




}
