package com.onion.android.app.constants;

import android.util.Base64;

import com.onion.android.BuildConfig;

import java.nio.charset.StandardCharsets;

/**
 * Plex Application CONSTANTS.
 */
public abstract class PlexConstants{

    public static final String AUTHORISATION_BEARER_STRING = decodeAuthorisationBearer();
    public static final String AUTHORISATION_BEARER = "ZWFzeXBsZXhfZXhhbXBsZV9hdXRob3Jpc2F0aW9uX2JlYXJlcg==";
    // this is not your laravel api , this key will be used to authorise all api calls inside your app ( this key must be the same as API_KEY value in your .env file otherwise you will get
    //  "Invalid access key" error in your api calls )
    // for ex http://192.168.1.130/public/api/movies/featured/p2lbgWkFrykA4QyUmpHihzmc5BNzIABq
    public static final String API_KEY = "p2lbgWkFrykA4QyUmpHihzmc5BNzIABq";
    // this is your laravel main api ( Change this encoded value to Your Server Encoded URL ( BASE64 ) )
    // for ex http://192.168.1.130/public/api/          !note : url must be ending with /
    public static final String SERVER_ENCODED = "aHR0cHM6Ly9lYXN5cGxleC55b2JkZXYubGl2ZS9wMmxiZ1drRnJ5a0Ev";
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

