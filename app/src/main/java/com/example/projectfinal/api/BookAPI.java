package com.example.projectfinal.api;

import com.example.projectfinal.entity.Book;
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

public interface BookAPI {
    //Link API: http://localhost:8080/SolarBookAPI/solar_book/category/getcategory

    Gson gson = new GsonBuilder().setLenient().create();
    BookAPI bookAPI = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/SolarBookAPI/solar_book/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(BookAPI.class);

    @GET("book/getbook")
    Call<List<Book>> getBook();

    @POST("book/addbook")
    Call<Book> addBook(@Body Book book);

    @PUT("book/updatebook")
    Call<Book> updateBook(@Body Book book);

    @POST("book/deletebook/{id}")
    Call<Void> deleteBook(@Path("id") int id);

    @GET("book/detailbook/{id}")
    Call<Book> detailBook(@Path("id") int id);

    @GET("book/getbook/find")
    Call<List<Book>> findByName(@Query("name") String name);
}
