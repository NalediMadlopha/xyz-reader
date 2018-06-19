package com.example.xyzreader.service;

import com.example.xyzreader.model.Article;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.example.xyzreader.service.Config.BASE_URL;

public interface Service {

    @GET(BASE_URL)
    Call<Article[]> getArticles();

}
