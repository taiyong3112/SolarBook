package com.example.projectfinal.api;

import com.example.projectfinal.entity.Category;
import com.example.projectfinal.entity.News;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsAPI {
    //Link API: http://localhost:8080/SolarBookAPI/solar_book/news/getnews

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    NewsAPI newsAPI = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/SolarBookAPI/solar_book/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(NewsAPI.class);

    @GET("news/getnews")
    Call<List<News>> getNesws();

    @POST("news/addnews")
    Call<News> addNews(@Body News news);

    @PUT("news/updatenews")
    Call<News> updateNews(@Body News news);

    //truyền id vào link post thì mới xóa được, sử dụng @Path chứ không dùng @Query
    @POST("news/deletenews/{id}")
    Call<News> deleteNews(@Path("id") int id);

    //hiển thị chi tiết tin tức
    @GET("news/detailnews/{id}")
    Call<News> detailNews(@Path("id") int id);
}
