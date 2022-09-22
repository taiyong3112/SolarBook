package com.example.projectfinal.api;

import com.example.projectfinal.entity.Category;
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

public interface CategoryAPI {
    //Link API: http://localhost:8080/SolarBookAPI/solar_book/category/getcategory

    Gson gson = new GsonBuilder().setLenient().create();
    CategoryAPI categoryAPI = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/SolarBookAPI/solar_book/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(CategoryAPI.class);

    @GET("category/getcategory")
    Call<List<Category>> getCategory();

    @POST("category/addcategory")
    Call<Category> addCategory(@Body Category category);

    @PUT("category/updatecategory")
    Call<Category> updateCategory(@Body Category category);

    @POST("category/deletecategory/{id}")
    Call<Void> deleteCategory(@Path("id") int id);

    @GET("category/detailCategory/{id}")
    Call<Category> detailCategory(@Path("id") int id);
}
