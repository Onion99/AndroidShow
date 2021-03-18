package com.onion.android.app.plex.manager;

import android.content.SharedPreferences;

import com.onion.android.app.plex.data.model.auth.UserAuthInfo;


import static com.onion.android.app.constants.PlexConstants.*;





public class AuthManager {


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    public AuthManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public void saveSettings(UserAuthInfo userAuthInfo){
        editor.putInt(PREMUIM, userAuthInfo.getPremuim()).commit();
        editor.putString(AUTH_NAME, userAuthInfo.getName()).commit();
        editor.putInt(AUTH_ID, userAuthInfo.getId()).commit();
        editor.putString(AUTH_EXPIRED_DATE, userAuthInfo.getExpiredIn()).commit();
        editor.apply();
    }

    public void deleteAuth(){
        editor.remove(PREMUIM).commit();
        editor.remove(AUTH_NAME).commit();
        editor.remove(AUTH_ID).commit();
        editor.remove(AUTH_EXPIRED_DATE).commit();
    }

    public UserAuthInfo getUserInfo(){
        UserAuthInfo userAuthInfo = new UserAuthInfo();
        userAuthInfo.setPremuim(prefs.getInt(PREMUIM, 0));
        userAuthInfo.setName(prefs.getString(AUTH_NAME, null));
        userAuthInfo.setId(prefs.getInt(AUTH_ID, 0));
        userAuthInfo.setExpiredIn(prefs.getString(AUTH_EXPIRED_DATE, null));
        return userAuthInfo;
    }




}
