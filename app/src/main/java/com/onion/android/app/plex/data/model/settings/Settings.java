package com.onion.android.app.plex.data.model.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private String apiKey;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("app_name")
    @Expose
    private String appName;

    @SerializedName("paypal_client_id")
    @Expose
    private String paypalClientId;

    @SerializedName("paypal_amount")
    @Expose
    private String paypalAmount;

    @SerializedName("privacy_policy")
    @Expose
    private String privacyPolicy;


    @SerializedName("tmdb_api_key")
    @Expose
    private String tmdbApiKey;


    @SerializedName("purchase_key")
    @Expose
    private String purchaseKey;


    @SerializedName("stripe_publishable_key")
    @Expose
    private String stripePublishableKey;


    @SerializedName("stripe_secret_key")
    @Expose
    private String stripeSecretKey;


    public String getDefaultMediaPlaceholderPath() {
        return defaultMediaPlaceholderPath;
    }

    public void setDefaultMediaPlaceholderPath(String defaultMediaPlaceholderPath) {
        this.defaultMediaPlaceholderPath = defaultMediaPlaceholderPath;
    }

    @SerializedName("default_media_placeholder_path")
    @Expose
    private String defaultMediaPlaceholderPath;

    @SerializedName("app_url_android")
    @Expose
    private String appUrlAndroid;


    @SerializedName("facebook_url")
    @Expose
    private String facebookUrl;

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    @SerializedName("twitter_url")
    @Expose
    private String twitterUrl;


    @SerializedName("instagram_url")
    @Expose
    private String instagramUrl;


    @SerializedName("telegram_url")
    @Expose
    private String telegram;


    @SerializedName("autosubstitles")
    @Expose
    private int autosubstitles;


    public int getDownloadPremuimOnly() {
        return downloadPremuimOnly;
    }

    public void setDownloadPremuimOnly(int downloadPremuimOnly) {
        this.downloadPremuimOnly = downloadPremuimOnly;
    }

    public int getNextEpisodeTimer() {
        return nextEpisodeTimer;
    }

    public void setNextEpisodeTimer(int nextEpisodeTimer) {
        this.nextEpisodeTimer = nextEpisodeTimer;
    }

    @SerializedName("next_episode_timer")
    @Expose
    private int nextEpisodeTimer;

    public int getAllowAdm() {
        return allowAdm;
    }

    public void setAllowAdm(int allowAdm) {
        this.allowAdm = allowAdm;
    }



    public int getVlc() {
        return vlc;
    }

    public void setVlc(int vlc) {
        this.vlc = vlc;
    }

    @SerializedName("enable_vlc")
    @Expose
    private int vlc;

    @SerializedName("startapp_interstitial")
    @Expose
    private int startappInterstitial;

    public int getStartappInterstitial() {
        return startappInterstitial;
    }

    public void setStartappInterstitial(int startappInterstitial) {
        this.startappInterstitial = startappInterstitial;
    }

    public int getStartappBanner() {
        return startappBanner;
    }

    public void setStartappBanner(int startappBanner) {
        this.startappBanner = startappBanner;
    }

    public int getUnityadsInterstitial() {
        return unityadsInterstitial;
    }

    public void setUnityadsInterstitial(int unityadsInterstitial) {
        this.unityadsInterstitial = unityadsInterstitial;
    }

    public int getUnityadsBanner() {
        return unityadsBanner;
    }

    public void setUnityadsBanner(int unityadsBanner) {
        this.unityadsBanner = unityadsBanner;
    }


    public int getStreaming() {
        return streaming;
    }

    public void setStreaming(int streaming) {
        this.streaming = streaming;
    }


    public int getEnableBannerBottom() {
        return enableBannerBottom;
    }

    public void setEnableBannerBottom(int enableBannerBottom) {
        this.enableBannerBottom = enableBannerBottom;
    }

    @SerializedName("enable_banner_bottom")
    @Expose
    private int enableBannerBottom;

    @SerializedName("streaming")
    @Expose
    private int streaming;

    @SerializedName("unityads_interstitial")
    @Expose
    private int unityadsInterstitial;

    @SerializedName("unityads_banner")
    @Expose
    private int unityadsBanner;

    @SerializedName("startapp_banner")
    @Expose
    private int startappBanner;

    public int getResumeOffline() {
        return resumeOffline;
    }

    public void setResumeOffline(int resumeOffline) {
        this.resumeOffline = resumeOffline;
    }

    @SerializedName("resume_offline")
    @Expose
    private int resumeOffline;

    @SerializedName("allow_adm")
    @Expose
    private int allowAdm;

    @SerializedName("download_premuim_only")
    @Expose
    private int downloadPremuimOnly;


    @SerializedName("ads_player")
    @Expose
    private int ads;

    @SerializedName("anime")
    @Expose
    private Integer anime;

    @SerializedName("ad_app_id")
    @Expose
    private String adAppId;


    @SerializedName("latestVersion")
    @Expose
    private String latestVersion;


    @SerializedName("update_title")
    @Expose
    private String updateTitle;


    @SerializedName("releaseNotes")
    @Expose
    private String releaseNotes;


    @SerializedName("url")
    @Expose
    private String url;



    @SerializedName("imdb_cover_path")
    @Expose
    private String imdbCoverPath;




    @SerializedName("custom_message")
    @Expose
    private String customMessage;


    @SerializedName("wach_ads_to_unlock")
    @Expose
    private int wachAdsToUnlock;

    public int getWachAdsToUnlockPlayer() {
        return wachAdsToUnlockPlayer;
    }

    public void setWachAdsToUnlockPlayer(int wachAdsToUnlockPlayer) {
        this.wachAdsToUnlockPlayer = wachAdsToUnlockPlayer;
    }

    @SerializedName("wach_ads_to_unlock_player")
    @Expose
    private int wachAdsToUnlockPlayer;


    @SerializedName("startapp_id")
    @Expose
    private String startappId;




    @SerializedName("ad_unit_id_rewarded")
    @Expose
    private String adUnitIdRewarded;


    @SerializedName("ad_unit_id__facebook_rewarded")
    @Expose
    private String adUnitIdFacebookRewarded;

    public int getAdFaceAudienceNative() {
        return adFaceAudienceNative;
    }

    public void setAdFaceAudienceNative(int adfaceaudiencenative) {
        this.adFaceAudienceNative = adfaceaudiencenative;
    }

    @SerializedName("ad_face_audience_native")
    @Expose
    private int adFaceAudienceNative;

    public String getAdUnitIdFacebookNativeAudience() {
        return adUnitIdFacebookNativeAudience;
    }

    public void setAdUnitIdFacebookNativeAudience(String adunitidfacebooknativeaudience) {
        this.adUnitIdFacebookNativeAudience = adunitidfacebooknativeaudience;
    }

    @SerializedName("ad_unit_id_facebook_native_audience")
    @Expose
    private String adUnitIdFacebookNativeAudience;

    @SerializedName("unity_game_id")
    @Expose
    private String unityGameId;


    public String getDefaultNetworkPlayer() {
        return defaultNetworkPlayer;
    }

    public void setDefaultNetworkPlayer(String defaultNetworkPlayer) {
        this.defaultNetworkPlayer = defaultNetworkPlayer;
    }

    @SerializedName("default_network_player")
    @Expose
    private String defaultNetworkPlayer;


    @SerializedName("default_network")
    @Expose
    private String defaultRewardedNetworkAds;

    public int getServerDialogSelection() {
        return serverDialogSelection;
    }

    public void setServerDialogSelection(int serverDialogSelection) {
        this.serverDialogSelection = serverDialogSelection;
    }

    public String getDefaultPayment() {
        return defaultPayment;
    }

    public void setDefaultPayment(String defaultpayment) {
        this.defaultPayment = defaultpayment;
    }

    @SerializedName("default_payment")
    @Expose
    private String defaultPayment;



    @SerializedName("server_dialog_selection")
    @Expose
    private int serverDialogSelection;



    @SerializedName("ad_unit_id__appodeal_rewarded")
    @Expose
    private String adUnitIdAppodealRewarded;

    @SerializedName("facebook_show_interstitial")
    @Expose
    private int facebookShowInterstitial;

    @SerializedName("ad_show_interstitial")
    @Expose
    private int adShowInterstitial;

    @SerializedName("ad_interstitial")
    @Expose
    private int adInterstitial;


    public int getAppodealInterstitial() {
        return appodealInterstitial;
    }

    public int getAppodealBanner() {
        return appodealBanner;
    }

    public void setAppodealBanner(int appodealBanner) {
        this.appodealBanner = appodealBanner;
    }

    public void setAppodealInterstitial(int appodealInterstitial) {
        this.appodealInterstitial = appodealInterstitial;
    }

    @SerializedName("appodeal_interstitial")
    @Expose
    private int appodealInterstitial;


    @SerializedName("appodeal_banner")
    @Expose
    private int appodealBanner;


    public int getEnablePinned() {
        return enablePinned;
    }

    public void setEnablePinned(int enablePinned) {
        this.enablePinned = enablePinned;
    }

    public int getEnableUpcoming() {
        return enableUpcoming;
    }

    public void setEnableUpcoming(int enableUpcoming) {
        this.enableUpcoming = enableUpcoming;
    }

    @SerializedName("enable_upcoming")
    @Expose
    private int enableUpcoming;

    @SerializedName("enable_pinned")
    @Expose
    private int enablePinned;

    public int getEnablePreviews() {
        return enablePreviews;
    }

    public void setEnablePreviews(int enablePreviews) {
        this.enablePreviews = enablePreviews;
    }

    @SerializedName("enable_previews")
    @Expose
    private int enablePreviews;


    public int getAppodealShowInterstitial() {
        return appodealShowInterstitial;
    }

    public void setAppodealShowInterstitial(int appodealShowInterstitial) {
        this.appodealShowInterstitial = appodealShowInterstitial;
    }

    @SerializedName("appodeal_show_interstitial")
    @Expose
    private int appodealShowInterstitial;



    @SerializedName("ad_banner")
    @Expose
    private int adBanner;


    public int getAdUnitIdNativeEnable() {
        return adUnitIdNativeEnable;
    }

    public void setAdUnitIdNativeEnable(int adUnitIdNativeEnable) {
        this.adUnitIdNativeEnable = adUnitIdNativeEnable;
    }

    @SerializedName("ad_unit_id_native_enable")
    @Expose
    private int adUnitIdNativeEnable;


    @SerializedName("ad_face_audience_interstitial")
    @Expose
    private int adFaceAudienceInterstitial;

    @SerializedName("ad_face_audience_banner")
    @Expose
    private int adFaceAudienceBanner;

    public String getAdUnitIdNative() {
        return adUnitIdNative;
    }

    public void setAdUnitIdNative(String adUnitIdNative) {
        this.adUnitIdNative = adUnitIdNative;
    }


    public String getPaypalCurrency() {
        return paypalCurrency;
    }

    public void setPaypalCurrency(String paypalCurrency) {
        this.paypalCurrency = paypalCurrency;
    }

    @SerializedName("paypal_currency")
    @Expose
    private String paypalCurrency;

    @SerializedName("ad_unit_id_native")
    @Expose
    private String adUnitIdNative;

    @SerializedName("enable_custom_message")
    @Expose
    private int enableCustomMessage;


    public int getEnableCustomBanner() {
        return enableCustomBanner;
    }

    public void setEnableCustomBanner(int enableCustomBanner) {
        this.enableCustomBanner = enableCustomBanner;
    }

    public String getCustomBannerImage() {
        return customBannerImage;
    }

    public void setCustomBannerImage(String customBannerImage) {
        this.customBannerImage = customBannerImage;
    }

    @SerializedName("enable_custom_banner")
    @Expose
    private int enableCustomBanner;

    public String getCustomBannerImageLink() {
        return customBannerImageLink;
    }

    public void setCustomBannerImageLink(String customBannerImageLink) {
        this.customBannerImageLink = customBannerImageLink;
    }

    @SerializedName("custom_banner_image_link")
    @Expose
    private String customBannerImageLink;


    @SerializedName("custom_banner_image")
    @Expose
    private String customBannerImage;


    @SerializedName("ad_unit_id_facebook_interstitial_audience")
    @Expose
    private String adUnitIdFacebookInterstitialAudience;

    public int getMantenanceMode() {
        return mantenanceMode;
    }

    public void setMantenanceMode(int mantenanceMode) {
        this.mantenanceMode = mantenanceMode;
    }

    public String getMantenanceModeMessage() {
        return mantenanceModeMessage;
    }

    public void setMantenanceModeMessage(String mantenanceModeMessage) {
        this.mantenanceModeMessage = mantenanceModeMessage;
    }

    @SerializedName("mantenance_mode")
    @Expose
    private int mantenanceMode;

    public String getSplashImage() {
        return splashImage;
    }

    public void setSplashImage(String splashImage) {
        this.splashImage = splashImage;
    }

    @SerializedName("mantenance_mode_message")
    @Expose
    private String mantenanceModeMessage;


    public String getDefaultYoutubeQuality() {
        return defaultYoutubeQuality;
    }

    public void setDefaultYoutubeQuality(String defaultYoutubeQuality) {
        this.defaultYoutubeQuality = defaultYoutubeQuality;
    }

    @SerializedName("default_youtube_quality")
    @Expose
    private String defaultYoutubeQuality;

    public String getDefaultDownloadsOptions() {
        return defaultDownloadsOptions;
    }

    public void setDefaultDownloadsOptions(String defaultDownloadsOptions) {
        this.defaultDownloadsOptions = defaultDownloadsOptions;
    }



    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @SerializedName("user_agent")
    @Expose
    private String userAgent;

    @SerializedName("default_downloads_options")
    @Expose
    private String defaultDownloadsOptions;

    @SerializedName("splash_image")
    @Expose
    private String splashImage;


    public String getAdUnitIdAppodealBannerAudience() {
        return adUnitIdAppodealBannerAudience;
    }

    public void setAdUnitIdAppodealBannerAudience(String adUnitIdAppodealBannerAudience) {
        this.adUnitIdAppodealBannerAudience = adUnitIdAppodealBannerAudience;
    }

    public String getAdUnitIdAppodealInterstitialAudience() {
        return adUnitIdAppodealInterstitialAudience;
    }

    public void setAdUnitIdAppodealInterstitialAudience(String adUnitIdAppodealInterstitialAudience) {
        this.adUnitIdAppodealInterstitialAudience = adUnitIdAppodealInterstitialAudience;
    }




    @SerializedName("ad_unit_id_appodeal_banner_audience")
    @Expose
    private String adUnitIdAppodealBannerAudience;

    @SerializedName("ad_unit_id_appodeal_interstitial_audience")
    @Expose
    private String adUnitIdAppodealInterstitialAudience;


    @SerializedName("ad_unit_id_facebook_banner_audience")
    @Expose
    private String adUnitIdFacebookBannerAudience;



    @SerializedName("ad_unit_id_banner")
    @Expose
    private String adUnitIdBanner;


    @SerializedName("ad_unit_id_interstitial")
    @Expose
    private String adUnitIdInterstitial;

    public String getPurchaseKey() {
        return purchaseKey;
    }

    public String getCurrentKey() {
        return currentKey;
    }

    public void setCurrentKey(String currentkey) {
        this.currentKey = currentkey;
    }

    @SerializedName("current_key")
    @Expose
    private String currentKey;


    @SerializedName("featured_home_numbers")
    @Expose
    private int featuredHomeNumbers;


    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;


    public String getAdUnitIdAppodealRewarded() {
        return adUnitIdAppodealRewarded;
    }

    public void setAdUnitIdAppodealRewarded(String adUnitIdAppodealRewarded) {
        this.adUnitIdAppodealRewarded = adUnitIdAppodealRewarded;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTmdbApiKey() {
        return tmdbApiKey;
    }

    public void setTmdbApiKey(String tmdbApiKey) {
        this.tmdbApiKey = tmdbApiKey;
    }

    public String getcode() {
        return purchaseKey;
    }

    public void setPurchaseKey(String purchaseKey) {
        this.purchaseKey = purchaseKey;
    }

    public String getAppUrlAndroid() {
        return appUrlAndroid;
    }

    public void setAppUrlAndroid(String appUrlAndroid) {
        this.appUrlAndroid = appUrlAndroid;
    }


    public Integer getAnime() {
        return anime;
    }

    public void setAnime(Integer anime) {
        this.anime = anime;
    }

    public int getAds() {
        return ads;
    }

    public void setAds(int ads) {
        this.ads = ads;
    }

    public String getAdAppId() {
        return adAppId;
    }

    public void setAdAppId(String adAppId) {
        this.adAppId = adAppId;
    }

    public int getAdInterstitial() {
        return adInterstitial;
    }

    public void setAdInterstitial(int adInterstitial) {
        this.adInterstitial = adInterstitial;
    }

    public String getAdUnitIdInterstitial() {
        return adUnitIdInterstitial;
    }

    public void setAdUnitIdInterstitial(String adUnitIdInterstitial) {
        this.adUnitIdInterstitial = adUnitIdInterstitial;
    }


    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }


    public int getEnableCustomMessage() {
        return enableCustomMessage;
    }

    public void setEnableCustomMessage(int enableCustomMessage) {
        this.enableCustomMessage = enableCustomMessage;
    }



    public String getAdUnitIdBanner() {
        return adUnitIdBanner;
    }

    public void setAdUnitIdBanner(String adUnitIdBanner) {
        this.adUnitIdBanner = adUnitIdBanner;
    }


    public int getAdBanner() {
        return adBanner;
    }

    public void setAdBanner(int adBanner) {
        this.adBanner = adBanner;
    }


    public int getFacebookShowInterstitial() {
        return facebookShowInterstitial;
    }

    public void setFacebookShowInterstitial(int facebookShowInterstitial) {
        this.facebookShowInterstitial = facebookShowInterstitial;
    }


    public int getAdShowInterstitial() {
        return adShowInterstitial;
    }

    public void setAdShowInterstitial(int adShowInterstitial) {
        this.adShowInterstitial = adShowInterstitial;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }



    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }


    public String getPaypalClientId() {
        return paypalClientId;
    }

    public void setPaypalClientId(String paypalClientId) {
        this.paypalClientId = paypalClientId;
    }


    public String getStripePublishableKey() {
        return stripePublishableKey;
    }

    public void setStripePublishableKey(String stripePublishableKey) {
        this.stripePublishableKey = stripePublishableKey;
    }

    public String getStripeSecretKey() {
        return stripeSecretKey;
    }

    public void setStripeSecretKey(String stripeSecretKey) {
        this.stripeSecretKey = stripeSecretKey;
    }

    public int getAutosubstitles() {
        return autosubstitles;
    }

    public void setAutosubstitles(int autosubstitles) {
        this.autosubstitles = autosubstitles;
    }


    public String getPaypalAmount() {
        return paypalAmount;
    }

    public void setPaypalAmount(String paypalAmount) {
        this.paypalAmount = paypalAmount;
    }


    public String getUpdateTitle() {
        return updateTitle;
    }

    public void setUpdateTitle(String updateTitle) {
        this.updateTitle = updateTitle;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
    }


    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }


    public String getImdbCoverPath() {
        return imdbCoverPath;
    }

    public void setImdbCoverPath(String imdbCoverPath) {
        this.imdbCoverPath = imdbCoverPath;
    }


    public int getFeaturedHomeNumbers() {
        return featuredHomeNumbers;
    }

    public void setFeaturedHomeNumbers(int featuredHomeNumbers) {
        this.featuredHomeNumbers = featuredHomeNumbers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAdFaceAudienceInterstitial() {
        return adFaceAudienceInterstitial;
    }

    public void setAdFaceAudienceInterstitial(int adFaceAudienceInterstitial) {
        this.adFaceAudienceInterstitial = adFaceAudienceInterstitial;
    }

    public int getAdFaceAudienceBanner() {
        return adFaceAudienceBanner;
    }

    public void setAdFaceAudienceBanner(int adFaceAudienceBanner) {
        this.adFaceAudienceBanner = adFaceAudienceBanner;
    }

    public String getAdUnitIdFacebookInterstitialAudience() {
        return adUnitIdFacebookInterstitialAudience;
    }

    public void setAdUnitIdFacebookInterstitialAudience(String adUnitIdFacebookInterstitialAudience) {
        this.adUnitIdFacebookInterstitialAudience = adUnitIdFacebookInterstitialAudience;
    }

    public String getAdUnitIdFacebookBannerAudience() {
        return adUnitIdFacebookBannerAudience;
    }

    public void setAdUnitIdFacebookBannerAudience(String adUnitIdFacebookBannerAudience) {
        this.adUnitIdFacebookBannerAudience = adUnitIdFacebookBannerAudience;
    }


    public String getDefaultRewardedNetworkAds() {
        return defaultRewardedNetworkAds;
    }

    public void setDefaultRewardedNetworkAds(String defaultRewardedNetworkAds) {
        this.defaultRewardedNetworkAds = defaultRewardedNetworkAds;
    }


    public int getWachAdsToUnlock() {
        return wachAdsToUnlock;
    }

    public void setWachAdsToUnlock(int wachAdsToUnlock) {
        this.wachAdsToUnlock = wachAdsToUnlock;
    }


    public String getStartappId() {
        return startappId;
    }

    public void setStartappId(String startappId) {
        this.startappId = startappId;
    }

    public String getAdUnitIdRewarded() {
        return adUnitIdRewarded;
    }

    public void setAdUnitIdRewarded(String adUnitIdRewarded) {
        this.adUnitIdRewarded = adUnitIdRewarded;
    }

    public String getAdUnitIdFacebookRewarded() {
        return adUnitIdFacebookRewarded;
    }

    public void setAdUnitIdFacebookRewarded(String adUnitIdFacebookRewarded) {
        this.adUnitIdFacebookRewarded = adUnitIdFacebookRewarded;
    }

    public String getUnityGameId() {
        return unityGameId;
    }

    public void setUnityGameId(String unityGameId) {
        this.unityGameId = unityGameId;
    }


}