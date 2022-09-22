package com.example.projectfinal.api;

import com.example.projectfinal.entity.Cart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartAPI {

    Gson gson = new GsonBuilder().setLenient().create();
    CartAPI cartApi = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/SolarBookAPI/solar_book/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(CartAPI.class);

    @GET("cart/getcart")
    Call<List<Cart>> getListCart();

    @GET("cart/getcart/{userId}")
    Call<List<Cart>> getBookCartByUserId(@Path("userId") int userId);

    @POST("cart")
    Call<Cart> insertCart(@Body Cart cart);

    @DELETE("cart/{userId}/{bookId}")
    Call<Void> deleteBookFromCartByBookId(@Path("userId") int userId, @Path("bookId") int bookId);

}
