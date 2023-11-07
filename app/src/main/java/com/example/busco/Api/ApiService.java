package com.example.busco.Api;

import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiService {
    private static EndpointsMap instance;
    public static EndpointsMap getInstance(){
        Moshi moshi = new Moshi.Builder()
                .add(new DateAdapter())
                .build();
        if (instance == null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api-spring-busco-pt2.onrender.com").addConverterFactory(MoshiConverterFactory.create(moshi)).build();
            instance = retrofit.create(EndpointsMap.class);
        }
        return instance;
    }
}
