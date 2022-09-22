package com.example.projectfinal.api;

import com.example.projectfinal.entity.OrderDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OrderDetailAPI {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    OrderDetailAPI orderDetailApi = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/SolarBookAPI/solar_book/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(OrderDetailAPI.class);

    @GET("orderdetail/getorderdetail")
    Call<List<OrderDetail>> getOrderDetail();

    @POST("orderdetail")
    Call<OrderDetail> insert(@Body OrderDetail orderDetail);
}
