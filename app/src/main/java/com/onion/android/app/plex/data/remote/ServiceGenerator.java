package com.onion.android.app.plex.data.remote;

import static com.onion.android.app.constants.PlexConstants.ACCEPT;
import static com.onion.android.app.constants.PlexConstants.APPLICATION_JSON;
import static com.onion.android.app.constants.PlexConstants.AUTHORISATION_BEARER_STRING;
import static com.onion.android.app.constants.PlexConstants.CACHE_CONTROL;
import static com.onion.android.app.constants.PlexConstants.IMDB_BASE_URL;
import static com.onion.android.app.constants.PlexConstants.PREFS2;
import static com.onion.android.app.constants.PlexConstants.SERVER_BASE_URL;
import static com.onion.android.app.constants.PlexConstants.SERVER_OPENSUBS_URL;
import static com.onion.android.app.utils.Tools.USER_AGENT;

import android.util.Base64;

import androidx.annotation.NonNull;

import com.onion.android.App;
import com.onion.android.app.plex.manager.TokenManager;
import com.onion.android.app.plex.util.MediaHelper;
import com.onion.android.app.utils.Tools;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


/**
 * A class that defines how Retrofit 2 & OkHttp should communicate with an API.
 * Interceptors, Caching, Logging
 *
 * @author Yobex.
 */
@Singleton
public class ServiceGenerator {

    // 叉，原来OkHttp缓存报错是这里的原因，缓存目录有问题
    private static final File httpCacheDirectory = new File(App.getContext().getCacheDir(), "responses");
    private static final int CACHE_SIZE = 30 * 1024 * 1024; // 10 MB
    // 因为kotlin的Cache 类，所以识别不了,但可以正常調用
    private static final Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);


    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new ResponseCacheInterceptor())
            .addInterceptor(new OfflineResponseCacheInterceptor())
            .addInterceptor(new ErrorHandlerInterceptor())
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .cache(cache);

    private static final OkHttpClient client = buildClient();


    private static OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request();
            Request.Builder addHeader = request.newBuilder().addHeader(ACCEPT, APPLICATION_JSON);
            request = addHeader.build();
            return chain.proceed(request);
        });
        return builder.build();
    }

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());


    private static final Retrofit.Builder builderApp = new Retrofit.Builder()
            .baseUrl(PREFS2)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());


    private static final Retrofit.Builder builderImdb = new Retrofit.Builder()
            .baseUrl(IMDB_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());


    private static final Retrofit.Builder builderOpenSubs = new Retrofit.Builder()
            .baseUrl(SERVER_OPENSUBS_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());


    private static final Retrofit.Builder builderStatus = new Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = builder.build();
    private static final Retrofit retrofitStatus = builderStatus.build();
    private static final Retrofit retrofitApp = builderApp.build();
    private static final Retrofit retrofit2 = builderImdb.build();
    private static final Retrofit retrofit3 = builderOpenSubs.build();
    private static Retrofit retrofit5 = null;

    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging)
                    .addNetworkInterceptor(chain -> {
                        Request request = chain.request();
                        Request.Builder newBuilder = request.newBuilder();
                        newBuilder.addHeader(ACCEPT, APPLICATION_JSON);
                        newBuilder.addHeader("packagename", "com.easyplexdemoapp");
                        newBuilder.addHeader("Authorization", "Bearer " + AUTHORISATION_BEARER_STRING);
                        newBuilder.addHeader(USER_AGENT, MediaHelper.userAgent());
                        request = newBuilder.build();
                        return chain.proceed(request);
                    });
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }


    @Named("app")
    public static <T> T createServiceApp(Class<T> service) {
        OkHttpClient newClient = client.newBuilder().addInterceptor(chain -> {
            Request request = chain.request();

            Request.Builder newBuilder = request.newBuilder();
            newBuilder.addHeader(ACCEPT, APPLICATION_JSON);
            request = newBuilder.build();
            return chain.proceed(request);
        }).build();

        Retrofit newRetrofit = retrofitApp.newBuilder().client(newClient).build();
        return newRetrofit.create(service);

    }


    @Named("status")
    public static <T> T createServiceWithStatus(Class<T> service) {
        OkHttpClient newClient = client.newBuilder().addInterceptor(chain -> {
            Request request = chain.request();
            Request.Builder newBuilder = request.newBuilder();
            newBuilder.addHeader("Authorization", Arrays.toString(Base64.decode("QmVhcmVyIEd4b05kUGhPcnNrV1laZlN3MmQ5aGdlWFRvU2xVQmFs", Base64.DEFAULT)));
            newBuilder.addHeader(ACCEPT, APPLICATION_JSON);
            request = newBuilder.build();
            return chain.proceed(request);
        })
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).build();

        Retrofit newRetrofit = retrofitStatus.newBuilder().client(newClient).build();
        return newRetrofit.create(service);

    }


    @Named("imdb")
    public static <S> S createServiceImdb(Class<S> serviceClass) {

        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builderImdb.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit2.create(serviceClass);
    }


    @Named("opensubs")
    public static <S> S createServiceOpenSubs(Class<S> serviceClass) {

        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builderOpenSubs.client(httpClient.build());
            retrofit = builderOpenSubs.build();
        }
        return retrofit3.create(serviceClass);
    }


    @Named("Auth")
    public static <T> T createServiceWithAuth(Class<T> service, final TokenManager tokenManager) {
        OkHttpClient newClient = client.newBuilder().addInterceptor(chain -> {

            Request request = chain.request();
            Request.Builder newBuilder = request.newBuilder();
            if (tokenManager.getToken().getAccessToken() != null) {
                newBuilder.addHeader("Authorization", "Bearer " + tokenManager.getToken().getAccessToken());
            }
            request = newBuilder.build();
            return chain.proceed(request);
        }).authenticator(CustomAuthenticator.getInstance(tokenManager)).build();

        Retrofit newRetrofit = retrofit.newBuilder().client(newClient).build();
        return newRetrofit.create(service);

    }


    /**
     * Interceptor to cache data and maintain it for a minute.
     * If the same network request is sent within a minute,
     * the response is retrieved from cache.
     */
    private static class ResponseCacheInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {

            Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header(CACHE_CONTROL);

            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                Timber.i("Response cache applied");
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header(CACHE_CONTROL, "public, max-age=" + 60)
                        .build();
            } else {
                Timber.i("Response cache not applied");
                return originalResponse;
            }
        }
    }


    /**
     * Interceptor to cache data and maintain it for four weeks.
     * If the device is offline, stale (at most four weeks old)
     * response is fetched from the cache.
     */
    private static class OfflineResponseCacheInterceptor implements Interceptor {
        @Override
        public @NotNull Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();

            if (!Tools.checkIfHasNetwork(App.getContext())) {
                Timber.i("Offline cache applied");
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header(CACHE_CONTROL, "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            } else {
                Timber.i("Offline cache not applied");
            }
            return chain.proceed(request);
        }
    }

    /**
     * Interceptor to display response message
     */
    private static class ErrorHandlerInterceptor implements Interceptor {
        @Override
        public @NotNull Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            switch (response.code()) {
                case 200:
                    Timber.i("200 - Found");
                    break;
                case 404:
                    Timber.i("404 - Not Found");
                    break;
                case 500:
                case 504:
                    Timber.i("500 - Server Broken");
                    break;
                default:
                    Timber.i("Network Unknown Error");
                    break;
            }
            return response;
        }
    }


    public static Retrofit getOpenSubsServer() {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        if (retrofit5 == null) {
            retrofit5 = new Retrofit.Builder()
                    .baseUrl(SERVER_OPENSUBS_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit5;
    }

}