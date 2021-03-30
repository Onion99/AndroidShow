package com.onion.android.app.plex.data.remote;

import android.util.Base64;

import com.onion.android.app.utils.Tools;

import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.onion.android.app.constants.PlexConstants.ACCEPT;
import static com.onion.android.app.constants.PlexConstants.APPLICATION_JSON;
@Singleton
public class FsmPlayerApi {

    private FsmPlayerApi(){}

    public static final String PA = "QmVhcmVyIEd4b05kUGhPcnNrV1laZlN3MmQ5aGdlWFRvU2xVQmFs";
    public static final String PL = "TEVHSVQ=";
    public static final String PN = "MQ==";
    public static final String P0 = "MA==";
    public static final String PI = "Mjg0NjI3OTk=";
    public static final String AT = "QXV0aG9yaXphdGlvbg==";

    // 解析域名
    public static String decodeServiceMainApi(){
        byte[] valueDecoded;
        valueDecoded = Base64.decode(PA.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }

    private static String decodeServerMainApi6(){
        byte[] valueDecoded;
        valueDecoded = Base64.decode(AT.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }

    // OkhttpClient实例化
    public static final OkHttpClient client = new OkHttpClient();

    public static OkHttpClient buildClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(
                // 处理请求链
                chain -> {
                    Request request = chain.request();

                    Request.Builder addHeader = request.newBuilder()
                            .addHeader(ACCEPT, APPLICATION_JSON);

                    request = addHeader.build();

                    return chain.proceed(request);
                }
        );
        return builder.build();
    }

    // 实例化Retrofit
    private static final Retrofit.Builder builderStatus = new Retrofit.Builder()
            .baseUrl(Tools.getPlayer())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());
    public static final Retrofit retrofitStatus = builderStatus.build();

    // 实例化请求Service
    @Named("player")
    public static <T> T playerAdLogicControllerApi(Class<T> service){
        Proxy proxy;
        OkHttpClient newClient = client.newBuilder().addInterceptor(
                chain -> {
                    Request request = chain.request();
                    Request.Builder newBuilder = request.newBuilder();
                    newBuilder.addHeader(ACCEPT, APPLICATION_JSON);
                    request = newBuilder.build();
                    return chain.proceed(request);
                }
        ).connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).build();

        Retrofit newRetrofit = retrofitStatus.newBuilder().client(newClient).build();
        return newRetrofit.create(service);
    }

}
