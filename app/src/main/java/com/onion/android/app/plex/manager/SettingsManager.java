package com.onion.android.app.plex.manager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;


import com.onion.android.app.plex.data.model.settings.Settings;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.onion.android.app.constants.PlexConstants.*;



public class SettingsManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SettingsManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public SettingsManager() { }


    public void saveSettings(Settings settings){

        editor.putString(APP_NAME, settings.getAppName()).commit();
        editor.putInt(AD_FACEBOOK_INTERSTITIAL_SHOW, settings.getFacebookShowInterstitial()).commit();
        editor.putInt(AD_INTERSTITIAL_SHOW, settings.getAdShowInterstitial()).commit();
        editor.putInt(AD_INTERSTITIAL, settings.getAdInterstitial()).commit();
        editor.putString(AD_INTERSTITIAL_UNIT, settings.getAdUnitIdInterstitial()).commit();
        editor.putInt(AD_BANNER, settings.getAdBanner()).commit();
        editor.putString(AD_BANNER_UNIT, settings.getAdUnitIdBanner()).commit();
        editor.putString(PURCHASE_KEY, API_KEY).commit();
        editor.putString(TMDB, settings.getTmdbApiKey()).commit();
        editor.putString(PRIVACY_POLICY, settings.getPrivacyPolicy()).commit();
        editor.putInt(AUTOSUBSTITLES, settings.getAutosubstitles()).commit();
        editor.putInt(ANIME, settings.getAnime()).commit();
        editor.putString(LATEST_VERSION, settings.getLatestVersion()).commit();
        editor.putString(UPDATE_TITLE, settings.getUpdateTitle()).commit();
        editor.putString(RELEASE_NOTES, settings.getReleaseNotes()).commit();
        editor.putString(URL, settings.getUrl()).commit();
        editor.putString(PAYPAL_CLIENT_ID, settings.getPaypalClientId()).commit();
        editor.putString(PAYPAL_AMOUNT, settings.getPaypalAmount()).commit();
        editor.putInt(FEATURED_HOME_NUMBERS, settings.getFeaturedHomeNumbers()).commit();
        editor.putString(APP_URL_ANDROID, settings.getAppUrlAndroid()).commit();
        editor.putString(IMDB_COVER_PATH, settings.getImdbCoverPath()).commit();
        editor.putInt(ADS_SETTINGS, settings.getAds()).commit();
        editor.putInt(AD_INTERSTITIAL_FACEEBOK_ENABLE, settings.getAdFaceAudienceInterstitial()).commit();
        editor.putString(AD_INTERSTITIAL_FACEEBOK_UNIT_ID, settings.getAdUnitIdFacebookInterstitialAudience()).commit();
        editor.putString(AD_INTERSTITIAL_APPODEAL_UNIT_ID, settings.getAdUnitIdAppodealInterstitialAudience()).commit();
        editor.putString(AD_BANNER_APPODEAL_UNIT_ID, settings.getAdUnitIdAppodealBannerAudience()).commit();
        editor.putInt(AD_BANNER_FACEEBOK_ENABLE, settings.getAdFaceAudienceBanner()).commit();
        editor.putString(AD_BANNER_FACEEBOK_UNIT_ID, settings.getAdUnitIdFacebookBannerAudience()).commit();
        editor.putString(DEFAULT_NETWORK, settings.getDefaultRewardedNetworkAds()).commit();
        editor.putString(DEFAULT_NETWORK_PLAYER, settings.getDefaultNetworkPlayer()).commit();
        editor.putString(STARTAPP_ID, settings.getStartappId()).commit();
        editor.putString(ADMOB_REWARD, settings.getAdUnitIdRewarded()).commit();
        editor.putString(FACEBOOK_REWARD, settings.getAdUnitIdFacebookRewarded()).commit();
        editor.putString(UNITY_GAME_ID, settings.getUnityGameId()).commit();
        editor.putInt(WATCH_ADS_TO_UNLOCK, settings.getWachAdsToUnlock()).commit();
        editor.putInt(WATCH_ADS_TO_UNLOCK_PLAYER, settings.getWachAdsToUnlockPlayer()).commit();
        editor.putString(CUSTOM_MESSAGE, settings.getCustomMessage()).commit();
        editor.putInt(ENABLE_CUSTOM_MESSAGE, settings.getEnableCustomMessage()).commit();
        editor.putString(STRIPE_PUBLISHABLE_KEY, settings.getStripePublishableKey()).commit();
        editor.putString(STRIPE_SECRET_KEY, settings.getStripeSecretKey()).commit();
        editor.putString(APPODEAL_REWARD, settings.getAdUnitIdAppodealRewarded()).commit();
        editor.putInt(APPODEAL_BANNER, settings.getAppodealBanner()).commit();
        editor.putInt(DOWNLOADS_PREMUIM_ONLY, settings.getDownloadPremuimOnly()).commit();
        editor.putInt(NEXT_EPISODE_TIMER, settings.getNextEpisodeTimer()).commit();
        editor.putString(FACEBOOK, settings.getFacebookUrl()).commit();
        editor.putString(TWITTER, settings.getTwitterUrl()).commit();
        editor.putString(INSTAGRAM, settings.getInstagramUrl()).commit();
        editor.putString(YOUTUBE, settings.getTelegram()).commit();
        editor.putInt(ENABLE_SERVER_DIALOG_SELECTION, settings.getServerDialogSelection()).commit();
        editor.putString(API_KEY, settings.getApiKey()).commit();
        editor.putInt(AD_INTERSTITIAL_APPOBEAL_ENABLE, settings.getAppodealInterstitial()).commit();
        editor.putInt(AD_INTERSTITIAL_APPOBEAL_SHOW, settings.getAppodealShowInterstitial()).commit();
        editor.putInt(AD_NATIVEADS_ADMOB_ENABLE, settings.getAdUnitIdNativeEnable()).commit();
        editor.putString(AD_NATIVEADS_ADMOB_UNIT_ID, settings.getAdUnitIdNative()).commit();
        editor.putString(PAYPAL_CURRENCY, settings.getPaypalCurrency()).commit();
        editor.putString(DEFAULT_PAYMENT, settings.getDefaultPayment()).commit();
        editor.putInt(ENABLE_CUSTOM_BANNER, settings.getEnableCustomBanner()).commit();
        editor.putString(CUSTOM_BANNER_IMAGE, settings.getCustomBannerImage()).commit();
        editor.putString(CUSTOM_BANNER_IMAGE_LINK, settings.getCustomBannerImageLink()).commit();
        editor.putString(MANTENANCE_MESSAGE, settings.getMantenanceModeMessage()).commit();
        editor.putInt(MANTENANCE_MODE, settings.getMantenanceMode()).commit();
        editor.putString(SPLASH_IMAGE, settings.getSplashImage()).commit();
        editor.putString(DEFAULT_YOUTUBE_QUALITY, settings.getDefaultYoutubeQuality()).commit();
        editor.putInt(ALLOW_ADM_DOWNLOADS, settings.getAllowAdm()).commit();
        editor.putString(DEFAULT_DOWNLOADS_OPTION, settings.getDefaultDownloadsOptions()).commit();
        editor.putInt(STARTAPP_BANNER, settings.getStartappBanner()).commit();
        editor.putInt(STARTAPP_INTER ,settings.getStartappInterstitial()).commit();
        editor.putInt(VLC ,settings.getVlc()).commit();
        editor.putInt(OFFLINE_RESUME ,settings.getResumeOffline()).commit();
        editor.putInt(PINNED ,settings.getEnablePinned()).commit();
        editor.putInt(UPCOMING ,settings.getEnableUpcoming()).commit();
        editor.putInt(PREVIEWS ,settings.getEnablePreviews()).commit();
        editor.putString(USER_AGENT, settings.getUserAgent()).commit();
        editor.putInt(UNITYADS_BANNER, settings.getUnityadsBanner()).commit();
        editor.putInt(UNITYADS_INTER ,settings.getUnityadsInterstitial()).commit();
        editor.putInt(ENABLE_STREAMING ,settings.getStreaming()).commit();
        editor.putInt(ENABLE_BOTTOM_ADS_HOME ,settings.getEnableBannerBottom()).commit();
        editor.putString(USER_AGENT, settings.getUserAgent()).commit();
        editor.putInt(AD_FACEBOOK_NATIVE_ENABLE ,settings.getAdFaceAudienceNative()).commit();
        editor.putString(AD_FACEBOOK_NATIVE_UNIT_ID, settings.getAdUnitIdFacebookNativeAudience()).commit();
        editor.putString(DEFAULT_MEDIA_COVER, settings.getDefaultMediaPlaceholderPath()).commit();


    }

    public void deleteSettings(){
        editor.remove(APP_NAME).commit();
        editor.remove(AD_INTERSTITIAL).commit();
        editor.remove(AD_INTERSTITIAL_UNIT).commit();
        editor.remove(AD_BANNER).commit();
        editor.remove(AD_BANNER_UNIT).commit();
        editor.remove(TMDB).commit();
        editor.remove(PRIVACY_POLICY).commit();
        editor.remove(AUTOSUBSTITLES).commit();
        editor.remove(LATEST_VERSION).commit();
        editor.remove(UPDATE_TITLE).commit();
        editor.remove(RELEASE_NOTES).commit();
        editor.remove(URL).commit();
        editor.remove(PAYPAL_CLIENT_ID).commit();
        editor.remove(PAYPAL_AMOUNT).commit();
        editor.remove(FEATURED_HOME_NUMBERS).commit();
        editor.remove(APP_URL_ANDROID).commit();
        editor.remove(IMDB_COVER_PATH).commit();
        editor.remove(ADS_SETTINGS).commit();
        editor.remove(AD_INTERSTITIAL_FACEEBOK_ENABLE).commit();
        editor.remove(AD_INTERSTITIAL_FACEEBOK_UNIT_ID).commit();
        editor.remove(AD_BANNER_FACEEBOK_ENABLE).commit();
        editor.remove(AD_BANNER_FACEEBOK_UNIT_ID).commit();
        editor.remove(DOWNLOADS_PREMUIM_ONLY).commit();
        editor.remove(NEXT_EPISODE_TIMER).commit();
        editor.remove(FACEBOOK).commit();
        editor.remove(TWITTER).commit();
        editor.remove(INSTAGRAM).commit();
        editor.remove(YOUTUBE).commit();
        editor.remove(ENABLE_SERVER_DIALOG_SELECTION).commit();
    }

    public Settings getSettings(){

        Settings settings = new Settings();
        settings.setAppName(prefs.getString(APP_NAME, null));
        settings.setFacebookShowInterstitial(prefs.getInt(AD_FACEBOOK_INTERSTITIAL_SHOW, 0));
        settings.setAdShowInterstitial(prefs.getInt(AD_INTERSTITIAL_SHOW, 0));
        settings.setAdInterstitial(prefs.getInt(AD_INTERSTITIAL, 0));
        settings.setAdUnitIdInterstitial(prefs.getString(AD_INTERSTITIAL_UNIT, "ca-app-pub-3940256099942544/1033173712"));
        settings.setAdBanner(prefs.getInt(AD_BANNER, 0));
        settings.setAdUnitIdBanner(prefs.getString(AD_BANNER_UNIT, "ca-app-pub-3940256099942544/6300978111"));
        settings.setPurchaseKey(prefs.getString(PURCHASE_KEY, API_KEY));
        settings.setTmdbApiKey(prefs.getString(TMDB, null));
        settings.setPrivacyPolicy(prefs.getString(PRIVACY_POLICY, null));
        settings.setAutosubstitles(prefs.getInt(AUTOSUBSTITLES, 1));
        settings.setUrl(prefs.getString(URL, null));
        settings.setPaypalClientId(prefs.getString(PAYPAL_CLIENT_ID, null));
        settings.setPaypalAmount(prefs.getString(PAYPAL_AMOUNT, null));
        settings.setAppUrlAndroid(prefs.getString(APP_URL_ANDROID, null));
        settings.setFeaturedHomeNumbers(prefs.getInt(FEATURED_HOME_NUMBERS, 0));
        settings.setUpdateTitle(prefs.getString(UPDATE_TITLE, null));
        settings.setReleaseNotes(prefs.getString(RELEASE_NOTES, null));
        settings.setImdbCoverPath(prefs.getString(IMDB_COVER_PATH, null));
        settings.setAds(prefs.getInt(ADS_SETTINGS, 0));
        settings.setAnime(prefs.getInt(ANIME,0));
        settings.setAdFaceAudienceInterstitial(prefs.getInt(AD_INTERSTITIAL_FACEEBOK_ENABLE, 0));
        settings.setAdFaceAudienceBanner(prefs.getInt(AD_BANNER_FACEEBOK_ENABLE, 0));
        settings.setAdUnitIdFacebookBannerAudience(prefs.getString(AD_BANNER_FACEEBOK_UNIT_ID, null));
        settings.setAdUnitIdFacebookInterstitialAudience(prefs.getString(AD_INTERSTITIAL_FACEEBOK_UNIT_ID, null));
        settings.setDefaultRewardedNetworkAds(prefs.getString(DEFAULT_NETWORK, null));
        settings.setDefaultNetworkPlayer(prefs.getString(DEFAULT_NETWORK_PLAYER, null));
        settings.setStartappId(prefs.getString(STARTAPP_ID, null));
        settings.setAdUnitIdRewarded(prefs.getString(ADMOB_REWARD, "ca-app-pub-3940256099942544/5224354917"));
        settings.setAdUnitIdFacebookRewarded(prefs.getString(FACEBOOK_REWARD, null));
        settings.setUnityGameId(prefs.getString(UNITY_GAME_ID, "111111"));
        settings.setWachAdsToUnlock(prefs.getInt(WATCH_ADS_TO_UNLOCK, 0));
        settings.setWachAdsToUnlockPlayer(prefs.getInt(WATCH_ADS_TO_UNLOCK_PLAYER, 0));
        settings.setCustomMessage(prefs.getString(CUSTOM_MESSAGE, null));
        settings.setEnableCustomMessage(prefs.getInt(ENABLE_CUSTOM_MESSAGE, 0));
        settings.setStripePublishableKey(prefs.getString(STRIPE_PUBLISHABLE_KEY, null));
        settings.setStripeSecretKey(prefs.getString(STRIPE_SECRET_KEY, null));
        settings.setAdUnitIdAppodealRewarded(prefs.getString(APPODEAL_REWARD, null));
        settings.setDownloadPremuimOnly(prefs.getInt(DOWNLOADS_PREMUIM_ONLY, 0));
        settings.setNextEpisodeTimer(prefs.getInt(NEXT_EPISODE_TIMER, 0));
        settings.setAppodealBanner(prefs.getInt(APPODEAL_BANNER, 0));
        settings.setFacebookUrl(prefs.getString(FACEBOOK, null));
        settings.setTwitterUrl(prefs.getString(TWITTER, null));
        settings.setInstagramUrl(prefs.getString(INSTAGRAM, null));
        settings.setTelegram(prefs.getString(YOUTUBE, null));
        settings.setServerDialogSelection(prefs.getInt(ENABLE_SERVER_DIALOG_SELECTION, 0));
        settings.setApiKey(prefs.getString(API_KEY, API_KEY));
        settings.setAppodealShowInterstitial(prefs.getInt(AD_INTERSTITIAL_APPOBEAL_SHOW, 0));
        settings.setAppodealInterstitial(prefs.getInt(AD_INTERSTITIAL_APPOBEAL_ENABLE, 0));
        settings.setAdUnitIdNativeEnable(prefs.getInt(AD_NATIVEADS_ADMOB_ENABLE, 0));
        settings.setAdUnitIdNative(prefs.getString(AD_NATIVEADS_ADMOB_UNIT_ID, null));
        settings.setPaypalCurrency(prefs.getString(PAYPAL_CURRENCY, null));
        settings.setDefaultPayment(prefs.getString(DEFAULT_PAYMENT, null));
        settings.setEnableCustomBanner(prefs.getInt(ENABLE_CUSTOM_BANNER, 0));
        settings.setCustomBannerImage(prefs.getString(CUSTOM_BANNER_IMAGE, null));
        settings.setCustomBannerImageLink(prefs.getString(CUSTOM_BANNER_IMAGE_LINK, null));
        settings.setMantenanceModeMessage(prefs.getString(MANTENANCE_MESSAGE, null));
        settings.setMantenanceMode(prefs.getInt(MANTENANCE_MODE, 0));
        settings.setSplashImage(prefs.getString(SPLASH_IMAGE, null));
        settings.setDefaultYoutubeQuality(prefs.getString(DEFAULT_YOUTUBE_QUALITY, "720p"));
        settings.setAllowAdm(prefs.getInt(ALLOW_ADM_DOWNLOADS, 0));
        settings.setDefaultDownloadsOptions(prefs.getString(DEFAULT_DOWNLOADS_OPTION, "Free"));
        settings.setStartappBanner(prefs.getInt(STARTAPP_BANNER, 0));
        settings.setStartappInterstitial(prefs.getInt(STARTAPP_INTER, 0));
        settings.setVlc(prefs.getInt(VLC, 0));
        settings.setResumeOffline(prefs.getInt(OFFLINE_RESUME, 0));
        settings.setEnablePinned(prefs.getInt(PINNED, 0));
        settings.setEnableUpcoming(prefs.getInt(UPCOMING, 0));
        settings.setEnablePreviews(prefs.getInt(PREVIEWS, 0));
        settings.setUserAgent(prefs.getString(USER_AGENT, "EasyPlexPlayer"));
        settings.setUnityadsBanner(prefs.getInt(UNITYADS_BANNER, 0));
        settings.setUnityadsInterstitial(prefs.getInt(UNITYADS_BANNER, 0));
        settings.setStreaming(prefs.getInt(ENABLE_STREAMING, 1));
        settings.setEnableBannerBottom(prefs.getInt(ENABLE_BOTTOM_ADS_HOME, 0));
        settings.setAdFaceAudienceNative(prefs.getInt(AD_FACEBOOK_NATIVE_ENABLE, 0));
        settings.setAdUnitIdFacebookNativeAudience(prefs.getString(AD_FACEBOOK_NATIVE_UNIT_ID, null));
        settings.setDefaultMediaPlaceholderPath(prefs.getString(DEFAULT_MEDIA_COVER, null));
        return settings;
    }
}

