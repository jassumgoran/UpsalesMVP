package com.gokiapps.upsalesmvp.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Goran on 20.4.2018.
 */

public class ApiClient {

    private static final String KEY_TOKEN = "token";
    private static final String TOKEN = "25b7a23c00d1061d62315ec93b3297523b640b2bf57af2cd1e588d457a896ca8";

    private static UpsalesAPI upsalesApi;

    public static UpsalesAPI getClient(){
        if(upsalesApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UpsalesAPI.BASE_URL)
                    .client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            upsalesApi = retrofit.create(UpsalesAPI.class);
        }
        return upsalesApi;
    }

    private static OkHttpClient getHttpClient(){
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(KEY_TOKEN, TOKEN)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClient.build();
    }



}
