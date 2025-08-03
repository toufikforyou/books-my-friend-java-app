package com.sopnolikhi.booksmyfriend.Services.Networks.Apis.Account;

import com.sopnolikhi.booksmyfriend.Services.Includes.Storage.ApiStorage;
import com.sopnolikhi.booksmyfriend.Services.Includes.Utils.UserSetting;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountApiInstance {
    private static final String API_LANG_VALUE = UserSetting.SETTING_LANGUAGE_USER_SET;
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient;

    // TODO: Get Retrofit Builder static function use any request for this api;
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(ApiStorage.getApiBaseUrl()).client(okHttpClient().build()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    // TODO: Create a custom OkHttpClient with an Interceptor in our api key; header send data for user authorization;
    private static OkHttpClient.Builder okHttpClient() {
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request request = chain.request().newBuilder().header("X-API-KEY", ApiStorage.getApiKey()).addHeader("X-API-TOKEN", ApiStorage.getApiToken()).addHeader("AUTHORIZATION", ApiStorage.getUserToken()).addHeader("SOPNOLIKHI", ApiStorage.getUid()).addHeader("X-LANG", API_LANG_VALUE).build();
                return chain.proceed(request);
            });
        }
        return httpClient;
    }
}
