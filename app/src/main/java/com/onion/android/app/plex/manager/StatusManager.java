package com.onion.android.app.plex.manager;

import android.content.SharedPreferences;

import com.onion.android.app.plex.data.model.status.Status;

import static com.onion.android.app.constants.PlexConstants.CODE;


public class StatusManager {


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public StatusManager(SharedPreferences prefs) {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public void saveSettings(Status status) {
        editor.putString(CODE, status.getCode()).commit();
        editor.apply();
    }


    public Status getAds() {
        Status status = new Status();
        status.setCode(prefs.getString(CODE, null));
        return status;
    }


}
