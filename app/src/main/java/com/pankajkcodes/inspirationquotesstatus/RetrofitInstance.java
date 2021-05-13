package com.pankajkcodes.inspirationquotesstatus;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    // STATIC INSTANCE FOR CALL ANYWHERE WITHOUT MAKE OBJECT
    private static Retrofit retrofit;
    // DEFINE YOUR BASE URL WHICH IS COMMON IN YOUR API
    private static String BASE_URL = "https://type.fit/api/";

    // CRETE A GETTER METHOD TO GET INSTANCE
    public static Retrofit getRetrofit() {
        // CONDITION FOR CREATE RETROFIT OBJECT
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
