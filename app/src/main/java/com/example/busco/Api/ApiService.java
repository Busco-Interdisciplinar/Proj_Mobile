package com.example.busco.Api;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiService {
    private static EndpointsMap instance;
    public static EndpointsMap getInstance(){
        if (instance == null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.138:8181/api").addConverterFactory(MoshiConverterFactory.create()).build();
            instance = retrofit.create(EndpointsMap.class);
        }
        return instance;
    }
}
