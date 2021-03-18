package com.onion.android.app.plex.manager;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.onion.android.app.plex.data.model.ads.Ads;

import static com.onion.android.app.constants.PlexConstants.*;


public class AdsManager {



    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public AdsManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public void saveSettings(Ads ads){
        editor.putString(ADS_LINK, ads.getLink()).commit();
        editor.putString(ADS_CLICKTHROUGHURL, ads.getClickThroughUrl()).commit();
        editor.apply();
    }

    public void deleteAds(){
        editor.remove(ADS_LINK).commit();
        editor.remove(ADS_CLICKTHROUGHURL).commit();
    }

    public Ads getAds(){
        Ads ads = new Ads();
        ads.setLink(prefs.getString(ADS_LINK, null));
        ads.setClickThroughUrl(prefs.getString(ADS_CLICKTHROUGHURL, null));
        return ads;
    }




}
