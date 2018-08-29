package com.mobile.meredithbayne.recipesharing.api;

import com.mobile.meredithbayne.recipesharing.Const;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
