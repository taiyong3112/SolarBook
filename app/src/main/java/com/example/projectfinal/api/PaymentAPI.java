package com.example.projectfinal.api;

import com.example.projectfinal.entity.Payment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface PaymentAPI {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    PaymentAPI paymentApi = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/SolarBookAPI/solar_book/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(PaymentAPI.class);

    @GET("payment/getpayment")
    Call<List<Payment>> getPayments();
}
