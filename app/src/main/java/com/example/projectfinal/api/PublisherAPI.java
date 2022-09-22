package com.example.projectfinal.api;

import com.example.projectfinal.entity.Publisher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PublisherAPI {
    //Link API: http://localhost:8080/SolarBookAPI/solar_book/publisher/getpublisher

    Gson gson = new GsonBuilder().setLenient().create();
    PublisherAPI publisherAPI = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/SolarBookAPI/solar_book/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(PublisherAPI.class);

    @GET("publisher/getpublisher")
    Call<List<Publisher>> getPublisher();

    @POST("publisher/addpublisher")
    Call<Publisher> addPublisher(@Body Publisher publisher);

    @PUT("publisher/updatepublisher")
    Call<Publisher> updatePublisher(@Body Publisher publisher);

    @POST("publisher/deletepublisher/{id}")
    Call<Void> deletePublisher(@Path("id") int id );

    @GET("publisher/detailpublisher/{id}")
    Call<Publisher> detailPublisher(@Path("id") int id);
}
