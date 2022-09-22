package com.example.projectfinal.api;

import com.example.projectfinal.entity.Order;
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

public interface OrderAPI {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    OrderAPI orderApi = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/SolarBookAPI/solar_book/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(OrderAPI.class);

    @GET("order/getorder")
    Call<List<Order>> getOrders();

    @POST("order")
    Call<Order> insertOrder(@Body Order order);

    @PUT("order")
    Call<Order> updateOrder(@Body Order order);
}
