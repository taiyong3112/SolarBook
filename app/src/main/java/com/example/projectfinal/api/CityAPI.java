package com.example.projectfinal.api;

import com.example.projectfinal.entity.City;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface CityAPI {
    //Link API: http://localhost:8080/SolarBookAPI/solar_book/city/getcity

    Gson gson = new GsonBuilder().setLenient().create();
    CityAPI cityApi = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/SolarBookAPI/solar_book/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(CityAPI.class);

    @GET("city/getcity")
    Call<List<City>> getListCities();


}