    // Don't Change
    private static String decodeAuthorisationBearer(){
        byte[] valueDecoded;
        valueDecoded = Base64.decode(AUTHORISATION_BEARER.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
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

    public static final String MOVIE ="MOVIE";
    public static final String SERIE ="SERIE";
    public static final String EMPTY_URL ="about:blank";
    public static final String YOUTUBE_WATCH_BASE_URL = "https://www.youtube.com/watch?v=";
    public static final String YOUTUBE_SEARCH_BASE_URL = " https://www.youtube.com/results?search_query=";
    public static final String PAYPAL_CLIENT_ID = "clientid";
    public static final String PAYMENT = "payment";
    public static final String SUBS_SIZE = "subs_size";
    public static final String SUBS_BACKGROUND = "subs_background";
    public static final String PLAYER_ASPECT_RATIO = "player_aspect_ratio";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String ARG_MOVIE = "movie";
    public static final String ARG_MOVIE_HISTORY = "history";
    public static final String ARG_PAYMENT = "payment";
    public static final String SUBSCRIPTIONS = "You Subscription has ended !";
    public static final String EXTERNAL_ID = "external_id";
    public static final String MOVIE_LINK = "link";


    // Buttons Switch Constants
    public static final String WIFI_CHECK = "wifi_check";
    public static final String SWITCH_PUSH_NOTIFICATION = "switch_push_notification";
    public static final String AUTO_PLAY = "autoplay_check";
    public static final String EXTENTIONS = "enable_extentions";


    // Auth Constants
    public static final String PREMUIM = "premuim";
    public static final String PREMUIM_MANUAL = "premuim_manual";
    public static final String AUTH_NAME = "name";
    public static final String AUTH_ID = "id";
    public static final String AUTH_EXPIRED_DATE = "expired_in";
    public static final String ERROR = "Error";
    public static final String PURCHASE_KEY = " ";


    // Ads Constants
    public static final String ADS_LINK = "link";
    public static final String ADS_CLICKTHROUGHURL = "clickThroughUrl";


    // Admob



    // Remote
    public static final String APPLICATION_JSON = "application/json";
    public static final String ACCEPT = "Accept";
    public static final String AUTHORISATION = "Authorization";

    // Substitles

    public static final String ZIP_FILE_NAME = "1.srt";
    public static final String ZIP_FILE_NAME2 = "1.vtt";
    public static final String ZIP_FILE_NAME3 = "1.ssa";
    public static final String SUBSTITLE_LOCATION = "file:///storage/emulated/0/Android/data/";
    public static final String SUBSTITLE_SUB_FILENAME_ZIP = "/subs.zip";



    // Settings Constants
    public static final String APP_NAME = "app_name";
    public static final String AD_FACEBOOK_INTERSTITIAL_SHOW = "facebook_show_interstitial";
    public static final String AD_FACEBOOK_NATIVE_ENABLE = "ad_face_audience_native";
    public static final String AD_FACEBOOK_NATIVE_UNIT_ID = "ad_unit_id_facebook_native_audience";
    public static final String AD_INTERSTITIAL_SHOW = "ad_show_interstitial";
    public static final String AD_INTERSTITIAL = "ad_interstitial";
    public static final String AD_INTERSTITIAL_UNIT = "ad_unit_id_interstitial";
    public static final String AD_BANNER = "ad_banner";
    public static final String AD_BANNER_UNIT = "ad_unit_id_banner";
    public static final String TMDB = "tmdb_api_key";
    public static final String APP_URL_ANDROID = "app_url_android";
    public static final String PRIVACY_POLICY = "privacy_policy";
    public static final String LATEST_VERSION = "latestVersion";
    public static final String UPDATE_TITLE = "update_title";
    public static final String RELEASE_NOTES = "releaseNotes";
    public static final String PAYPAL_AMOUNT = "paypal_amount";
    public static final String FEATURED_HOME_NUMBERS = "featured_home_numbers";
    public static final String IMDB_COVER_PATH = "imdb_cover_path";
    public static final String AUTOSUBSTITLES = "autosubstitles";
    public static final String ANIME = "anime";
    public static final String ENABLE_STREAMING = "streaming";
    public static final String ADS_SETTINGS = "ads";
    public static final String AD_INTERSTITIAL_FACEEBOK_ENABLE = "ad_face_audience_interstitial";
    public static final String AD_INTERSTITIAL_FACEEBOK_UNIT_ID = "ad_unit_id_facebook_interstitial_audience";
    public static final String AD_INTERSTITIAL_APPODEAL_UNIT_ID = "ad_unit_id__appodeal_rewarded";
    public static final String AD_BANNER_APPODEAL_UNIT_ID = "ad_unit_id_appodeal_banner_audience";
    public static final String AD_NATIVEADS_ADMOB_UNIT_ID = "admob_native_ads";
    public static final String AD_NATIVEADS_ADMOB_ENABLE = "ad_unit_id_native_enable";
    public static final String PAYPAL_CURRENCY = "paypal_currency";
    public static final String AD_INTERSTITIAL_APPOBEAL_ENABLE = "ad_unit_id_native";
    public static final String AD_INTERSTITIAL_APPOBEAL_SHOW = "appodeal_show_interstitial";
    public static final String DEFAULT_PAYMENT = "default_payment";
    public static final String PREFS2 = BuildConfig.APP_STARUP;
    public static final String AD_BANNER_FACEEBOK_ENABLE = "ad_face_audience_banner";
    public static final String AD_BANNER_FACEEBOK_UNIT_ID = "ad_unit_id_facebook_banner_audience";
    public static final String DEFAULT_NETWORK = "default_network";
    public static final String DEFAULT_NETWORK_PLAYER = "default_network_player";
    public static final String STARTAPP_ID = "startapp_id";
    public static final String ADMOB_REWARD = "ad_unit_id_rewarded";
    public static final String FACEBOOK_REWARD = "ad_unit_id__facebook_rewarded";
    public static final String UNITY_GAME_ID = "unity_game_id";
    public static final String WATCH_ADS_TO_UNLOCK = "wach_ads_to_unlock";
    public static final String WATCH_ADS_TO_UNLOCK_PLAYER = "wach_ads_to_unlock_player";
    public static final String CUSTOM_MESSAGE = "custom_message";
    public static final String ENABLE_CUSTOM_MESSAGE = "enable_custom_message";
    public static final String STRIPE_PUBLISHABLE_KEY = "stripe_publishable_key";
    public static final String STRIPE_SECRET_KEY = "stripe_secret_key";
    public static final String APPODEAL_REWARD = "ad_unit_id__appodeal_rewarded";
    public static final String APPODEAL_BANNER = "appodeal_banner";
    public static final String DOWNLOADS_PREMUIM_ONLY = "download_premuim_only";
    public static final String NEXT_EPISODE_TIMER = "next_episode_timer";
    public static final String FACEBOOK = "facebook";
    public static final String TWITTER = "twitter";
    public static final String INSTAGRAM = "instagram";
    public static final String YOUTUBE = "youtube";
    public static final String ENABLE_SERVER_DIALOG_SELECTION = "server_dialog_selection";
    public static final String ENABLE_CUSTOM_BANNER = "enable_custom_banner";
    public static final String CUSTOM_BANNER_IMAGE = "custom_banner_image";
    public static final String CUSTOM_BANNER_IMAGE_LINK = "custom_banner_image_link";
    public static final String MANTENANCE_MESSAGE = "mantenance_mode_message";
    public static final String MANTENANCE_MODE = "mantenance_mode";
    public static final String SPLASH_IMAGE = "splash_image";
    public static final String DEFAULT_YOUTUBE_QUALITY = "default_youtube_quality";
    public static final String ALLOW_ADM_DOWNLOADS = "allow_adm";
    public static final String DEFAULT_DOWNLOADS_OPTION = "default_downloads_options";
    public static final String STARTAPP_BANNER = "startapp_banner";
    public static final String STARTAPP_INTER = "startapp_interstitial";
    public static final String VLC = "vlc";
    public static final String OFFLINE_RESUME = "resume_offline";
    public static final String PINNED = "enable_pinned";
    public static final String UPCOMING = "enable_upcoming";
    public static final String PREVIEWS = "enable_previews";
    public static final String USER_AGENT = "user_agent";
    public static final String UNITYADS_BANNER = "unityads_banner";
    public static final String UNITYADS_INTER = "unityads_interstitial";
    public static final String ENABLE_BOTTOM_ADS_HOME = "enable_banner_bottom";
    public static final String DEFAULT_MEDIA_COVER = "default_media_placeholder_path";
    public static final String DEFAULT_DOWNLOAD_DIRECTORY = "cache";


    // Status
    public static final String CODE = "code";


    // TV
    public static final String SPECIALS = "Specials";
    public static final String SEASONS = "剧集: ";

    // Player Constants
    public static final int CUSTOM_SEEK_CONTROL_STATE = 2; // Every time long press left/right will enter this state
    public static final int EDIT_CUSTOM_SEEK_CONTROL_STATE = 3; // After long press left/right will enter this state
    public static final long DEFAULT_FREQUENCY = 1000;
    public static final String FSMPLAYER_TESTING = "FSM_LOGGING";
    public static final String UPNEXT = "Up Next in : ";
    public static final String EP = "EP";
    public static final String S0 = "S0";
    public static final String E = "E";
    public static final String STREAMING = "streaming";
    public static final String BRX = "brx";


    // Shared Preferences Constants
    public static final String PREF_FILE = "Preferences";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
}