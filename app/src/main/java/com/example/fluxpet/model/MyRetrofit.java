package com.example.fluxpet;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class MyRetrofit {
    protected Retrofit retrofit;

    public MyRetrofit(String urlBase) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(urlBase)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
    }

}
